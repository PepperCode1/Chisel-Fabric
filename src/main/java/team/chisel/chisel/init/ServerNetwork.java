package team.chisel.chisel.init;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import team.chisel.chisel.api.ChiselItem;
import team.chisel.chisel.api.ChiselMode;
import team.chisel.chisel.api.ChiselModeRegistry;
import team.chisel.chisel.client.init.ClientNetwork;
import team.chisel.chisel.impl.ChiselModeImpl;
import team.chisel.chisel.inventory.HitechChiselScreenHandler;
import team.chisel.chisel.util.ChiselNBT;

public class ServerNetwork {
	public static void init() {
		ServerPlayNetworking.registerGlobalReceiver(ClientNetwork.CHISEL_MODE, (server, player, handler, buf, responseSender) -> {
			int slot = buf.readInt();
			String modeName = buf.readString(256);

			server.execute(() -> {
				ChiselMode mode = ChiselModeRegistry.INSTANCE.getModeByName(modeName);
				if (mode == null) {
					mode = ChiselModeImpl.SINGLE;
				}
				ItemStack stack = player.inventory.getStack(slot);
				if (stack.getItem() instanceof ChiselItem && ((ChiselItem) stack.getItem()).supportsMode(player, stack, mode)) {
					ChiselNBT.setChiselMode(stack, mode);
				}
			});
		});

		ServerPlayNetworking.registerGlobalReceiver(ClientNetwork.CHISEL_BUTTON, (server, player, handler, buf, responseSender) -> {
			int[] slots = buf.readIntArray();

			server.execute(() -> {
				if (player.currentScreenHandler instanceof HitechChiselScreenHandler) {
					((HitechChiselScreenHandler) player.currentScreenHandler).chiselAll(player, slots);
				}
			});
		});

		ServerPlayNetworking.registerGlobalReceiver(ClientNetwork.HITECH_SETTINGS, (server, player, handler, buf, responseSender) -> {
			int chiselSlot = buf.readInt();
			int type = buf.readInt();
			boolean rotate = buf.readBoolean();

			server.execute(() -> {
				ItemStack stack = player.inventory.getStack(chiselSlot);
				if (stack.getItem() instanceof ChiselItem) {
					ChiselNBT.setHitechType(stack, type);
					ChiselNBT.setHitechRotate(stack, rotate);
				}
			});
		});
	}
}
