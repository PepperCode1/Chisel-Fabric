package team.chisel.chisel.client;

import net.fabricmc.api.ClientModInitializer;
import team.chisel.chisel.client.init.Events;
import team.chisel.chisel.client.init.ClientNetwork;
import team.chisel.chisel.client.init.Screens;
import team.chisel.chisel.client.texture.TextureTypeAR;
import team.chisel.ctm.api.client.TextureTypeRegistry;

public class ChiselClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		Screens.init();
		ClientNetwork.init();
		Events.init();
		TextureTypeRegistry.INSTANCE.register("AR", new TextureTypeAR());
	}
}
