package team.chisel.chisel.client.gui;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.Rect2i;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import net.minecraft.world.chunk.ChunkProvider;
import net.minecraft.world.chunk.light.LightingProvider;
import net.minecraft.world.level.ColorResolver;
import team.chisel.chisel.Chisel;
import team.chisel.chisel.api.ChiselItem;
import team.chisel.chisel.client.init.ClientNetwork;
import team.chisel.chisel.client.util.ChiselLangKeys;
import team.chisel.chisel.client.util.PreviewType;
import team.chisel.chisel.inventory.HitechChiselScreenHandler;
import team.chisel.chisel.util.ChiselNBT;

public class HitechChiselScreen extends ChiselScreen<HitechChiselScreenHandler> {
	private static final Identifier TEXTURE = new Identifier(Chisel.MOD_ID, "textures/gui/hitech_chisel.png");
	private static final Rect2i PREVIEW_AREA = new Rect2i(8, 14, 74, 74);

	private static final BufferBuilder BUFFER = new BufferBuilder(256);
	private static final MatrixStack MATRIX_STACK = new MatrixStack();
	private static final Random RANDOM = new Random();

	private @Nullable PreviewModeButton previewButton;
	private @Nullable AbstractButtonWidget chiselButton;
	private @Nullable RotateButton rotateButton;

	private DummyBlockRenderView renderView = new DummyBlockRenderView(this);
	private @Nullable BlockState erroredState;

	private boolean previewClicked;
	private int clickedButton;
	private int clickX;
	private int clickY;
	private long lastDragTime;
	private double initRotX;
	private double initRotY;
	private double prevRotX;
	private double prevRotY;
	private double momentumX;
	private double momentumY;
	private float momentumDampening = 0.98F;
	private boolean doMomentum = false;
	private double rotationX = -15;
	private double rotationY = 0;
	private double initZoom;
	private double zoom = 0;
	private int scrollAmount;

	public HitechChiselScreen(HitechChiselScreenHandler screenHandler, PlayerInventory inventory, Text title) {
		super(screenHandler, inventory, title);
		backgroundWidth = 256;
		backgroundHeight = 220;
	}

	@Override
	public void init() {
		super.init();

		int x = this.x + PREVIEW_AREA.getX() - 1;
		int y = this.y + PREVIEW_AREA.getY() + PREVIEW_AREA.getHeight() + 3;
		int w = 76;
		int h = 20;

		boolean firstInit = previewButton == null;

		previewButton = new PreviewModeButton(x, y, w, h);
		addButton(previewButton);

		chiselButton = new ButtonWidget(x, y += h + 2, w, h, ChiselLangKeys.CHISEL_HITECH_BUTTON_CHISEL.getText(), (pressed) -> {
			Slot target = handler.getTarget();
			Slot selected = handler.getSelection();
			if (target != null && target.hasStack() && selected != null && selected.hasStack()) {
				if (ItemStack.areItemsEqual(target.getStack(), selected.getStack())) {
					return;
				}
				ItemStack converted = target.getStack().copy();
				converted.setCount(selected.getStack().getCount());
				int[] slots = new int[] { selected.id };
				if (hasShiftDown()) {
					slots = ArrayUtils.addAll(slots, handler.getSelectionDuplicates().stream().mapToInt(slot -> slot.id).toArray());
				}

				handler.chiselAll(player, slots);
				ClientPlayNetworking.getSender().sendPacket(ClientNetwork.createChiselButtonPacket(slots));

				if (!hasShiftDown()) {
					List<Slot> dupes = handler.getSelectionDuplicates();
					Slot next = selected;
					for (Slot s : dupes) {
						if (s.id > selected.id) {
							next = s;
							break;
						}
					}
					if (next == selected && dupes.size() > 0) {
						next = dupes.get(0);
					}
					handler.setSelection(next);
				} else {
					handler.setSelection(selected); // Force recalculation
				}
			}
		});
		addButton(chiselButton);

		rotateButton = new RotateButton(this.x + PREVIEW_AREA.getX() + PREVIEW_AREA.getWidth() - 16, this.y + PREVIEW_AREA.getY() + PREVIEW_AREA.getHeight() - 16);
		addButton(rotateButton);

		ItemStack chisel = handler.getChisel();
		if (firstInit) {
			previewButton.setType(ChiselNBT.getHitechType(chisel));
			rotateButton.rotate = ChiselNBT.getHitechRotate(chisel);
		}

		tick();
	}

	@Override
	public void tick() {
		super.tick();

		chiselButton.active = handler.getSelection() != null && handler.getSelection().hasStack() && handler.getTarget() != null && handler.getTarget().hasStack();

		if (!previewClicked) {
			initRotX = rotationX;
			initRotY = rotationY;
			initZoom = zoom;
		}

		if (hasShiftDown()) {
			chiselButton.setMessage(ChiselLangKeys.CHISEL_HITECH_BUTTON_CHISEL_ALL.getText().formatted(Formatting.YELLOW));
		} else {
			chiselButton.setMessage(ChiselLangKeys.CHISEL_HITECH_BUTTON_CHISEL.getText());
		}
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		client.getTextureManager().bindTexture(TEXTURE);
		int x = (width - backgroundWidth) / 2;
		int y = (height - backgroundHeight) / 2;
		drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

		if (handler.getSelection() != null) {
			Slot sel = handler.getSelection();
			if (sel.hasStack()) {
				drawSlotHighlight(matrices, sel, 0);
				for (Slot s : handler.getSelectionDuplicates()) {
					drawSlotHighlight(matrices, s, hasShiftDown() ? 0 : 18);
				}
			}
		}
		if (handler.getTarget() != null && !handler.getTarget().getStack().isEmpty()) {
			drawSlotHighlight(matrices, handler.getTarget(), 36);
		}

		if (rotateButton.getRotate() && momentumX == 0 && momentumY == 0 && !previewClicked && System.currentTimeMillis() - lastDragTime > 2000) {
			rotationY = initRotY + (delta * 2);
		}

		if (previewClicked && clickedButton == 0) {
			momentumX = rotationX - prevRotX;
			momentumY = rotationY - prevRotY;
			prevRotX = rotationX;
			prevRotY = rotationY;
		}

		if (handler.getTarget() != null) {
			ItemStack stack = handler.getTarget().getStack();
			if (!stack.isEmpty()) {
				Block block = Block.getBlockFromItem(stack.getItem());
				BlockState state = block.getDefaultState();

				if (state != null && state != erroredState) {
					erroredState = null;

					renderView.setState(state);
					BlockRenderManager renderManager = client.getBlockRenderManager();

					try {
						BUFFER.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL);
						for (BlockPos pos : previewButton.getType().getPositions()) {
							MATRIX_STACK.push();
							MATRIX_STACK.translate(pos.getX(), pos.getY(), pos.getZ());
							renderManager.renderBlock(state, pos, renderView, MATRIX_STACK, BUFFER, true, RANDOM);
							MATRIX_STACK.pop();
						}
					} catch (Exception e) {
						erroredState = state;
						//Chisel.logger.error("Exception rendering block {}", state, e);
					} finally {
						BUFFER.end();
						if (erroredState == null) {
							renderPreview();
						}
					}
				}
			}
		}
	}

	@Override
	protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
		if (doMomentum) {
			rotationX += momentumX;
			rotationX = MathHelper.clamp(rotationX, -90, 90);
			rotationY += momentumY;
			momentumX *= momentumDampening;
			momentumY *= momentumDampening;
			if (Math.abs(momentumX) < 0.2) {
				if (Math.abs(momentumX) < 0.05) {
					momentumX = 0;
				} else {
					momentumX *= momentumDampening * momentumDampening;
				}
			}
			if (Math.abs(momentumY) < 0.2) {
				if (Math.abs(momentumY) < 0.05) {
					momentumY = 0;
				} else {
					momentumY *= momentumDampening * momentumDampening;
				}
			}
		}

		Text text = ChiselLangKeys.CHISEL_HITECH_PREVIEW.getText();
		textRenderer.draw(matrices, text, PREVIEW_AREA.getX() + (PREVIEW_AREA.getWidth() / 2) - (textRenderer.getWidth(text) / 2), PREVIEW_AREA.getY() - 9, 0x404040);

		RenderSystem.disableAlphaTest();
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		boolean ret = super.mouseClicked(mouseX, mouseY, button); // always returns true
		if (!rotateButton.isMouseOver(mouseX, mouseY) && PREVIEW_AREA.contains((int) (mouseX - this.x), (int) (mouseY - this.y))) {
			if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
				doMomentum = false;
			}
			clickedButton = button;
			previewClicked = true;
			clickX = (int) mouseX;
			clickY = (int) mouseY;
			return true;
		}
		return ret;
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		if (previewClicked) {
			if (clickedButton == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
				rotationX = MathHelper.clamp(initRotX + mouseY - clickY, -90, 90);
				rotationY = initRotY + mouseX - clickX;
			} else if (clickedButton == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
				zoom = MathHelper.clamp(initZoom + (clickY - mouseY), -17.19, 525); // 0.1-16x
			}
		}

		return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int state) {
		if (previewClicked) {
			lastDragTime = System.currentTimeMillis();
		}
		doMomentum = true;
		previewClicked = false;
		initRotX = rotationX;
		initRotY = rotationY;
		initZoom = zoom;

		return super.mouseReleased(mouseX, mouseY, state);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		super.mouseScrolled(mouseX, mouseY, amount);
		scrollAmount += amount;
		if (Math.abs(scrollAmount) >= 1) {
			int id = -1;
			if (handler.getTarget() != null) {
				id = handler.getTarget().id;
			}

			id -= (int) scrollAmount;
			scrollAmount = (scrollAmount < 0 ? -1 : 1) * (scrollAmount % 1);

			if (id < 0) {
				for (int i = handler.getChiselInventory().getSelectionSize() - 1; i >= 0; i--) {
					if (handler.getSlot(i).hasStack()) {
						id = i;
						break;
					}
					if (i == 0) {
						id = 0;
					}
				}
			} else if (id >= handler.getChiselInventory().getSelectionSize() || !handler.getSlot(id).hasStack()) {
				id = 0;
			}

			handler.setTarget(handler.getSlot(id));
		}
		return true;
	}

	@Override
	protected Rect2i getModeButtonArea() {
		int down = 133;
		int padding = 7;
		return new Rect2i(this.x + padding, this.y + down + padding, 76, backgroundHeight - down - (padding * 2));
	}

	private void renderPreview() {
		client.getTextureManager().bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);

		RenderSystem.clear(GL11.GL_DEPTH_BUFFER_BIT, true);

		int windowScale = (int) client.getWindow().getScaleFactor();
		RenderSystem.viewport((x + PREVIEW_AREA.getX()) * windowScale, client.getWindow().getHeight() - (y + PREVIEW_AREA.getY() + PREVIEW_AREA.getHeight()) * windowScale, PREVIEW_AREA.getWidth() * windowScale, PREVIEW_AREA.getHeight() * windowScale);

		RenderSystem.matrixMode(GL11.GL_PROJECTION);
		RenderSystem.pushMatrix();
		RenderSystem.loadIdentity();
		RenderSystem.multMatrix(Matrix4f.viewboxMatrix(60, (float) PREVIEW_AREA.getWidth() / PREVIEW_AREA.getHeight(), 0.05F, 4000));

		RenderSystem.matrixMode(GL11.GL_MODELVIEW);
		RenderSystem.pushMatrix();

		// Makes zooming slower as zoom increases, but leaves 0 as the default zoom.
		double scale = 300 + 8 * previewButton.getType().getScale() * (Math.sqrt(zoom + 100) - 9);
		RenderSystem.scaled(scale, scale, scale);

		RenderSystem.rotatef((float) rotationX, 1, 0, 0);
		RenderSystem.rotatef((float) rotationY, 0, 1, 0);

		RenderSystem.translatef(-0.5F, -0.5F, -0.5F); // point at block center instead of corner

		RenderSystem.enableDepthTest();

		BufferRenderer.draw(BUFFER);

		RenderSystem.disableDepthTest();

		RenderSystem.popMatrix();

		RenderSystem.matrixMode(GL11.GL_PROJECTION);
		RenderSystem.popMatrix();

		RenderSystem.matrixMode(GL11.GL_MODELVIEW);

		RenderSystem.viewport(0, 0, client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
	}

	private void drawSlotHighlight(MatrixStack matrices, Slot slot, int u) {
		drawTexture(matrices, this.x + slot.x - 1, this.y + slot.y - 1, u, 220, 18, 18);
	}

	private void updateChiselData() {
		ItemStack stack = handler.getChisel();
		if (!(stack.getItem() instanceof ChiselItem)) {
			return;
		}

		ChiselNBT.setHitechType(stack, previewButton.getType());
		ChiselNBT.setHitechRotate(stack, rotateButton.getRotate());
		ClientPlayNetworking.getSender().sendPacket(ClientNetwork.createHitechSettingsPacket(handler.getChiselSlot(), previewButton.getType().ordinal(), rotateButton.getRotate()));
	}

	private class PreviewModeButton extends AbstractButtonWidget {
		private PreviewType type;

		public PreviewModeButton(int x, int y, int width, int height) {
			super(x, y, width, height, LiteralText.EMPTY);
			setType(PreviewType.values()[0]);
		}

		public PreviewType getType() {
			return type;
		}

		private void setType(PreviewType type) {
			this.type = type;
			setMessage(new LiteralText("< ").append(type.getText()).append(new LiteralText(" >")));
		}

		@Override
		public void onClick(double mouseX, double mouseY) {
			setType(PreviewType.values()[(type.ordinal() + 1) % PreviewType.values().length]);
			updateChiselData();
		}

		public void onRightClick(double mouseX, double mouseY) {
			int len = PreviewType.values().length;
			setType(PreviewType.values()[(type.ordinal() - 1 + len) % len]);
			updateChiselData();
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) { // TODO
			boolean handled = super.mouseClicked(mouseX, mouseY, button);
			if (handled) {
				return true;
			}
			if (this.active && this.visible) {
				if (button == GLFW.GLFW_MOUSE_BUTTON_2) {
					if (this.clicked(mouseX, mouseY)) {
						this.playDownSound(MinecraftClient.getInstance().getSoundManager());
						this.onRightClick(mouseX, mouseY);
						return true;
					}
				}
			}
			return false;
		}
	}

	private class RotateButton extends AbstractButtonWidget {
		private boolean rotate = true;

		public RotateButton(int x, int y) {
			super(x, y, 16, 16, LiteralText.EMPTY);
		}

		public boolean getRotate() {
			return rotate;
		}

		@Override
		public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			HitechChiselScreen.this.client.getTextureManager().bindTexture(TEXTURE);
			float a = isMouseOver(mouseX, mouseY) ? 1 : 0.2f;
			int u = rotate ? 0 : 16;
			int v = 238;

			RenderSystem.color4f(1, 1, 1, a);
			RenderSystem.enableBlend();
			RenderSystem.enableDepthTest();
			setZOffset(100);
			drawTexture(matrices, x, y, u, v, 16, 16);
			setZOffset(0);
			RenderSystem.color4f(1, 1, 1, 1);
		}

		@Override
		public void onClick(double mouseX, double mouseY) {
			rotate = !rotate;
			updateChiselData();
		}
	}

	private static class DummyBlockRenderView implements BlockRenderView {
		private final HitechChiselScreen screen;
		private final LightingProvider light = new LightingProvider(new ChunkProvider() {
			@Override
			@Nullable
			public BlockView getChunk(int chunkX, int chunkY) {
				return DummyBlockRenderView.this;
			}

			@Override
			public BlockView getWorld() {
				return DummyBlockRenderView.this;
			}
		}, true, true);
		private BlockState state = Blocks.AIR.getDefaultState();

		public DummyBlockRenderView(HitechChiselScreen screen) {
			this.screen = screen;
		}

		public void setState(BlockState state) {
			this.state = state;
		}

		@Override
		@Nullable
		public BlockEntity getBlockEntity(BlockPos pos) {
			return null;
		}

		@Override
		public BlockState getBlockState(BlockPos pos) {
			return screen.previewButton.getType().getPositions().contains(pos) ? state : Blocks.AIR.getDefaultState();
		}

		@Override
		public FluidState getFluidState(BlockPos pos) {
			return Fluids.EMPTY.getDefaultState();
		}

		@Override
		public float getBrightness(Direction direction, boolean shaded) {
			return 1;
		}

		@Override
		public LightingProvider getLightingProvider() {
			return light;
		}

		@Override
		public int getColor(BlockPos pos, ColorResolver colorResolver) {
			return -1;
		}
	}
}
