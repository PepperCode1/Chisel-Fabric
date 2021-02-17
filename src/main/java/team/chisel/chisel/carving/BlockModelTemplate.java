package team.chisel.chisel.carving;

import net.minecraft.util.Identifier;

public interface BlockModelTemplate {
	String getJson(Identifier textureLocation, String groupName, String variantName);
}
