package team.chisel.chisel.init;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.IceBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import team.chisel.chisel.Chisel;
import team.chisel.chisel.api.CarvingGroup;
import team.chisel.chisel.api.CarvingGroupRegistry;
import team.chisel.chisel.carving.ARRPResourceRegistry;
import team.chisel.chisel.carving.CarvingGroupBuilder;
import team.chisel.chisel.carving.CarvingGroupBuilder.ItemProvider;
import team.chisel.chisel.carving.DynamicResourceRegistry;
import team.chisel.chisel.carving.VariantTemplates;

/*
to create a variant we need:
variant namespace
variant name
block provider
item provider
DynamicResourceRegistry
blockstate json provider
block model json provider
item model json provider
block loot table json provider
*/

public class CarvingGroups {
	public static final ItemProvider ITEM_PROVIDER = (block, carvingVariant) -> new BlockItem(block, new Item.Settings().group(ItemGroups.CHISEL));
	
	public static final DynamicResourceRegistry DRR = new ARRPResourceRegistry(RuntimeResourcePack.create(Chisel.RESOURCE_NAMESPACE));
	
	public static final CarvingGroupBuilder DEFAULT_BUILDER = new CarvingGroupBuilder()
			.resourceNamespace(Chisel.RESOURCE_NAMESPACE)
			.itemProvider(ITEM_PROVIDER)
			.drr(DRR);
	
	public static final CarvingGroupBuilder METAL_BUILDER = DEFAULT_BUILDER.copy()
			.addVariantTemplates(VariantTemplates.ARRAY_METALS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.of(Material.METAL)
					.sounds(BlockSoundGroups.METAL)
					.strength(5.0F, 6.0F)
					.requiresTool()
					.breakByTool(FabricToolTags.PICKAXES, 1)));
	
	// groups start
	
	// vanilla
	
	public static final CarvingGroup ANDESITE = namedDefault("andesite")
			.addBlockItems(new Identifier("andesite"), new Identifier("polished_andesite"))
			.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.ANDESITE)))
			.build();
	
	public static final CarvingGroup BASALT = namedDefault("basalt")
			.addBlockItems(new Identifier("basalt"), new Identifier("polished_basalt"))
			.addVariantTemplates(VariantTemplates.RAW)
			.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.BASALT)))
			.build();
	
	public static final CarvingGroup BRICKS = namedDefault("bricks")
			.addBlockItems(new Identifier("bricks"))
			.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.removeVariantTemplates(VariantTemplates.SMALL_BRICKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.BRICKS)))
			.build();
	
	public static final CarvingGroup COAL = namedDefault("coal")
			.addBlockItems(new Identifier("coal_block"))
			.addVariantTemplates(VariantTemplates.RAW)
			.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.COAL_BLOCK)))
			.build();
	
	public static final CarvingGroup COBBLESTONE = namedDefault("cobblestone")
			.addBlockItems(new Identifier("cobblestone"))
			.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.addVariantTemplates(VariantTemplates.EMBOSS, VariantTemplates.INDENT, VariantTemplates.MARKER)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.COBBLESTONE)))
			.build();
	
	public static final CarvingGroup DIAMOND = namedDefault("diamond")
			.addBlockItems(new Identifier("diamond_block"))
			.addVariantTemplates(VariantTemplates.ARRAY_DIAMOND)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.DIAMOND_BLOCK)))
			.build();
	
	public static final CarvingGroup DIORITE = namedDefault("diorite")
			.addBlockItems(new Identifier("diorite"), new Identifier("polished_diorite"))
			.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.DIORITE)))
			.build();
	
	public static final CarvingGroup DIRT = namedDefault("dirt")
			.addBlockItems(new Identifier("dirt"))
			//.addVariantTemplates(VariantTemplates.ARRAY_DIRT) // TODO
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.DIRT)))
			.build();
	
	public static final CarvingGroup EMERALD = namedDefault("emerald")
			.addBlockItems(new Identifier("emerald_block"))
			.addVariantTemplates(VariantTemplates.ARRAY_EMERALD)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.EMERALD_BLOCK)))
			.build();
	
	public static final CarvingGroup END_STONE = namedDefault("end_stone")
			.addBlockItems(new Identifier("end_stone"), new Identifier("end_stone_bricks"))
			.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.END_STONE)))
			.build();
	
	public static final CarvingGroup GLOWSTONE = namedDefault("glowstone")
			.addBlockItems(new Identifier("glowstone"))
			.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.removeVariantTemplates(VariantTemplates.CHAOTIC_BRICKS, VariantTemplates.CUTS, VariantTemplates.WEAVER, VariantTemplates.ZAG)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.GLOWSTONE)))
			.build();
	
	public static final CarvingGroup GRANITE = namedDefault("granite")
			.addBlockItems(new Identifier("granite"), new Identifier("polished_granite"))
			.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.GRANITE)))
			.build();
	
	public static final CarvingGroup ICE = namedDefault("ice") // translucent renderlayer
			.addBlockItems(new Identifier("ice"))
			.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.blockProvider((carvingVariant) -> new IceBlock(FabricBlockSettings.copyOf(Blocks.ICE)))
			.build();
	
	public static final CarvingGroup LAPIS = namedDefault("lapis")
			.addBlockItems(new Identifier("lapis_block"))
			//.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.LAPIS_BLOCK)))
			.build();
	
	public static final CarvingGroup NETHER_BRICK = namedDefault("nether_brick")
			.addBlockItems(new Identifier("nether_bricks"))
			.addBlockItems(new Identifier("chiseled_nether_bricks"))
			//.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS)))
			.build();
	
	public static final CarvingGroup NETHERRACK = namedDefault("netherrack")
			.addBlockItems(new Identifier("netherrack"))
			//.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.NETHERRACK)))
			.build();
	
	public static final CarvingGroup OBSIDIAN = namedDefault("obsidian")
			.addBlockItems(new Identifier("obsidian"))
			//.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.OBSIDIAN)))
			.build();
	
	public static final CarvingGroup PRISMARINE = namedDefault("prismarine")
			.addBlockItems(new Identifier("prismarine"), new Identifier("prismarine_bricks"))
			.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.PRISMARINE)))
			.build();
	
	public static final CarvingGroup QUARTZ = namedDefault("quartz")
			.addBlockItems(new Identifier("quartz_block"), new Identifier("quartz_bricks"))
			.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK)))
			.build();
	
	public static final CarvingGroup PURPUR = namedDefault("purpur")
			.addBlockItems(new Identifier("purpur_block"), new Identifier("purpur_pillar"))
			.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.removeVariantTemplates(VariantTemplates.PILLAR, VariantTemplates.TILES_MEDIUM)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.PURPUR_BLOCK)))
			.build();
	
	public static final CarvingGroup RED_SANDSTONE = namedDefault("red_sandstone")
			.addBlockItems(new Identifier("red_sandstone"))
			.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.RED_SANDSTONE)))
			.build();
	
	public static final CarvingGroup SANDSTONE = namedDefault("sandstone")
			.addBlockItems(new Identifier("sandstone"))
			.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.SANDSTONE)))
			.build();
	
	public static final CarvingGroup STONE = namedDefault("stone")
			.addBlockItems(new Identifier("stone"), new Identifier("stone_bricks"), new Identifier("chiseled_stone_bricks"))
			.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.STONE)))
			.build();
	
	public static final CarvingGroup TERRACOTTA = namedDefault("terracotta")
			.addBlockItems(new Identifier("terracotta"))
			.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.TERRACOTTA)))
			.build();
	
	// wool
	// carpet
	// concrete
	// planks
	// bookshelves
	
	// metals
	// TODO set correct MaterialColor
	
	public static final CarvingGroup ALUMINUM = createMetalGroup("aluminum");
	public static final CarvingGroup BRONZE = createMetalGroup("bronze");
	public static final CarvingGroup COBALT = createMetalGroup("cobalt");
	public static final CarvingGroup COPPER = createMetalGroup("copper");
	public static final CarvingGroup ELECTRUM = createMetalGroup("electrum");
	public static final CarvingGroup GOLD = namedMetal("gold")
			// TODO more textures
			.build();
	public static final CarvingGroup INVAR = createMetalGroup("invar");
	public static final CarvingGroup IRON = namedMetal("iron")
			// TODO more textures
			.build();
	public static final CarvingGroup LEAD = createMetalGroup("lead");
	public static final CarvingGroup NICKEL = createMetalGroup("nickel");
	public static final CarvingGroup PLATINUM = createMetalGroup("platinum");
	public static final CarvingGroup SILVER = createMetalGroup("silver");
	public static final CarvingGroup STEEL = createMetalGroup("steel");
	public static final CarvingGroup TIN = createMetalGroup("tin");
	public static final CarvingGroup URANIUM = createMetalGroup("uranium");
	
	// custom
	
	/*
	public static final CarvingGroup BROWNSTONE = namedDefault("brownstone")
			.addVariantTemplates(VariantTemplates.ARRAY_BROWNSTONE)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.of(Material.STONE)
					.sounds(BlockSoundGroup.STONE)
					.requiresTool()))
			.build();
	
	public static final CarvingGroup CERTUS = namedDefault("certus")
			.addVariantTemplates(VariantTemplates.RAW)
			.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.of(Material.STONE)
					.sounds(BlockSoundGroup.STONE)
					.requiresTool()))
			.build();
	
	public static final CarvingGroup CHARCOAL = namedDefault("charcoal")
			.addVariantTemplates(VariantTemplates.RAW)
			.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.of(Material.STONE)
					.sounds(BlockSoundGroup.STONE)
					.requiresTool()))
			.build();
	
	public static final CarvingGroup CLOUD = namedDefault("cloud")
			.addVariantTemplates(VariantTemplates.ARRAY_CLOUD)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.of(Material.STONE)
					.sounds(BlockSoundGroup.STONE)
					.requiresTool()))
			.build();
	
	public static final CarvingGroup COAL_COKE = namedDefault("coal_coke")
			.addVariantTemplates(VariantTemplates.RAW)
			.addVariantTemplates(VariantTemplates.ARRAY_ROCKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.of(Material.STONE)
					.sounds(BlockSoundGroup.STONE)
					.requiresTool()))
			.build();
	
	public static final CarvingGroup FACTORY = namedDefault("factory")
			.addVariantTemplates(VariantTemplates.ARRAY_FACTORY)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.of(Material.METAL)
					.sounds(BlockSoundGroups.METAL)
					.requiresTool()))
			.build();
	*/

	
	private static CarvingGroupBuilder namedDefault(String name) {
		return DEFAULT_BUILDER.copy()
				.identifier(new Identifier(Chisel.MOD_ID, name))
				.texturePath(new Identifier(Chisel.RESOURCE_NAMESPACE, "block/"+name));
	}
	
	private static CarvingGroup createMetalGroup(String name) {
		return createNamedGroup(METAL_BUILDER.copy(), name);
	}
	
	private static CarvingGroup createNamedGroup(CarvingGroupBuilder base, String name) {
		return base
				.identifier(new Identifier(Chisel.MOD_ID, name))
				.texturePath(new Identifier(Chisel.RESOURCE_NAMESPACE, "block/"+name))
				.build();
	}
	
	private static CarvingGroupBuilder namedMetal(String name) {
		return METAL_BUILDER.copy()
				.identifier(new Identifier(Chisel.MOD_ID, name))
				.texturePath(new Identifier(Chisel.RESOURCE_NAMESPACE, "block/"+name));
	}
	
	private static void register(Identifier identifier, CarvingGroup group) {
		CarvingGroupRegistry.INSTANCE.register(identifier, group);
	}
	
	private static void register(String name, CarvingGroup group) {
		register(new Identifier(Chisel.MOD_ID, name), group);
	}
	
	public static void init() {
		DRR.init();

		register("andesite", ANDESITE);
		register("basalt", BASALT);
		register("bricks", BRICKS);
		register("coal", COAL);
		register("cobblestone", COBBLESTONE);
		register("diamond", DIAMOND);
		register("diorite", DIORITE);
		register("dirt", DIRT);
		register("emerald", EMERALD);
		register("end_stone", END_STONE);
		register("glowstone", GLOWSTONE);
		register("granite", GRANITE);
		register("ice", ICE);
		register("lapis", LAPIS);
		register("nether_brick", NETHER_BRICK);
		register("netherrack", NETHERRACK);
		register("obsidian", OBSIDIAN);
		register("prismarine", PRISMARINE);
		register("quartz", QUARTZ);
		register("purpur", PURPUR);
		register("red_sandstone", RED_SANDSTONE);
		register("sandstone", SANDSTONE);
		register("stone", STONE);
		register("terracotta", TERRACOTTA);
		
		register("aluminum", ALUMINUM);
		register("bronze", BRONZE);
		register("cobalt", COBALT);
		register("copper", COPPER);
		register("electrum", ELECTRUM);
		register("gold", GOLD);
		register("invar", INVAR);
		register("iron", IRON);
		register("lead", LEAD);
		register("nickel", NICKEL);
		register("platinum", PLATINUM);
		register("silver", SILVER);
		register("steel", STEEL);
		register("tin", TIN);
		register("uranium", URANIUM);
		
//		register("brownstone", BROWNSTONE);
//		register("certus", CERTUS);
//		register("charcoal", CHARCOAL);
//		register("cloud", CLOUD);
//		register("coal_coke", COAL_COKE);
//		register("factory", FACTORY);
	}
}
