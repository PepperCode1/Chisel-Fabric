package team.chisel.chisel.init;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import team.chisel.chisel.Chisel;
import team.chisel.chisel.inventory.ChiselScreenHandler;
import team.chisel.chisel.inventory.HitechChiselScreenHandler;

public class ScreenHandlers {
	public static final ScreenHandlerType<ChiselScreenHandler> NORMAL_CHISEL = ScreenHandlerRegistry.registerExtended(new Identifier(Chisel.MOD_ID, "normal_chisel"), (syncId, inventory, buf) -> {
		Hand hand = Hand.values()[buf.readInt()];
		return createNormalChisel(syncId, inventory, hand);
	});
	public static final ScreenHandlerType<HitechChiselScreenHandler> HITECH_CHISEL = ScreenHandlerRegistry.registerExtended(new Identifier(Chisel.MOD_ID, "hitech_chisel"), (syncId, inventory, buf) -> {
		Hand hand = Hand.values()[buf.readInt()];
		return createHitechChisel(syncId, inventory, hand);
	});
	
	public static ChiselScreenHandler createNormalChisel(int syncId, PlayerInventory inventory, Hand hand) {
		return new ChiselScreenHandler(NORMAL_CHISEL, syncId, inventory, hand);
	}
	
	public static HitechChiselScreenHandler createHitechChisel(int syncId, PlayerInventory inventory, Hand hand) {
		return new HitechChiselScreenHandler(HITECH_CHISEL, syncId, inventory, hand);
	}

	public static void init() {
	}
}
