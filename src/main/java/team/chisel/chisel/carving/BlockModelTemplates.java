package team.chisel.chisel.carving;

import net.minecraft.util.Identifier;

public class BlockModelTemplates {
	public static final BlockModelTemplate CUBE_ALL = new BlockModelTemplateBuilder(new Identifier("block/cube_all"))
			.addTexture("all", "{path}/{texture}")
			.build();
	
	public static final BlockModelTemplate CUBE_ALL_EXTRA = new BlockModelTemplateBuilder(new Identifier("block/cube_all"))
			.addTexture("all", "{path}/extra/{texture}")
			.build();
	
	public static final BlockModelTemplate CUBE_BOTTOM_TOP = new BlockModelTemplateBuilder(new Identifier("block/cube_bottom_top"))
			.addTexture("side", "{path}/{texture}_side")
			.addTexture("top", "{path}/{texture}_top")
			.addTexture("bottom", "{path}/{texture}_bottom")
			.build();
	
	public static final BlockModelTemplate CUBE_COLUMN = new BlockModelTemplateBuilder(new Identifier("block/cube_column"))
			.addTexture("side", "{path}/{texture}_side")
			.addTexture("end", "{path}/{texture}_top")
			.build();
	
	public static final BlockModelTemplate CUBE_ALL_CTM = new BlockModelTemplateBuilder(new Identifier("chisel", "block/cube_all_ctm"))
			.addTexture("all", "{path}/{texture}")
			.addTexture("ctm", "{path}/{texture}_ctm")
			.build();
	
	public static final BlockModelTemplate BROWNSTONE_DOUBLE_SLAB = new BlockModelTemplateBuilder(new Identifier("block/cube_column"))
			.addTexture("side", "{path}/{texture}_side")
			.addTexture("end", "{path}/block")
			.build();
	
	public static final BlockModelTemplate BROWNSTONE_WEATHERED_DOUBLE_SLAB = new BlockModelTemplateBuilder(new Identifier("block/cube_column"))
			.addTexture("side", "{path}/{texture}_side")
			.addTexture("end", "{path}/weathered_block")
			.build();
	
	public static final BlockModelTemplate BROWNSTONE_WEATHERED_HALF = new BlockModelTemplateBuilder(new Identifier("block/cube_bottom_top"))
			.addTexture("side", "{path}/{texture}_side")
			.addTexture("top", "{path}/default")
			.addTexture("bottom", "{path}/weathered")
			.build();
	
	public static final BlockModelTemplate BROWNSTONE_WEATHERED_BLOCK_HALF = new BlockModelTemplateBuilder(new Identifier("block/cube_bottom_top"))
			.addTexture("side", "{path}/{texture}_side")
			.addTexture("top", "{path}/block")
			.addTexture("bottom", "{path}/weathered_block")
			.build();
}
