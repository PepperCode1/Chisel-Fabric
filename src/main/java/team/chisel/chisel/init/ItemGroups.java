package team.chisel.chisel.init;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import team.chisel.chisel.Chisel;

public class ItemGroups {
	public static final ItemGroup CHISEL = FabricItemGroupBuilder.create(new Identifier(Chisel.MOD_ID, "chisel"))
			.icon(() -> new ItemStack(Items.IRON_CHISEL))
			.build();
	
	public static void init() {
	}
}
