package team.chisel.chisel.client.gui;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import team.chisel.chisel.api.ChiselMode;
import team.chisel.chisel.util.Point2i;

public class ChiselModeButton extends ButtonWidget {
	@NotNull
	private final ChiselMode mode;

	public ChiselModeButton(int x, int y, @NotNull ChiselMode mode, PressAction onPress) {
		super(x, y, 20, 20, LiteralText.EMPTY, onPress);
		this.mode = mode;
	}

	public ChiselModeButton(int x, int y, @NotNull ChiselMode mode, PressAction onPress, TooltipSupplier tooltipSupplier) {
		super(x, y, 20, 20, LiteralText.EMPTY, onPress, tooltipSupplier);
		this.mode = mode;
	}

	public ChiselMode getMode() {
		return mode;
	}

	@Override
	protected void renderBg(MatrixStack matrices, MinecraftClient client, int mouseX, int mouseY) {
		super.renderBg(matrices, client, mouseX, mouseY);
		client.getTextureManager().bindTexture(mode.getSpriteSheet());
		Point2i uv = mode.getSpritePos();
		drawTexture(matrices, x + 4, y + 4, 12, 12, uv.getX(), uv.getY(), 24, 24, 256, 256);
	}
}
