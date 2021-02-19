package team.chisel.chisel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import team.chisel.chisel.init.CarvingGroups;
import team.chisel.chisel.init.Events;
import team.chisel.chisel.init.ItemGroups;
import team.chisel.chisel.init.Items;
import team.chisel.chisel.init.ScreenHandlers;
import team.chisel.chisel.init.ServerNetwork;
import team.chisel.chisel.init.SoundEvents;

public class Chisel implements ModInitializer {
	public static final String MOD_ID = "chisel";
	public static final String RESOURCE_NAMESPACE = "chisel";
	public static final Logger LOGGER = LogManager.getLogger("chisel");

	@Override
	public void onInitialize() {
		SoundEvents.init();
		ItemGroups.init();
		Items.init();
		ScreenHandlers.init();
		ServerNetwork.init();
		Events.init();
		CarvingGroups.init();
	}
}
