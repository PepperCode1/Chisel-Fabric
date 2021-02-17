package team.chisel.chisel.carving;

import net.minecraft.util.Identifier;

public class BlockModelTemplates {
	public static final BlockModelTemplate CUBE_ALL = new BlockModelTemplateBuilder(new Identifier("minecraft", "block/cube_all"))
			.addTexture("all", "{texture}/{variant}")
			.build();
	
	public static final BlockModelTemplate CUBE_ALL_EXTRA = new BlockModelTemplateBuilder(new Identifier("minecraft", "block/cube_all"))
			.addTexture("all", "{texture}/extra/{variant}")
			.build();
	
	public static final BlockModelTemplate CUBE_BOTTOM_TOP = new BlockModelTemplateBuilder(new Identifier("minecraft", "block/cube_bottom_top"))
			.addTexture("side", "{texture}/{variant}_side")
			.addTexture("top", "{texture}/{variant}_top")
			.addTexture("bottom", "{texture}/{variant}_bottom")
			.build();
	
	public static final BlockModelTemplate CUBE_COLUMN = new BlockModelTemplateBuilder(new Identifier("minecraft", "block/cube_column"))
			.addTexture("side", "{texture}/{variant}_side")
			.addTexture("end", "{texture}/{variant}_top")
			.build();
	
	public static final BlockModelTemplate BROWNSTONE_DOUBLE_SLAB = new BlockModelTemplateBuilder(new Identifier("minecraft", "block/cube_column"))
			.addTexture("side", "{texture}/{variant}_side")
			.addTexture("end", "{texture}/block")
			.build();
	
	public static final BlockModelTemplate BROWNSTONE_WEATHERED_DOUBLE_SLAB = new BlockModelTemplateBuilder(new Identifier("minecraft", "block/cube_column"))
			.addTexture("side", "{texture}/{variant}_side")
			.addTexture("end", "{texture}/weathered_block")
			.build();
	
	public static final BlockModelTemplate BROWNSTONE_WEATHERED_HALF = new BlockModelTemplateBuilder(new Identifier("minecraft", "block/cube_bottom_top"))
			.addTexture("side", "{texture}/{variant}_side")
			.addTexture("top", "{texture}/default")
			.addTexture("bottom", "{texture}/weathered")
			.build();
	
	public static final BlockModelTemplate BROWNSTONE_WEATHERED_BLOCK_HALF = new BlockModelTemplateBuilder(new Identifier("minecraft", "block/cube_bottom_top"))
			.addTexture("side", "{texture}/{variant}_side")
			.addTexture("top", "{texture}/block")
			.addTexture("bottom", "{texture}/weathered_block")
			.build();
	
	public static final BlockModelTemplate LEGACY_CARPET = new BlockModelTemplateBuilder(new Identifier("minecraft", "block/carpet"))
			.addTexture("wool", "{texture}/legacy/{variant}")
			.build();
	
	public static final BlockModelTemplate LLAMA_CARPET = new BlockModelTemplateBuilder(new Identifier("minecraft", "block/carpet"))
			.addTexture("wool", "{texture}/llama/{variant}")
			.build();
}
