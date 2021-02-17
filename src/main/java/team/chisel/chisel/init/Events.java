package team.chisel.chisel.init;

import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

import com.google.common.base.Preconditions;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.decoration.painting.PaintingMotive;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import team.chisel.chisel.api.CarvingGroup;
import team.chisel.chisel.api.CarvingGroupRegistry;
import team.chisel.chisel.api.CarvingVariant;
import team.chisel.chisel.api.ChiselItem;
import team.chisel.chisel.api.ChiselMode;
import team.chisel.chisel.item.ChiselItemImpl;
import team.chisel.chisel.mixin.AbstractDecorationEntityAccessor;
import team.chisel.chisel.mixin.client.ClientPlayerInteractionManagerAccessor;
import team.chisel.chisel.util.ChiselNBT;
import team.chisel.chisel.util.SoundUtil;

public class Events {
	public static void init() {
		UseItemCallback.EVENT.register((PlayerEntity player, World world, Hand hand) -> {
			ItemStack held = player.getStackInHand(hand);
			if (!world.isClient()) {
				if (held.getItem() instanceof ChiselItem) {
					ChiselItem chisel = (ChiselItem) held.getItem();
					if (chisel.canOpenGui(player, world, hand)) {
						player.openHandledScreen(chisel.getHandlerFactory(player, world, hand));
						return TypedActionResult.success(held);
					}
				}
			}
			return TypedActionResult.pass(held);
		});
		
		AttackBlockCallback.EVENT.register((PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) -> {
			ItemStack held = player.getStackInHand(hand);
			
			if (held.getItem() instanceof ChiselItem) {
				BlockState state = world.getBlockState(pos);
				CarvingGroupRegistry registry = CarvingGroupRegistry.INSTANCE;
				CarvingGroup blockGroup = registry.getGroup(state);
				
				if (blockGroup == null) {
					return ActionResult.PASS;
				}
				
				ItemStack target = ChiselNBT.getChiselTarget(held);
				ChiselItem chisel = (ChiselItem) held.getItem();
				
				if (!chisel.canChiselBlock(player, world, hand, pos, state)) {
					return ActionResult.PASS;
				}
				
				ChiselMode mode = ChiselNBT.getChiselMode(held);
				Iterable<? extends BlockPos> candidates = mode.getCandidates(player, pos, direction);
				boolean success = false;
				
				if (target.isEmpty()) {
					CarvingVariant current = registry.getVariant(state);
					List<CarvingVariant> variants = blockGroup.getCarvingVariants();
					variants = variants.stream().filter(v -> v.getBlockState() != null).collect(Collectors.toList());
							
					int index = variants.indexOf(current);
					index = player.isSneaking() ? index - 1 : index + 1;
					index = (index + variants.size()) % variants.size();
					CarvingVariant next = variants.get(index);
					
					success = setAll(player, world, hand, candidates, state, next);
				} else {
					CarvingGroup sourceGroup = registry.getGroup(target.getItem());

					if (blockGroup == sourceGroup) {
						CarvingVariant variant = registry.getVariant(target.getItem());
						if (variant != null) {
							if (variant.getBlockState() != null) {
								success = setAll(player, world, hand, candidates, state, variant);
							}
						} else {
							//Chisel.logger.warn("Found itemstack {} in group {}, but it has no variant!", target, sourceGroup.getName());
						}
					}
				}
				
				if (player.isCreative()) {
					if (world.isClient()) {
						if (success) {
							MinecraftClient client = MinecraftClient.getInstance();
							((ClientPlayerInteractionManagerAccessor) client.interactionManager).callSendPlayerAction(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, pos, direction);
							if (world.getBlockState(pos) != state) {
								((ClientPlayerInteractionManagerAccessor) client.interactionManager).setBlockBreakingCooldown(5);
								return ActionResult.SUCCESS;
							}
						}
						return ActionResult.FAIL;
					} else {
						if (success) {
							return ActionResult.SUCCESS;
						}
					}
				}
			}
			
			return ActionResult.PASS;
		});
		
		AttackEntityCallback.EVENT.register((PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult) -> {
			if (entity instanceof PaintingEntity) {
				ItemStack held = player.getStackInHand(hand);
				if (held.getItem() instanceof ChiselItem) {
					PaintingEntity painting = (PaintingEntity) entity;
					
					int i = player.isSneaking() ? -1 : 1;
					List<PaintingMotive> motives = Registry.PAINTING_MOTIVE.stream().collect(Collectors.toList());
					int counter = motives.indexOf(painting.motive);
					int size = motives.size();
					do {
						counter = (counter + i + size) % size;
						painting.motive = motives.get(counter);
						((AbstractDecorationEntityAccessor) painting).callUpdateAttachmentPosition();
					} while (!painting.canStayAttached());
					
					// damage chisel
					//painting.onPlace();
					return ActionResult.SUCCESS;
				}
			}
			return ActionResult.PASS;
		});
		
		/*AttackBlockCallback.EVENT.register((PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) -> {
			ItemStack held = player.getStackInHand(hand);
			if (held.getItem() == net.minecraft.item.Items.NETHERITE_AXE) {
				BlockState state = world.getBlockState(pos);
				BlockState newState;
				if (state == Blocks.DIAMOND_BLOCK.getDefaultState()) {
					newState = Blocks.EMERALD_BLOCK.getDefaultState();
				} else {
					newState = Blocks.DIAMOND_BLOCK.getDefaultState();
				}
				
				if (world.isClient()) {
					if (!player.abilities.creativeMode) {
						held.damage(1, player.getRandom(), null);
					}
				} else {
					held.damage(1, player, (p) -> {
						p.sendToolBreakStatus(p.getActiveHand());
					});
				}
				
				world.setBlockState(pos, newState);
				
				if (player.isCreative()) {
					if (world.isClient()) {
						//if (success) {
							MinecraftClient client = MinecraftClient.getInstance();
							((ClientPlayerInteractionManagerAccessor) client.interactionManager).callSendPlayerAction(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, pos, direction);
							if (world.getBlockState(pos) != state) {
								((ClientPlayerInteractionManagerAccessor) client.interactionManager).setBlockBreakingCooldown(5);
								return ActionResult.SUCCESS;
							}
						//}
						return ActionResult.FAIL;
					} else {
						//if (success) {
							return ActionResult.SUCCESS;
						//}
					}
				}
			}
			
			return ActionResult.PASS;
		});*/
	}
	
	/**
	 * @return True if at least one BlockState changed.
	 */
	private static boolean setAll(PlayerEntity player, World world, Hand hand, Iterable<? extends BlockPos> candidates, BlockState origState, CarvingVariant variant) {
		boolean success = false;
		for (BlockPos pos : candidates) {
			boolean result = setVariant(player, world, hand, pos, origState, variant);
			if (result) {
				success = true;
			}
		}
		return success;
	}
	
	/**
	 * Assumes that the player is holding a chisel
	 * 
	 * @return True if the BlockState changed.
	 */
	private static boolean setVariant(PlayerEntity player, World world, Hand hand, BlockPos pos, BlockState origState, CarvingVariant variant) {
		BlockState targetState = variant.getBlockState();
		Preconditions.checkNotNull(targetState, "Variant state cannot be null!");
		
		BlockState curState = world.getBlockState(pos);
		ItemStack held = player.getStackInHand(hand);
		if (curState == variant.getBlockState()) {
			return false; // don't chisel to the same thing
		}
		if (origState != curState) {
			return false; // don't chisel if this doesn't match the target block (for the AOE modes)
		}

		if (held.getItem() instanceof ChiselItem) {
			ChiselItem chisel = ((ChiselItem) held.getItem());
			ItemStack current = new ItemStack(CarvingGroupRegistry.INSTANCE.getVariant(curState).getItem());
			ItemStack target = new ItemStack(variant.getItem());
			chisel.craftItem(held, current, target, player, (p) -> {
				if (held.getItem() instanceof ChiselItemImpl) {
					if (((ChiselItemImpl) held.getItem()).getType() == ChiselItemImpl.ChiselType.HITECH) {
						return;
					}
				}
				ItemStack targetStack = ChiselNBT.getChiselTarget(held);
				p.inventory.main.set(p.inventory.selectedSlot, targetStack);
			});
			chisel.onChisel(player, world, held, variant);
			
			if (world.isClient()) {
				MinecraftClient client = MinecraftClient.getInstance();
				SoundUtil.playSound(player, held, targetState);
				client.particleManager.addBlockBreakParticles(pos, curState);
			}
			
			world.setBlockState(pos, targetState);
			return true;
		}
		
		return false;
	}
}
