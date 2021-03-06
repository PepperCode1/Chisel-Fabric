package team.chisel.chisel.carving;

import org.apache.commons.lang3.ArrayUtils;

public class VariantTemplates {
	private static class SimpleVariantTemplate implements VariantTemplate {
		private String name;
		private String textureName;
		private BlockModelTemplate blockModel;
		
		public SimpleVariantTemplate(String name, String textureName, BlockModelTemplate model) {
			this.name = name;
			this.textureName = textureName;
			this.blockModel = model;
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public String getTextureName() {
			return textureName;
		}
		
		@Override
		public BlockModelTemplate getBlockModel() {
			return blockModel;
		}
	}
	
	private static VariantTemplate create(String name) {
		return new SimpleVariantTemplate(name, name, BlockModelTemplates.CUBE_ALL);
	}
	
	private static VariantTemplate create(String name, String textureName) {
		return new SimpleVariantTemplate(name, textureName, BlockModelTemplates.CUBE_ALL);
	}
	
	private static VariantTemplate create(String name, BlockModelTemplate model) {
		return new SimpleVariantTemplate(name, name, model);
	}
	
	private static VariantTemplate create(String name, String textureName, BlockModelTemplate model) {
		return new SimpleVariantTemplate(name, textureName, model);
	}
	
	public static final VariantTemplate RAW = create("raw");
	
	public static class EXTRA_COBBLESTONE {
		public static final VariantTemplate EMBOSS = create("emboss");
		public static final VariantTemplate INDENT = create("indent");
		public static final VariantTemplate MARKER = create("marker");
		public static final VariantTemplate[] ARRAY_EXTRA_COBBLESTONE = new VariantTemplate[] {EMBOSS, INDENT, MARKER};
	}
	
	public static class Rock {
		public static final VariantTemplate ARRAY = create("array");
		public static final VariantTemplate BRAID = create("braid");
		public static final VariantTemplate CHAOTIC_BRICKS = create("bricks_chaotic");
		public static final VariantTemplate CRACKED_BRICKS = create("bricks_cracked");
		public static final VariantTemplate ENCASED_BRICKS = create("bricks_encased");
		public static final VariantTemplate SMALL_BRICKS = create("bricks_small");
		public static final VariantTemplate SOFT_BRICKS = create("bricks_soft");
		public static final VariantTemplate SOLID_BRICKS = create("bricks_solid");
		public static final VariantTemplate TRIPLE_BRICKS = create("bricks_triple");
		public static final VariantTemplate CHAOTIC_MEDIUM = create("chaotic_medium");
		public static final VariantTemplate CHAOTIC_SMALL = create("chaotic_small");
		public static final VariantTemplate CIRCULAR = create("circular");
		public static final VariantTemplate CIRCULAR_CTM = create("circular_ctm", "circular", BlockModelTemplates.CUBE_ALL_CTM);
		public static final VariantTemplate CRACKED = create("cracked");
		public static final VariantTemplate CUTS = create("cuts");
		public static final VariantTemplate DENT = create("dent");
		public static final VariantTemplate FRENCH_1 = create("french_1");
		public static final VariantTemplate FRENCH_2 = create("french_2");
		public static final VariantTemplate JELLYBEAN = create("jellybean");
		public static final VariantTemplate LAYERS = create("layers");
		public static final VariantTemplate MOSAIC = create("mosaic");
		public static final VariantTemplate ORNATE = create("ornate");
		public static final VariantTemplate PANEL = create("panel");
		public static final VariantTemplate PILLAR = create("pillar", BlockModelTemplates.CUBE_COLUMN);
		public static final VariantTemplate PRISM = create("prism");
		public static final VariantTemplate ROAD = create("road");
		public static final VariantTemplate SLANTED = create("slanted");
		public static final VariantTemplate TILES_LARGE = create("tiles_large");
		public static final VariantTemplate TILES_MEDIUM = create("tiles_medium");
		public static final VariantTemplate TILES_SMALL = create("tiles_small");
		public static final VariantTemplate TWISTED = create("twisted", BlockModelTemplates.CUBE_COLUMN);
		public static final VariantTemplate WEAVER = create("weaver");
		public static final VariantTemplate ZAG = create("zag");
		public static final VariantTemplate[] ARRAY_ROCK = new VariantTemplate[] {ARRAY, BRAID, CHAOTIC_BRICKS, CRACKED_BRICKS, ENCASED_BRICKS, SMALL_BRICKS, SOFT_BRICKS, SOLID_BRICKS, TRIPLE_BRICKS, CHAOTIC_MEDIUM, CHAOTIC_SMALL, CIRCULAR, CIRCULAR_CTM, CRACKED, CUTS, DENT, FRENCH_1, FRENCH_2, JELLYBEAN, LAYERS, MOSAIC, ORNATE, PANEL, PILLAR, PRISM, ROAD, SLANTED, TILES_LARGE, TILES_MEDIUM, TILES_SMALL, TWISTED, WEAVER, ZAG};
	}
	
	public static class Metal {
		public static final VariantTemplate BAD_GREGGY = create("bad_greggy");
		public static final VariantTemplate BOLTED = create("bolted");
		public static final VariantTemplate CAUTION = create("caution");
		public static final VariantTemplate CRATE = create("crate");
		public static final VariantTemplate MACHINE = create("machine");
		public static final VariantTemplate SCAFFOLD = create("scaffold");
		public static final VariantTemplate THERMAL = create("thermal", BlockModelTemplates.CUBE_BOTTOM_TOP);
		public static final VariantTemplate[] ARRAY_METAL = new VariantTemplate[] {CAUTION, CRATE, THERMAL, MACHINE, BAD_GREGGY, BOLTED, SCAFFOLD};
	}
	
	public static class ExtraMetal {
		public static final VariantTemplate BRICK = create("brick", BlockModelTemplates.CUBE_BOTTOM_TOP);
		public static final VariantTemplate COIN_HEADS = create("coin_heads", BlockModelTemplates.CUBE_BOTTOM_TOP);
		public static final VariantTemplate COIN_TAILS = create("coin_tails", BlockModelTemplates.CUBE_BOTTOM_TOP);
		public static final VariantTemplate CRATE_DARK = create("crate_dark", BlockModelTemplates.CUBE_BOTTOM_TOP);
		public static final VariantTemplate CRATE_LIGHT = create("crate_light", BlockModelTemplates.CUBE_BOTTOM_TOP);
		public static final VariantTemplate LARGE_INGOT = create("large_ingot", BlockModelTemplates.CUBE_BOTTOM_TOP);
		public static final VariantTemplate PLATES = create("plates", BlockModelTemplates.CUBE_BOTTOM_TOP);
		public static final VariantTemplate RIVETS = create("rivets", BlockModelTemplates.CUBE_BOTTOM_TOP);
		public static final VariantTemplate SIMPLE = create("simple", BlockModelTemplates.CUBE_BOTTOM_TOP);
		public static final VariantTemplate SMALL_INGOT = create("small_ingot", BlockModelTemplates.CUBE_BOTTOM_TOP);
		public static final VariantTemplate[] ARRAY_EXTRA_METAL = new VariantTemplate[] {BRICK, COIN_HEADS, COIN_TAILS, CRATE_DARK, CRATE_LIGHT, LARGE_INGOT, PLATES, RIVETS, SIMPLE, SMALL_INGOT};
	}
	
	public static class ExtraIron extends ExtraMetal {
		public static final VariantTemplate GEARS = create("gears", BlockModelTemplates.CUBE_COLUMN);
		public static final VariantTemplate MOON = create("moon", BlockModelTemplates.CUBE_BOTTOM_TOP);
		public static final VariantTemplate SPACE = create("space");
		public static final VariantTemplate SPACE_BLACK = create("space_black");
		public static final VariantTemplate VENTS = create("vents", BlockModelTemplates.CUBE_COLUMN);
		public static final VariantTemplate[] ARRAY_EXTRA_IRON = ArrayUtils.addAll(ARRAY_EXTRA_METAL, GEARS, MOON, SPACE, SPACE_BLACK, VENTS);
	}

	public static class Brownstone {
		public static final VariantTemplate BLOCK = create("block");
		public static final VariantTemplate BLOCKS = create("blocks");
		public static final VariantTemplate DEFAULT = create("default");
		public static final VariantTemplate DOUBLE_SLAB = create("double_slab", BlockModelTemplates.BROWNSTONE_DOUBLE_SLAB);
		public static final VariantTemplate WEATHERED = create("weathered");
		public static final VariantTemplate WEATHERED_BLOCK = create("weathered_block");
		public static final VariantTemplate WEATHERED_BLOCK_HALF = create("weathered_block_half", BlockModelTemplates.BROWNSTONE_WEATHERED_BLOCK_HALF);
		public static final VariantTemplate WEATHERED_BLOCKS = create("weathered_blocks");
		public static final VariantTemplate WEATHERED_DOUBLE_SLAB = create("weathered_double_slab", BlockModelTemplates.BROWNSTONE_WEATHERED_DOUBLE_SLAB);
		public static final VariantTemplate WEATHERED_HALF = create("weathered_half", BlockModelTemplates.BROWNSTONE_WEATHERED_HALF);
		public static final VariantTemplate[] ARRAY_BROWNSTONE = new VariantTemplate[] {BLOCK, BLOCKS, DEFAULT, DOUBLE_SLAB, WEATHERED, WEATHERED_BLOCK, WEATHERED_BLOCK_HALF, WEATHERED_BLOCKS, WEATHERED_DOUBLE_SLAB, WEATHERED_HALF};
	}
	
//	public static final VariantTemplate LEGACY = create("legacy", BlockModelTemplates.LEGACY_CARPET);
//	public static final VariantTemplate LLAMA = create("llama", BlockModelTemplates.LLAMA_CARPET);
//	public static final VariantTemplate[] ARRAY_CARPET = new VariantTemplate[] {LEGACY, LLAMA};
	
	public static class Cloud {
		public static final VariantTemplate CLOUD = create("cloud");
		public static final VariantTemplate LARGE = create("large");
		public static final VariantTemplate SMALL = create("small");
		public static final VariantTemplate VERTICAL = create("vertical");
		public static final VariantTemplate GRID = create("grid");
		public static final VariantTemplate[] ARRAY_CLOUD = new VariantTemplate[] {CLOUD, LARGE, SMALL, VERTICAL, GRID};
	}

	public static class Diamond {
		public static final VariantTemplate BISMUTH = create("bismuth");
		public static final VariantTemplate CELLS = create("cells");
		public static final VariantTemplate CRUSHED = create("crushed");
		public static final VariantTemplate EMBOSSED = create("embossed", BlockModelTemplates.CUBE_BOTTOM_TOP);
		public static final VariantTemplate FOUR = create("four");
		public static final VariantTemplate FOUR_ORNATE = create("four_ornate");
		public static final VariantTemplate GEM = create("gem", BlockModelTemplates.CUBE_BOTTOM_TOP);
		public static final VariantTemplate ORNATE_LAYER = create("ornate_layer");
		public static final VariantTemplate SIMPLE = create("simple", BlockModelTemplates.CUBE_BOTTOM_TOP);
		public static final VariantTemplate SPACE = create("space");
		public static final VariantTemplate SPACE_BLACK = create("space_black");
		public static final VariantTemplate ZELDA = create("zelda");
		public static final VariantTemplate[] ARRAY_DIAMOND = new VariantTemplate[] {BISMUTH, CELLS, CRUSHED, EMBOSSED, FOUR, FOUR_ORNATE, GEM, ORNATE_LAYER, SIMPLE, SPACE, SPACE_BLACK, ZELDA};
	}
	
	public static class Emerald {
		public static final VariantTemplate CELL = create("cell");
		public static final VariantTemplate CELL_BISMUTH = create("cell_bismuth");
		public static final VariantTemplate CHUNK = create("chunk");
		public static final VariantTemplate CIRCLE = create("circle");
		public static final VariantTemplate FOUR = create("four");
		public static final VariantTemplate FOUR_ORNATE = create("four_ornate");
		public static final VariantTemplate GOLD_BORDER = create("gold_border");
		public static final VariantTemplate MASONRY = create("masonry");
		public static final VariantTemplate ORNATE = create("ornate");
		public static final VariantTemplate PANEL = create("panel");
		public static final VariantTemplate PANEL_CLASSIC = create("panel_classic");
		public static final VariantTemplate PRISMATIC = create("prismatic");
		public static final VariantTemplate SMOOTH = create("smooth");
		public static final VariantTemplate ZELDA = create("zelda");
		public static final VariantTemplate[] ARRAY_EMERALD = new VariantTemplate[] {CELL, CELL_BISMUTH, CHUNK, CIRCLE, FOUR, FOUR_ORNATE, GOLD_BORDER, MASONRY, ORNATE, PANEL, PANEL_CLASSIC, PRISMATIC, SMOOTH, ZELDA};
	}
	
	public static class Planks {
		public static final VariantTemplate BLINDS = create("blinds");
		public static final VariantTemplate CHAOTIC = create("chaotic");
		public static final VariantTemplate CHAOTIC_HORIZONTAL = create("chaotic_horizontal");
		public static final VariantTemplate CLEAN = create("clean");
		public static final VariantTemplate CRATE = create("crate");
		public static final VariantTemplate CRATE_FANCY = create("crate_fancy");
		public static final VariantTemplate CRATE_X = create("crate_x");
		public static final VariantTemplate DOUBLE_SIDE = create("double_side");
		public static final VariantTemplate DOUBLE_TOP = create("double_top");
		public static final VariantTemplate FANCY = create("fancy");
		public static final VariantTemplate LARGE = create("large");
		public static final VariantTemplate PANEL_NAILS = create("panel_nails");
		public static final VariantTemplate PARQUET = create("parquet");
		public static final VariantTemplate SHORT = create("short");
		public static final VariantTemplate VERTICAL = create("vertical");
		public static final VariantTemplate VERTICAL_UNEVEN = create("vertical_uneven");
		public static final VariantTemplate[] ARRAY_PLANKS = new VariantTemplate[] {BLINDS, CHAOTIC, CHAOTIC_HORIZONTAL, CLEAN, CRATE, CRATE_FANCY, CRATE_X, DOUBLE_SIDE, DOUBLE_TOP, FANCY, LARGE, PANEL_NAILS, PARQUET, SHORT, VERTICAL, VERTICAL_UNEVEN};
	}
	
	public static class Dirt {
		public static final VariantTemplate BRICKS = create("bricks");
		public static final VariantTemplate BRICKS2 = create("bricks2");
		public static final VariantTemplate BRICKS3 = create("bricks3");
		public static final VariantTemplate BRICKS_DIRT2_TOP = create("bricks_dirt2_top");
		public static final VariantTemplate CHUNKY = create("chunky");
		public static final VariantTemplate COBBLE = create("cobble");
		public static final VariantTemplate HAPPY = create("happy");
		public static final VariantTemplate HORIZONTAL = create("horizontal");
		public static final VariantTemplate HOR_TOP = create("hor_top");
		public static final VariantTemplate LAYERS = create("layers");
		public static final VariantTemplate NETHER_BRICKS = create("nether_bricks");
		public static final VariantTemplate PLATE = create("plate");
		public static final VariantTemplate REINFORCED = create("reinforced");
		public static final VariantTemplate REINFORCED_COBBLE = create("reinforced_cobble");
		public static final VariantTemplate VERTICAL_TOP = create("vertical_top");
		public static final VariantTemplate VERT_TOP = create("vert_top");
		public static final VariantTemplate[] ARRAY_DIRT = new VariantTemplate[] {BRICKS, BRICKS2, BRICKS3, BRICKS_DIRT2_TOP, CHUNKY, COBBLE, HAPPY, HORIZONTAL, HOR_TOP, LAYERS, NETHER_BRICKS, PLATE, REINFORCED, REINFORCED_COBBLE, VERTICAL_TOP, VERT_TOP};
	}
	
	public static class Lapis {
		public static final VariantTemplate CHUNKY = create("chunky");
		public static final VariantTemplate MASONRY = create("masonry");
		public static final VariantTemplate ORNATE = create("ornate");
		public static final VariantTemplate ORNATE_LAYER = create("ornate_layer");
		public static final VariantTemplate PANEL = create("panel");
		public static final VariantTemplate SHINY = create("shiny");
		public static final VariantTemplate SMOOTH = create("smooth");
		public static final VariantTemplate TILE = create("tile");
		public static final VariantTemplate ZELDA = create("zelda");
		public static final VariantTemplate[] ARRAY_LAPIS = new VariantTemplate[] {CHUNKY, MASONRY, ORNATE, ORNATE_LAYER, PANEL, SHINY, SMOOTH, TILE, ZELDA};
	}
	
	public static class NetherBricks {
		public static final VariantTemplate BRINSTAR = create("brinstar");
		public static final VariantTemplate CLASSICSPATTER = create("classicspatter");
		public static final VariantTemplate FANCY = create("fancy");
		public static final VariantTemplate GUTS = create("guts");
		public static final VariantTemplate GUTSDARK = create("gutsdark");
		public static final VariantTemplate GUTSSMALL = create("gutssmall");
		public static final VariantTemplate LAVABRINSTAR = create("lavabrinstar");
		public static final VariantTemplate LAVABROWN = create("lavabrown");
		public static final VariantTemplate LAVAOBSIDIAN = create("lavaobsidian");
		public static final VariantTemplate LAVASTONEDARK = create("lavastonedark");
		public static final VariantTemplate MEAT = create("meat");
		public static final VariantTemplate MEATRED = create("meatred");
		public static final VariantTemplate MEATREDSMALL = create("meatredsmall");
		public static final VariantTemplate MEATSMALL = create("meatsmall");
		public static final VariantTemplate RED = create("red");
		public static final VariantTemplate REDSMALL = create("redsmall");
		public static final VariantTemplate[] ARRAY_NETHER_BRICKS = new VariantTemplate[] {BRINSTAR, CLASSICSPATTER, FANCY, GUTS, GUTSDARK, GUTSSMALL, LAVABRINSTAR, LAVABROWN, LAVAOBSIDIAN, LAVASTONEDARK, MEAT, MEATRED, MEATREDSMALL, MEATSMALL, RED, REDSMALL};
	}
	
	public static class Netherrack {
		public static final VariantTemplate BLOODGRAVEL = create("bloodgravel");
		public static final VariantTemplate BLOODROCK = create("bloodrock");
		public static final VariantTemplate BLOODROCKGREY = create("bloodrockgrey");
		public static final VariantTemplate BRINSTAR = create("brinstar");
		public static final VariantTemplate BRINSTARSHALE = create("brinstarshale");
		public static final VariantTemplate CLASSIC = create("classic");
		public static final VariantTemplate CLASSICSPATTER = create("classicspatter");
		public static final VariantTemplate GUTS = create("guts");
		public static final VariantTemplate GUTSDARK = create("gutsdark");
		public static final VariantTemplate MEAT = create("meat");
		public static final VariantTemplate MEATRED = create("meatred");
		public static final VariantTemplate MEATROCK = create("meatrock");
		public static final VariantTemplate RED = create("red");
		public static final VariantTemplate WELLS = create("wells");
		public static final VariantTemplate[] ARRAY_NETHERRACK = new VariantTemplate[] {BLOODGRAVEL, BLOODROCK, BLOODROCKGREY, BRINSTAR, BRINSTARSHALE, CLASSIC, CLASSICSPATTER, GUTS, GUTSDARK, MEAT, MEATRED, MEATROCK, RED, WELLS};
	}
	
	public static class Obsidian {
		public static final VariantTemplate BLOCKS = create("blocks");
		public static final VariantTemplate CHISELED = create("chiseled", BlockModelTemplates.CUBE_COLUMN);
		public static final VariantTemplate CHUNKS = create("chunks");
		public static final VariantTemplate CRATE = create("crate", BlockModelTemplates.CUBE_BOTTOM_TOP);
		public static final VariantTemplate CRYSTAL = create("crystal");
		public static final VariantTemplate GREEK = create("greek", BlockModelTemplates.CUBE_COLUMN);
		public static final VariantTemplate GROWTH = create("growth");
		public static final VariantTemplate MAP_A = create("map_a");
		public static final VariantTemplate MAP_B = create("map_b");
		public static final VariantTemplate PANEL = create("panel");
		public static final VariantTemplate PANEL_LIGHT = create("panel_light");
		public static final VariantTemplate PANEL_SHINY = create("panel_shiny");
		public static final VariantTemplate PILLAR_QUARTZ = create("pillar_quartz", BlockModelTemplates.CUBE_COLUMN);
		public static final VariantTemplate PILLAR = create("pillar", BlockModelTemplates.CUBE_COLUMN);
		public static final VariantTemplate TILES = create("tiles");
		public static final VariantTemplate[] ARRAY_OBSIDIAN = new VariantTemplate[] {BLOCKS, CHISELED, CHUNKS, CRATE, CRYSTAL, GREEK, GROWTH, MAP_A, MAP_B, PANEL, PANEL_LIGHT, PANEL_SHINY, PILLAR_QUARTZ, PILLAR, TILES};
	}
	
	public static class Factory {
		public static final VariantTemplate CIRCUIT = create("circuit");
		public static final VariantTemplate COLUMN = create("column", "column_top"); // TODO: Rename texture?
		public static final VariantTemplate DOTS = create("dots");
		public static final VariantTemplate FRAME_BLUE = create("frame_blue");
		public static final VariantTemplate GOLD_PLATE = create("gold_plate");
		public static final VariantTemplate GOLD_PLATING = create("gold_plating");
		public static final VariantTemplate GRINDER = create("grinder");
		public static final VariantTemplate HAZARD = create("hazard");
		public static final VariantTemplate HAZARD_ORANGE = create("hazard_orange");
		public static final VariantTemplate ICE_ICE_ICE = create("ice_ice_ice");
		public static final VariantTemplate METAL_BOX = create("metal_box", BlockModelTemplates.CUBE_COLUMN);
		public static final VariantTemplate PLATE_X = create("plate_x");
		public static final VariantTemplate PLATING = create("plating");
		public static final VariantTemplate RUST = create("rust");
		public static final VariantTemplate RUST_2 = create("rust_2");
		public static final VariantTemplate RUST_PLATES = create("rust_plates");
		public static final VariantTemplate TILE_MOSAIC = create("tile_mosaic");
		public static final VariantTemplate VENT = create("vent", "vent_top"); // TODO: Rename texture?
		public static final VariantTemplate WIREFRAME = create("wireframe");
		public static final VariantTemplate WIREFRAME_BLUE = create("wireframe_blue");
		public static final VariantTemplate WIREFRAME_WHITE = create("wireframe_white");
		public static final VariantTemplate[] ARRAY_FACTORY = new VariantTemplate[] {CIRCUIT, COLUMN, DOTS, FRAME_BLUE, GOLD_PLATE, GOLD_PLATING, GRINDER, HAZARD, HAZARD_ORANGE, ICE_ICE_ICE, METAL_BOX, PLATE_X, PLATING, RUST, RUST_2, RUST_PLATES, TILE_MOSAIC, VENT, WIREFRAME, WIREFRAME_BLUE, WIREFRAME_WHITE};
	}
}
