package team.chisel.chisel.impl;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.AxisDirection;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import team.chisel.chisel.Chisel;
import team.chisel.chisel.api.ChiselMode;
import team.chisel.chisel.api.ChiselModeRegistry;
import team.chisel.chisel.util.Point2i;

// TODO: This class should not be in the impl package
public enum ChiselModeImpl implements ChiselMode {
	SINGLE() {
		@Override
		public Iterable<BlockPos> getCandidates(PlayerEntity player, BlockPos pos, Direction side) {
			return Collections.singleton(pos);
		}
		
		@Override
		public Box getBounds(Direction side) {
			return new Box(0, 0, 0, 1, 1, 1);
		}
	},
	PANEL() {
		private final BlockPos ONE = new BlockPos(1, 1, 1);
		private final BlockPos NEG_ONE = new BlockPos(-1, -1, -1);

		@Override
		public Iterable<BlockPos> getCandidates(PlayerEntity player, BlockPos pos, Direction side) {
			if (side.getDirection() == AxisDirection.NEGATIVE) {
				side = side.getOpposite();
			}
			Vec3i offset = side.getVector();
			return filteredIterable(BlockPos.stream(NEG_ONE.add(offset).add(pos), ONE.subtract(offset).add(pos)), player.world, player.world.getBlockState(pos));
		}
		
		@Override
		public Box getBounds(Direction side) {
			switch (side.getAxis()) {
			case X:
			default:
				return new Box(0, -1, -1, 1, 2, 2);
			case Y:
				return new Box(-1, 0, -1, 2, 1, 2);
			case Z:
				return new Box(-1, -1, 0, 2, 2, 1);
			}
		}
	},
	COLUMN() {
		@Override
		public Iterable<BlockPos> getCandidates(PlayerEntity player, BlockPos pos, Direction side) {
			int facing = MathHelper.floor(player.yaw * 4.0F / 360.0F + 0.5D) & 3;
			Set<BlockPos> ret = new LinkedHashSet<>();
			for (int i = -1; i <= 1; i++) {
				if (side != Direction.DOWN && side != Direction.UP) {
					ret.add(pos.up(i));
				} else {
					if (facing == 0 || facing == 2) {
						ret.add(pos.south(i));
					} else {
						ret.add(pos.east(i));
					}
				}
			}
			return filteredIterable(ret.stream(), player.world, player.world.getBlockState(pos));
		}
		
		@Override
		public Box getBounds(Direction side) {
			return PANEL.getBounds(side);
		}
		
		@SuppressWarnings("resource")
		@Override
		public long[] getCacheState(BlockPos origin, Direction side) {
			return ArrayUtils.add(super.getCacheState(origin, side), MinecraftClient.getInstance().player.getHorizontalFacing().ordinal());
		}
	},
	ROW() {
		@Override
		public Iterable<BlockPos> getCandidates(PlayerEntity player, BlockPos pos, Direction side) {
			int facing = MathHelper.floor(player.yaw * 4.0F / 360.0F + 0.5D) & 3;
			Set<BlockPos> ret = new LinkedHashSet<>();
			for (int i = -1; i <= 1; i++) {
				if (side != Direction.DOWN && side != Direction.UP) {
					if (side == Direction.EAST || side == Direction.WEST) {
						ret.add(pos.south(i));
					} else {
						ret.add(pos.east(i));
					}
				} else {
					if (facing == 0 || facing == 2) {
						ret.add(pos.east(i));
					} else {
						ret.add(pos.south(i));
					}
				}
			}
			return filteredIterable(ret.stream(), player.world, player.world.getBlockState(pos));
		}
		
		@Override
		public Box getBounds(Direction side) {
			return PANEL.getBounds(side);
		}
		
		@Override
		public long[] getCacheState(BlockPos origin, Direction side) {
			return COLUMN.getCacheState(origin, side);
		}
	}, 
	CONTIGUOUS() {
		@Override
		public Iterable<? extends BlockPos> getCandidates(PlayerEntity player, BlockPos pos, Direction side) {
			return () -> getContiguousIterator(pos, player.world, Direction.values());
		}
		
		@Override
		public Box getBounds(Direction side) {
			int r = CONTIGUOUS_RANGE;
			return new Box(-r - 1, -r - 1, -r - 1, r + 2, r + 2, r + 2);
		}
	},
	CONTIGUOUS_2D() {
		@Override
		public Iterable<? extends BlockPos> getCandidates(PlayerEntity player, BlockPos pos, Direction side) {
			return () -> getContiguousIterator(pos, player.world, ArrayUtils.removeElements(Direction.values(), side, side.getOpposite()));
		}
		
		@Override
		public Box getBounds(Direction side) {
			int r = CONTIGUOUS_RANGE;
			switch (side.getAxis()) {
			case X:
			default:
				return new Box(0, -r - 1, -r - 1, 1, r + 2, r + 2);
			case Y:
				return new Box(-r - 1, 0, -r - 1, r + 2, 1, r + 2);
			case Z:
				return new Box(-r - 1, -r - 1, 0, r + 2, r + 2, 1);
			}
		}
	}
	;
	
	// Register all enum constants to the mode registry
	{
		ChiselModeRegistry.INSTANCE.register(this);
	}
	
	public static final Identifier SPRITES = new Identifier(Chisel.MOD_ID, "textures/gui/mode_icons.png");
	public static final int CONTIGUOUS_RANGE = 10;
	
	private static class Node {
		private BlockPos pos;
		int distance;
		
		public Node(BlockPos pos, int distance) {
			this.pos = pos;
			this.distance = distance;
		}
		
		public BlockPos getPos() {
			return pos;
		}
		
		public int getDistance() {
			return distance;
		}
	}
	
	private static Iterator<BlockPos> getContiguousIterator(BlockPos origin, World world, Direction[] directionsToSearch) {
		final BlockState state = world.getBlockState(origin);
		return new Iterator<BlockPos>() {
			private Set<BlockPos> seen = Sets.newHashSet(origin);
			private Queue<Node> search = new ArrayDeque<>();
			{ search.add(new Node(origin, 0)); }

			@Override
			public boolean hasNext() {
				return !search.isEmpty();
			}

			@Override
			public BlockPos next() {
				Node ret = search.poll();
				if (ret.getDistance() < CONTIGUOUS_RANGE) {
					for (Direction face : directionsToSearch) {
						BlockPos bp = ret.getPos().offset(face);
						if (!seen.contains(bp) && world.getBlockState(bp) == state) {
							for (Direction obscureCheck : Direction.values()) {
								BlockPos obscuringPos = bp.offset(obscureCheck);
								BlockState obscuringState = world.getBlockState(obscuringPos);
								if (!Block.shouldDrawSide(obscuringState, world, obscuringPos, obscureCheck.getOpposite())) {
									search.offer(new Node(bp, ret.getDistance() + 1));
									break;
								}
							}
						}
						seen.add(bp);
					}
				}
				return ret.getPos();
			}
		};
	}
	
	private static Iterable<BlockPos> filteredIterable(Stream<BlockPos> source, World world, BlockState state) {
		return source.filter(p -> world.getBlockState(p) == state)::iterator;
	}
	
	private ChiselModeImpl() {
	}
	
	@Override
	public Identifier getSpriteSheet() {
		return SPRITES;
	}
	
	@Override
	public Point2i getSpritePos() {
		return new Point2i((ordinal() % 10) * 24, (ordinal() / 10) * 24);
	}
}
