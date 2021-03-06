package team.chisel.chisel.client.init;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext.BlockOutlineContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.world.BlockView;
import team.chisel.chisel.api.CarvingGroupRegistry;
import team.chisel.chisel.api.ChiselItem;
import team.chisel.chisel.api.ChiselMode;
import team.chisel.chisel.util.ChiselNBT;

public class Events {
	public static void init() {
		WorldRenderEvents.BLOCK_OUTLINE.register(new BlockOutlineEventHandler(MinecraftClient.getInstance()));
	}
	
	public static class BlockOutlineEventHandler implements WorldRenderEvents.BlockOutline {
		private final MinecraftClient client;
		private final BufferBuilder buffer = new BufferBuilder(256);
		private float animation = 0;
		
		public BlockOutlineEventHandler(MinecraftClient client) {
			this.client = client;
		}
		
		@Override
		public boolean onBlockOutline(WorldRenderContext worldRenderContext, BlockOutlineContext blockOutlineContext) {
			ItemStack held = client.player.getMainHandStack();
			if (held.getItem() instanceof ChiselItem && CarvingGroupRegistry.INSTANCE.getGroup(blockOutlineContext.blockState()) != null) {
				Direction side = ((BlockHitResult) client.crosshairTarget).getSide();
				ChiselMode mode = ChiselNBT.getChiselMode(held);
				
				float sin = (float) Math.sin(animation);
				float color = Math.round(sin * 0.5F + 0.5F);
				float alpha = Math.abs(sin) * 0.25F;
				animation += client.getLastFrameDuration() * 0.12F;
				
				buffer.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR);
				
				Matrix4f matrix = worldRenderContext.matrixStack().peek().getModel();
				for (BlockPos pos : mode.getCandidates(client.player, blockOutlineContext.blockPos(), side)) {
					drawVertices(buffer, matrix, blockOutlineContext.cameraX(), blockOutlineContext.cameraY(), blockOutlineContext.cameraZ(), client.world, pos, color, alpha);
				}

				buffer.end();
				
				RenderSystem.disableTexture();
				
				RenderSystem.enableBlend();
				RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO); //4 GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA
				
				RenderSystem.enableDepthTest();
				RenderSystem.depthFunc(GL11.GL_LEQUAL);
				
				RenderSystem.polygonOffset(-1.0F, 0.0F); // Render under breaking animation
				RenderSystem.enablePolygonOffset();
				
				BufferRenderer.draw(buffer);
				
				RenderSystem.enableTexture();

				RenderSystem.disableBlend();
				RenderSystem.defaultBlendFunc();
				
				RenderSystem.disableDepthTest();
				RenderSystem.depthFunc(GL11.GL_LEQUAL);
				
				RenderSystem.polygonOffset(0.0F, 0.0F);
				RenderSystem.disablePolygonOffset();
				
				return false;
			}
			
			return true;
		}
		
		private static void drawVertices(BufferBuilder buffer, Matrix4f matrix, double cameraX, double cameraY, double cameraZ, BlockView world, BlockPos pos, float color, float alpha) {
			BlockState state = world.getBlockState(pos);
			Box box = state.getOutlineShape(world, pos).getBoundingBox();
			
			float minX = (float) (box.minX + pos.getX() - cameraX);
			float minY = (float) (box.minY + pos.getY() - cameraY);
			float minZ = (float) (box.minZ + pos.getZ() - cameraZ);
			float maxX = (float) (box.maxX + pos.getX() - cameraX);
			float maxY = (float) (box.maxY + pos.getY() - cameraY);
			float maxZ = (float) (box.maxZ + pos.getZ() - cameraZ);
			
			if (Block.shouldDrawSide(state, world, pos, Direction.DOWN)) {
				buffer.vertex(matrix, minX, minY, minZ).color(color, color, color, alpha).next();
				buffer.vertex(matrix, maxX, minY, minZ).color(color, color, color, alpha).next();
				buffer.vertex(matrix, maxX, minY, maxZ).color(color, color, color, alpha).next();
				buffer.vertex(matrix, minX, minY, maxZ).color(color, color, color, alpha).next();
			}
			if (Block.shouldDrawSide(state, world, pos, Direction.UP)) {
				buffer.vertex(matrix, minX, maxY, maxZ).color(color, color, color, alpha).next();
				buffer.vertex(matrix, maxX, maxY, maxZ).color(color, color, color, alpha).next();
				buffer.vertex(matrix, maxX, maxY, minZ).color(color, color, color, alpha).next();
				buffer.vertex(matrix, minX, maxY, minZ).color(color, color, color, alpha).next();
			}
			if (Block.shouldDrawSide(state, world, pos, Direction.NORTH)) {
				buffer.vertex(matrix, maxX, minY, minZ).color(color, color, color, alpha).next();
				buffer.vertex(matrix, minX, minY, minZ).color(color, color, color, alpha).next();
				buffer.vertex(matrix, minX, maxY, minZ).color(color, color, color, alpha).next();
				buffer.vertex(matrix, maxX, maxY, minZ).color(color, color, color, alpha).next();
			}
			if (Block.shouldDrawSide(state, world, pos, Direction.SOUTH)) {
				buffer.vertex(matrix, minX, minY, maxZ).color(color, color, color, alpha).next();
				buffer.vertex(matrix, maxX, minY, maxZ).color(color, color, color, alpha).next();
				buffer.vertex(matrix, maxX, maxY, maxZ).color(color, color, color, alpha).next();
				buffer.vertex(matrix, minX, maxY, maxZ).color(color, color, color, alpha).next();
			}
			if (Block.shouldDrawSide(state, world, pos, Direction.WEST)) {
				buffer.vertex(matrix, minX, minY, minZ).color(color, color, color, alpha).next();
				buffer.vertex(matrix, minX, minY, maxZ).color(color, color, color, alpha).next();
				buffer.vertex(matrix, minX, maxY, maxZ).color(color, color, color, alpha).next();
				buffer.vertex(matrix, minX, maxY, minZ).color(color, color, color, alpha).next();
			}
			if (Block.shouldDrawSide(state, world, pos, Direction.EAST)) {
				buffer.vertex(matrix, maxX, minY, maxZ).color(color, color, color, alpha).next();
				buffer.vertex(matrix, maxX, minY, minZ).color(color, color, color, alpha).next();
				buffer.vertex(matrix, maxX, maxY, minZ).color(color, color, color, alpha).next();
				buffer.vertex(matrix, maxX, maxY, maxZ).color(color, color, color, alpha).next();
			}
		}
	}
}
