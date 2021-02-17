package team.chisel.chisel.carving;

import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

public interface DynamicResourceRegistry {
	void init();
	
	void register(ResourceType type, Identifier identifier, String data);
}
