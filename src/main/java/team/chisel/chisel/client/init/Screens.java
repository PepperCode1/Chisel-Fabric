package team.chisel.chisel.client.init;

import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import team.chisel.chisel.client.gui.ChiselScreen;
import team.chisel.chisel.client.gui.HitechChiselScreen;
import team.chisel.chisel.init.ScreenHandlers;

public class Screens {
	public static void init() {
		ScreenRegistry.register(ScreenHandlers.NORMAL_CHISEL, ChiselScreen::new);
		ScreenRegistry.register(ScreenHandlers.HITECH_CHISEL, HitechChiselScreen::new);
	}
}
