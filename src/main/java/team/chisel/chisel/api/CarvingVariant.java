package team.chisel.chisel.api;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;

public interface CarvingVariant {
	CarvingGroup getCarvingGroup();
	
	String getName();
	
	BlockState getBlockState();
	
	Item getItem();
}
