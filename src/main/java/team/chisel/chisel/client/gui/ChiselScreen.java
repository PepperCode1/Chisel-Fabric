package team.chisel.chisel.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.util.Rect2i;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import team.chisel.chisel.Chisel;
import team.chisel.chisel.api.ChiselItem;
import team.chisel.chisel.api.ChiselMode;
import team.chisel.chisel.api.ChiselModeRegistry;
import team.chisel.chisel.client.init.ClientNetwork;
import team.chisel.chisel.inventory.ChiselInputSlot;
import team.chisel.chisel.inventory.ChiselScreenHandler;
import team.chisel.chisel.util.ChiselNBT;

// TODO: Make AbstractChiselScreen
public class ChiselScreen<T extends ChiselScreenHandler> extends HandledScreen<T> {
	private static final Identifier TEXTURE = new Identifier(Chisel.MOD_ID, "textures/gui/chisel.png");

	protected final PlayerEntity player;

	public ChiselScreen(T screenHandler, PlayerInventory inventory, Text title) {
		super(screenHandler, inventory, title);
		player = inventory.player;
		backgroundWidth = 252;
		backgroundHeight = 202;
	}

	@Override
	public void init() {
		super.init();

		ItemStack chisel = handler.getChisel();
		ChiselItem chiselItem = (ChiselItem) chisel.getItem();
		ChiselMode currentMode = ChiselNBT.getChiselMode(chisel);

		Rect2i buttonArea = getModeButtonArea();
		int buttonsPerRow = buttonArea.getWidth() / 20;
		int padding = (buttonArea.getWidth() - (buttonsPerRow * 20)) / buttonsPerRow;
		int id = 0;

		for (ChiselMode mode : ChiselModeRegistry.INSTANCE.getAllModes()) {
			if (chiselItem.supportsMode(player, chisel, mode)) {
				int buttonX = buttonArea.getX() + (padding / 2) + ((id % buttonsPerRow) * (20 + padding));
				int buttonY = buttonArea.getY() + ((id / buttonsPerRow) * (20 + padding));
				ChiselModeButton button = new ChiselModeButton(buttonX, buttonY, mode,
						(pressed) -> {
							ChiselMode mode1 = ((ChiselModeButton) pressed).getMode();

							ChiselNBT.setChiselMode(chisel, mode1);
							ClientPlayNetworking.getSender().sendPacket(ClientNetwork.createChiselModePacket(handler.getChiselSlot(), mode1));

							pressed.active = false;
							for (AbstractButtonWidget other : buttons) {
								if (other != pressed && other instanceof ChiselModeButton) {
									other.active = true;
								}
							}
						},
						(hovered, matrices, mouseX, mouseY) -> {
							ChiselMode mode1 = ((ChiselModeButton) hovered).getMode();
							List<OrderedText> lines = new ArrayList<>();
							lines.add(mode1.getNameText().asOrderedText());
							lines.addAll(textRenderer.wrapLines(new TranslatableText(mode1.getDescriptionKey()).formatted(Formatting.GRAY), width - mouseX - 20));
							renderOrderedTooltip(matrices, lines, mouseX, mouseY);
						}
				);
				if (mode == currentMode) {
					button.active = false;
				}
				addButton(button);
				id++;
			}
		}
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		drawMouseoverTooltip(matrices, mouseX, mouseY);
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		client.getTextureManager().bindTexture(TEXTURE);
		int x = (width - backgroundWidth) / 2;
		int y = (height - backgroundHeight) / 2;
		drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

		Slot inputSlot = handler.getInputSlot();
		if (inputSlot.getStack().isEmpty()) {
			drawSlotOverlay(this, matrices, x + 14, y + 14, inputSlot, 0, backgroundHeight, 60);
		}
	}

	@Override
	protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		// TODO: Fix title
		List<OrderedText> lines = textRenderer.wrapLines(title, 40);
		int y = 60;
		for (OrderedText line : lines) {
			textRenderer.draw(matrices, line, 32 - textRenderer.getWidth(line) / 2, y, 0x404040);
			y += 10;
		}
	}

	@Override
	protected void drawSlot(MatrixStack matrices, Slot slot) {
		if (slot instanceof ChiselInputSlot) {
			RenderSystem.pushMatrix();
			RenderSystem.scalef(2.0F, 2.0F, 1.0F);
			RenderSystem.translatef(-16.0F, -16.0F, 0.0F);
			super.drawSlot(matrices, slot);
			RenderSystem.popMatrix();
		} else {
			super.drawSlot(matrices, slot);
		}
	}

	protected Rect2i getModeButtonArea() {
		int down = 73;
		int padding = 7;
		return new Rect2i(x + padding, y + down + padding, 50, backgroundHeight - down - (padding * 2));
	}

	public static void drawSlotOverlay(HandledScreen<?> screen, MatrixStack matrices, int x, int y, Slot slot, int u, int v, int padding) {
		padding /= 2;
		screen.drawTexture(matrices, x + (slot.x - padding), y + (slot.y - padding), u, v, 18 + padding, 18 + padding);
	}
}
