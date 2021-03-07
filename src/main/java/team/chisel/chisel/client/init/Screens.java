package team.chisel.chisel.client.init;

import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import team.chisel.chisel.client.gui.ChiselScreen;
import team.chisel.chisel.client.gui.HitechChiselScreen;
import team.chisel.chisel.init.ScreenHandlers;
import team.chisel.chisel.inventory.ChiselScreenHandler;

public class Screens {
	public static void init() {
		ScreenRegistry.register(ScreenHandlers.NORMAL_CHISEL, (ScreenRegistry.Factory<ChiselScreenHandler, HandledScreen<ChiselScreenHandler>>) ChiselScreen::new);
		ScreenRegistry.register(ScreenHandlers.HITECH_CHISEL, HitechChiselScreen::new);
	}
}
