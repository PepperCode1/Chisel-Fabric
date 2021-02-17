package team.chisel.chisel.carving;

public class VariantTemplates {
	private static class SimpleVariantTemplate implements VariantTemplate {
		private String name;
		private BlockModelTemplate model;
		
		public SimpleVariantTemplate(String name, BlockModelTemplate model) {
			this.name = name;
			this.model = model;
		}
		
		public String getName() {
			return name;
		}
		
		public BlockModelTemplate getModel() {
			return model;
		}
	}
	
	private static VariantTemplate simple(String name) {
		return new SimpleVariantTemplate(name, BlockModelTemplates.CUBE_ALL);
	}
	
	private static VariantTemplate withModel(String name, BlockModelTemplate model) {
		return new SimpleVariantTemplate(name, model);
	}
	
	public static final VariantTemplate ARRAY = simple("array");
	public static final VariantTemplate BRAID = simple("braid");
	public static final VariantTemplate CHAOTIC_BRICKS = simple("bricks_chaotic");
	public static final VariantTemplate CRACKED_BRICKS = simple("bricks_cracked");
	public static final VariantTemplate ENCASED_BRICKS = simple("bricks_encased");
	public static final VariantTemplate SMALL_BRICKS = simple("bricks_small");
	public static final VariantTemplate SOFT_BRICKS = simple("bricks_soft");
	public static final VariantTemplate SOLID_BRICKS = simple("bricks_solid");
	public static final VariantTemplate TRIPLE_BRICKS = simple("bricks_triple");
	public static final VariantTemplate CHAOTIC_MEDIUM = simple("chaotic_medium");
	public static final VariantTemplate CHAOTIC_SMALL = simple("chaotic_small");
	public static final VariantTemplate CIRCULAR = simple("circular");
	//public static final VariantTemplate CIRCULAR_CTM = withModel("circular_ctm", ModelTemplates.ctm("circular"));
	public static final VariantTemplate CRACKED = simple("cracked");
	public static final VariantTemplate CUTS = simple("cuts");
	public static final VariantTemplate DENT = simple("dent");
	public static final VariantTemplate FRENCH_1 = simple("french_1");
	public static final VariantTemplate FRENCH_2 = simple("french_2");
	public static final VariantTemplate JELLYBEAN = simple("jellybean");
	public static final VariantTemplate LAYERS = simple("layers");
	public static final VariantTemplate MOSAIC = simple("mosaic");
	public static final VariantTemplate ORNATE = simple("ornate");
	public static final VariantTemplate PANEL = simple("panel");
	public static final VariantTemplate PILLAR = withModel("pillar", BlockModelTemplates.CUBE_COLUMN);
	public static final VariantTemplate PRISM = simple("prism");
	public static final VariantTemplate ROAD = simple("road");
	public static final VariantTemplate SLANTED = simple("slanted");
	public static final VariantTemplate TILES_LARGE = simple("tiles_large");
	public static final VariantTemplate TILES_MEDIUM = simple("tiles_medium");
	public static final VariantTemplate TILES_SMALL = simple("tiles_small");
	public static final VariantTemplate TWISTED = withModel("twisted", BlockModelTemplates.CUBE_COLUMN);
	public static final VariantTemplate WEAVER = simple("weaver");
	public static final VariantTemplate ZAG = simple("zag");
	public static final VariantTemplate[] ARRAY_ROCKS = new VariantTemplate[] {ARRAY, BRAID, CHAOTIC_BRICKS, CRACKED_BRICKS, ENCASED_BRICKS, SMALL_BRICKS, SOFT_BRICKS, SOLID_BRICKS, TRIPLE_BRICKS, CHAOTIC_MEDIUM, CHAOTIC_SMALL, CIRCULAR, CRACKED, CUTS, DENT, FRENCH_1, FRENCH_2, JELLYBEAN, LAYERS, MOSAIC, ORNATE, PANEL, PILLAR, PRISM, ROAD, SLANTED, TILES_LARGE, TILES_MEDIUM, TILES_SMALL, TWISTED, WEAVER, ZAG};
	
	public static final VariantTemplate BAD_GREGGY = simple("bad_greggy");
	public static final VariantTemplate BOLTED = simple("bolted");
	public static final VariantTemplate CAUTION = simple("caution");
	public static final VariantTemplate CRATE = simple("crate");
	public static final VariantTemplate MACHINE = simple("machine");
	public static final VariantTemplate SCAFFOLD = simple("scaffold");
	public static final VariantTemplate THERMAL = withModel("thermal", BlockModelTemplates.CUBE_BOTTOM_TOP);
	public static final VariantTemplate[] ARRAY_METALS = new VariantTemplate[] {CAUTION, CRATE, THERMAL, MACHINE, BAD_GREGGY, BOLTED, SCAFFOLD};
	
	public static final VariantTemplate RAW = simple("raw");

	public static final VariantTemplate BLOCK = simple("block");
	public static final VariantTemplate BLOCKS = simple("blocks");
	public static final VariantTemplate DEFAULT = simple("default");
	public static final VariantTemplate DOUBLE_SLAB = withModel("double_slab", BlockModelTemplates.BROWNSTONE_DOUBLE_SLAB);
	public static final VariantTemplate WEATHERED = simple("weathered");
	public static final VariantTemplate WEATHERED_BLOCK = simple("weathered_block");
	public static final VariantTemplate WEATHERED_BLOCK_HALF = withModel("weathered_block_half", BlockModelTemplates.BROWNSTONE_WEATHERED_BLOCK_HALF);
	public static final VariantTemplate WEATHERED_BLOCKS = simple("weathered_blocks");
	public static final VariantTemplate WEATHERED_DOUBLE_SLAB = withModel("weathered_double_slab", BlockModelTemplates.BROWNSTONE_WEATHERED_DOUBLE_SLAB);
	public static final VariantTemplate WEATHERED_HALF = withModel("weathered_half", BlockModelTemplates.BROWNSTONE_WEATHERED_HALF);
	public static final VariantTemplate[] ARRAY_BROWNSTONE = new VariantTemplate[] {BLOCK, BLOCKS, DEFAULT, DOUBLE_SLAB, WEATHERED, WEATHERED_BLOCK, WEATHERED_BLOCK_HALF, WEATHERED_BLOCKS, WEATHERED_DOUBLE_SLAB, WEATHERED_HALF};
	
	public static final VariantTemplate LEGACY = withModel("legacy", BlockModelTemplates.LEGACY_CARPET);
	public static final VariantTemplate LLAMA = withModel("llama", BlockModelTemplates.LLAMA_CARPET);
	public static final VariantTemplate[] ARRAY_CARPET = new VariantTemplate[] {LEGACY, LLAMA};
	
	public static final VariantTemplate CLOUD = simple("cloud");
	public static final VariantTemplate LARGE = simple("large");
	public static final VariantTemplate SMALL = simple("small");
	public static final VariantTemplate VERTICAL = simple("vertical");
	public static final VariantTemplate GRID = simple("grid");
	public static final VariantTemplate[] ARRAY_CLOUD = new VariantTemplate[] {CLOUD, LARGE, SMALL, VERTICAL, GRID};
	
	public static final VariantTemplate EMBOSS = simple("emboss");
	public static final VariantTemplate INDENT = simple("indent");
	public static final VariantTemplate MARKER = simple("marker");

	public static final VariantTemplate BISMUTH = simple("bismuth");
	public static final VariantTemplate CELLS = simple("cells");
	public static final VariantTemplate CRUSHED = simple("crushed");
	public static final VariantTemplate EMBOSSED = withModel("embossed", BlockModelTemplates.CUBE_BOTTOM_TOP);
	public static final VariantTemplate FOUR = simple("four");
	public static final VariantTemplate FOUR_ORNATE = simple("four_ornate");
	public static final VariantTemplate GEM = withModel("gem", BlockModelTemplates.CUBE_BOTTOM_TOP);
	public static final VariantTemplate ORNATE_LAYER = simple("ornate_layer");
	public static final VariantTemplate SIMPLE = withModel("simple", BlockModelTemplates.CUBE_BOTTOM_TOP);
	public static final VariantTemplate SPACE = simple("space");
	public static final VariantTemplate SPACE_BLACK = simple("space_black");
	public static final VariantTemplate ZELDA = simple("zelda");
	public static final VariantTemplate[] ARRAY_DIAMOND = new VariantTemplate[] {BISMUTH, CELLS, CRUSHED, EMBOSSED, FOUR, FOUR_ORNATE, GEM, ORNATE_LAYER, SIMPLE, SPACE, SPACE_BLACK, ZELDA};
	
	public static final VariantTemplate CELL = simple("cell");
	public static final VariantTemplate CELL_BISMUTH = simple("cell_bismuth");
	public static final VariantTemplate CHUNK = simple("chunk");
	public static final VariantTemplate CIRCLE = simple("circle");
	public static final VariantTemplate GOLD_BORDER = simple("gold_border");
	public static final VariantTemplate MASONRY = simple("masonry");
	public static final VariantTemplate PANEL_CLASSIC = simple("panel_classic");
	public static final VariantTemplate PRISMATIC = simple("prismatic");
	public static final VariantTemplate SMOOTH = simple("smooth");
	public static final VariantTemplate[] ARRAY_EMERALD = new VariantTemplate[] {CELL, CELL_BISMUTH, CHUNK, CIRCLE, FOUR, FOUR_ORNATE, GOLD_BORDER, MASONRY, ORNATE, PANEL, PANEL_CLASSIC, PRISMATIC, SMOOTH, ZELDA};

	public static final VariantTemplate CIRCUIT = simple("circuit");
	public static final VariantTemplate COLUMN = simple("column"); // TODO
	public static final VariantTemplate DOTS = simple("dots");
	public static final VariantTemplate FRAME_BLUE = simple("frame_blue");
	public static final VariantTemplate GOLD_PLATE = simple("gold_plate");
	public static final VariantTemplate GOLD_PLATING = simple("gold_plating");
	public static final VariantTemplate GRINDER = simple("grinder");
	public static final VariantTemplate HAZARD = simple("hazard");
	public static final VariantTemplate HAZARD_ORANGE = simple("hazard_orange");
	public static final VariantTemplate ICE_ICE_ICE = simple("ice_ice_ice");
	public static final VariantTemplate METAL_BOX = withModel("metal_box", BlockModelTemplates.CUBE_COLUMN);
	public static final VariantTemplate PLATE_X = simple("plate_x");
	public static final VariantTemplate PLATING = simple("plating");
	public static final VariantTemplate RUST = simple("rust");
	public static final VariantTemplate RUST_2 = simple("rust_2");
	public static final VariantTemplate RUST_PLATES = simple("rust_plates");
	public static final VariantTemplate TILE_MOSAIC = simple("tile_mosaic");
	public static final VariantTemplate VENT = simple("vent"); // TODO
	public static final VariantTemplate WIREFRAME = simple("wireframe");
	public static final VariantTemplate WIREFRAME_BLUE = simple("wireframe_blue");
	public static final VariantTemplate WIREFRAME_WHITE = simple("wireframe_white");
	public static final VariantTemplate[] ARRAY_FACTORY = new VariantTemplate[] {CIRCUIT, COLUMN, DOTS, FRAME_BLUE, GOLD_PLATE, GOLD_PLATING, GRINDER, HAZARD, HAZARD_ORANGE, ICE_ICE_ICE, METAL_BOX, PLATE_X, PLATING, RUST, RUST_2, RUST_PLATES, TILE_MOSAIC, VENT, WIREFRAME, WIREFRAME_BLUE, WIREFRAME_WHITE};
}
