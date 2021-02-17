package team.chisel.chisel.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import team.chisel.chisel.api.CarvingGroup;
import team.chisel.chisel.api.CarvingGroupRegistry;
import team.chisel.chisel.api.CarvingVariant;

public class CarvingGroupRegistryImpl implements CarvingGroupRegistry {
	public static final CarvingGroupRegistryImpl INSTANCE = new CarvingGroupRegistryImpl();
	
	private Map<Identifier, CarvingGroup> carvingGroups = new HashMap<>();

	@Override
	public boolean register(Identifier identifier, CarvingGroup carvingGroup) {
		if (!carvingGroups.containsKey(identifier)) {
			carvingGroups.put(identifier, carvingGroup);
			return true;
		}
		return false;
	}
	
	@Override
	public Set<CarvingGroup> getGroups() {
		return Set.copyOf(carvingGroups.values());
	}

	@Override
	public CarvingGroup getGroup(Identifier identifier) {
		return carvingGroups.get(identifier);
	}
	
	@Override
	public Identifier getIdentifier(CarvingGroup group) {
		for (Entry<Identifier, CarvingGroup> e : carvingGroups.entrySet()) {
			if (group.equals(e.getValue())) {
				return e.getKey();
			}
		}
		return null;
	}
	
	@Override
	public CarvingVariant getVariant(BlockState blockState) {
		for (CarvingGroup group : carvingGroups.values()) {
			for (CarvingVariant variant : group.getCarvingVariants()) {
				if (blockState.equals(variant.getBlockState())) {
					return variant;
				}
			}
		}
		return null;
	}

	@Override
	public CarvingVariant getVariant(Item item) {
		for (CarvingGroup group : carvingGroups.values()) {
			for (CarvingVariant variant : group.getCarvingVariants()) {
				if (item.equals(variant.getItem())) {
					return variant;
				}
			}
		}
		return null;
	}
}
