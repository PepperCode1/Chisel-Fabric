package team.chisel.chisel.api;

import java.util.Set;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import team.chisel.chisel.impl.CarvingGroupRegistryImpl;

public interface CarvingGroupRegistry {
	CarvingGroupRegistry INSTANCE = CarvingGroupRegistryImpl.INSTANCE;
	
	boolean register(Identifier identifier, CarvingGroup group);
	
	Set<CarvingGroup> getGroups();
	
	CarvingGroup getGroup(Identifier identifier);
	
	Identifier getIdentifier(CarvingGroup group);
	
	default CarvingGroup getGroup(BlockState blockState) {
		CarvingVariant variant = getVariant(blockState);
		return variant == null ? null : variant.getCarvingGroup();
	}
	
	default CarvingGroup getGroup(Item item) {
		CarvingVariant variant = getVariant(item);
		return variant == null ? null : variant.getCarvingGroup();
	}

	CarvingVariant getVariant(BlockState blockState);
	
	CarvingVariant getVariant(Item item);
}
