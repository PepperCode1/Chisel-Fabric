package team.chisel.chisel.init;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import team.chisel.chisel.Chisel;
import team.chisel.chisel.item.ChiselItemImpl;
import team.chisel.chisel.item.ChiselItemImpl.ChiselType;

public class Items {
	public static final Item IRON_CHISEL = register(new Identifier(Chisel.MOD_ID, "iron_chisel"), new ChiselItemImpl(ChiselType.IRON, new Item.Settings().group(ItemGroups.CHISEL)));
	public static final Item DIAMOND_CHISEL = register(new Identifier(Chisel.MOD_ID, "diamond_chisel"), new ChiselItemImpl(ChiselType.DIAMOND, new Item.Settings().group(ItemGroups.CHISEL)));
	public static final Item HITECH_CHISEL = register(new Identifier(Chisel.MOD_ID, "hitech_chisel"), new ChiselItemImpl(ChiselType.HITECH, new Item.Settings().group(ItemGroups.CHISEL)));
	public static final Item OFFSET_TOOL = register(new Identifier(Chisel.MOD_ID, "offset_tool"), new Item(new Item.Settings().group(ItemGroups.CHISEL)));
	
	private static Item register(Identifier identifier, Item item) {
		return Registry.register(Registry.ITEM, identifier, item);
	}
	
	public static void init() {
	}
}
