package team.chisel.chisel.carving;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.Identifier;

public class BlockModelTemplateBuilder {
	private Identifier parent;
	private Map<String, String> textures = new HashMap<>();
	
	public BlockModelTemplateBuilder(Identifier parent) {
		this.parent = parent;
	}
	
	public BlockModelTemplateBuilder addTexture(String key, String value) {
		textures.put(key, value);
		return this;
	}
	
	public BlockModelTemplate build() {
		return (textureLocation, groupName, variantName) -> {
			String textureJson = "";
			boolean first = true;
			for (String key : textures.keySet()) {
				String texture = new Identifier(textureLocation.getNamespace(), fillPlaceholders(textures.get(key), textureLocation, groupName, variantName)).toString();
				textureJson += (first ? "" : ",")+"\""+key+"\":\""+texture+"\"";
				first = false;
			}
			String json =
				"{"+
				"\"parent\":\""+parent.toString()+"\","+
				"\"textures\":{"+
				textureJson+
				"}"+
				"}"
				;
			return json;
		};
	}
	
	private static String fillPlaceholders(String string, Identifier textureLocation, String groupName, String variantName) {
		return string
				.replace("{texture}", textureLocation.getPath())
				//.replace("{group}", groupName)
				.replace("{variant}", variantName);
	}
}
