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
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import team.chisel.chisel.Chisel;
import team.chisel.chisel.api.CarvingGroup;
import team.chisel.chisel.carving.ARRPResourceRegistry;
import team.chisel.chisel.carving.CarvingGroupBuilder;
import team.chisel.chisel.carving.CarvingGroupBuilder.ItemProvider;
import team.chisel.chisel.carving.CarvingGroupBuilder.LootTableProvider;
import team.chisel.chisel.carving.DynamicResourceRegistry;
import team.chisel.chisel.carving.VariantTemplates;

public class CarvingGroups {
	private static final ItemProvider ITEM_PROVIDER = (block, carvingVariant) -> new BlockItem(block, new Item.Settings().group(ItemGroups.CHISEL));
	private static final LootTableProvider LOOT_TABLE_PROVIDER = (id) -> "{\"type\":\"minecraft:block\",\"pools\":[{\"rolls\":1,\"entries\":[{\"type\":\"minecraft:item\",\"name\":\""+id.toString()+"\"}],\"conditions\":[{\"condition\":\"minecraft:survives_explosion\"}]}]}";
	private static final DynamicResourceRegistry DRR = new ARRPResourceRegistry(RuntimeResourcePack.create(Chisel.RESOURCE_NAMESPACE));
	
	private static final CarvingGroupBuilder DEFAULT_BUILDER = new CarvingGroupBuilder()
			.itemProvider(ITEM_PROVIDER)
			.lootTableProvider(LOOT_TABLE_PROVIDER)
			.resourceNamespace(Chisel.RESOURCE_NAMESPACE)
			.drr(DRR);
	
	private static final CarvingGroupBuilder METAL_BUILDER = DEFAULT_BUILDER.copy()
			.addVariantTemplates(VariantTemplates.Metal.ARRAY_METAL)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.of(Material.METAL)
					.sounds(BlockSoundGroups.METAL)
					.strength(5.0F, 6.0F)
					.requiresTool()
					.breakByTool(FabricToolTags.PICKAXES, 1)));
	
	// groups start
	
	// vanilla
	
	public static final CarvingGroup ACACIA_PLANKS = namedDefault("acacia_planks")
			.addBlockItems(new Identifier("acacia_planks"))
			.addVariantTemplates(VariantTemplates.Planks.ARRAY_PLANKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.ACACIA_PLANKS)))
			.build();
	
	public static final CarvingGroup ANDESITE = namedDefault("andesite")
			.addBlockItems(new Identifier("andesite"), new Identifier("polished_andesite"))
			.addVariantTemplates(VariantTemplates.Rock.ARRAY_ROCK)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.ANDESITE)))
			.build();
	
	public static final CarvingGroup BASALT = namedDefault("basalt")
			.addBlockItems(new Identifier("basalt"), new Identifier("polished_basalt"))
			.addVariantTemplates(VariantTemplates.RAW)
			.addVariantTemplates(VariantTemplates.Rock.ARRAY_ROCK)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.BASALT)))
			.build();
	
	public static final CarvingGroup BIRCH_PLANKS = namedDefault("birch_planks")
			.addBlockItems(new Identifier("birch_planks"))
			.addVariantTemplates(VariantTemplates.Planks.ARRAY_PLANKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.BIRCH_PLANKS)))
			.build();
	
	public static final CarvingGroup BRICKS = namedDefault("bricks")
			.addBlockItems(new Identifier("bricks"))
			.addVariantTemplates(VariantTemplates.Rock.ARRAY_ROCK)
			.removeVariantTemplates(VariantTemplates.Rock.SMALL_BRICKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.BRICKS)))
			.build();
	
	public static final CarvingGroup COAL = namedDefault("coal")
			.addBlockItems(new Identifier("coal_block"))
			.addVariantTemplates(VariantTemplates.RAW)
			.addVariantTemplates(VariantTemplates.Rock.ARRAY_ROCK)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.COAL_BLOCK)))
			.build();
	
	public static final CarvingGroup COBBLESTONE = namedDefault("cobblestone")
			.addBlockItems(new Identifier("cobblestone"))
			.addVariantTemplates(VariantTemplates.Rock.ARRAY_ROCK)
			.addVariantTemplates(VariantTemplates.EXTRA_COBBLESTONE.ARRAY_EXTRA_COBBLESTONE)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.COBBLESTONE)))
			.build();
	
	public static final CarvingGroup DARK_OAK_PLANKS = namedDefault("dark_oak_planks")
			.addBlockItems(new Identifier("dark_oak_planks"))
			.addVariantTemplates(VariantTemplates.Planks.ARRAY_PLANKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.DARK_OAK_PLANKS)))
			.build();
	
	public static final CarvingGroup DIAMOND = namedDefault("diamond")
			.addBlockItems(new Identifier("diamond_block"))
			.addVariantTemplates(VariantTemplates.Diamond.ARRAY_DIAMOND)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.DIAMOND_BLOCK)))
			.build();
	
	public static final CarvingGroup DIORITE = namedDefault("diorite")
			.addBlockItems(new Identifier("diorite"), new Identifier("polished_diorite"))
			.addVariantTemplates(VariantTemplates.Rock.ARRAY_ROCK)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.DIORITE)))
			.build();
	
	public static final CarvingGroup DIRT = namedDefault("dirt")
			.addBlockItems(new Identifier("dirt"))
			.addVariantTemplates(VariantTemplates.Dirt.ARRAY_DIRT)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.DIRT)))
			.build();
	
	public static final CarvingGroup EMERALD = namedDefault("emerald")
			.addBlockItems(new Identifier("emerald_block"))
			.addVariantTemplates(VariantTemplates.Emerald.ARRAY_EMERALD)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.EMERALD_BLOCK)))
			.build();
	
	public static final CarvingGroup END_STONE = namedDefault("end_stone")
			.addBlockItems(new Identifier("end_stone"), new Identifier("end_stone_bricks"))
			.addVariantTemplates(VariantTemplates.Rock.ARRAY_ROCK)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.END_STONE)))
			.build();
	
	public static final CarvingGroup GLOWSTONE = namedDefault("glowstone")
			.addBlockItems(new Identifier("glowstone"))
			.addVariantTemplates(VariantTemplates.Rock.ARRAY_ROCK)
			.removeVariantTemplates(VariantTemplates.Rock.CHAOTIC_BRICKS, VariantTemplates.Rock.CIRCULAR_CTM, VariantTemplates.Rock.CUTS, VariantTemplates.Rock.WEAVER, VariantTemplates.Rock.ZAG)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.GLOWSTONE)))
			.build();
	
	public static final CarvingGroup GRANITE = namedDefault("granite")
			.addBlockItems(new Identifier("granite"), new Identifier("polished_granite"))
			.addVariantTemplates(VariantTemplates.Rock.ARRAY_ROCK)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.GRANITE)))
			.build();
	
	public static final CarvingGroup ICE = namedDefault("ice") // TODO: Fix loot table and add pillars
			.addBlockItems(new Identifier("ice"))
			.addVariantTemplates(VariantTemplates.Rock.ARRAY_ROCK)
			.blockProvider((carvingVariant) -> new IceBlock(FabricBlockSettings.copyOf(Blocks.ICE)))
			.build();
	
	public static final CarvingGroup JUNGLE_PLANKS = namedDefault("jungle_planks")
			.addBlockItems(new Identifier("jungle_planks"))
			.addVariantTemplates(VariantTemplates.Planks.ARRAY_PLANKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.JUNGLE_PLANKS)))
			.build();
	
	public static final CarvingGroup LAPIS = namedDefault("lapis")
			.addBlockItems(new Identifier("lapis_block"))
			.addVariantTemplates(VariantTemplates.Lapis.ARRAY_LAPIS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.LAPIS_BLOCK)))
			.build();
	
	public static final CarvingGroup NETHER_BRICKS = namedDefault("nether_bricks")
			.addBlockItems(new Identifier("nether_bricks"), new Identifier("chiseled_nether_bricks"))
			.addVariantTemplates(VariantTemplates.NetherBricks.ARRAY_NETHER_BRICKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS)))
			.build();
	
	public static final CarvingGroup NETHERRACK = namedDefault("netherrack")
			.addBlockItems(new Identifier("netherrack"))
			.addVariantTemplates(VariantTemplates.Netherrack.ARRAY_NETHERRACK)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.NETHERRACK)))
			.build();
	
	public static final CarvingGroup OAK_PLANKS = namedDefault("oak_planks")
			.addBlockItems(new Identifier("oak_planks"))
			.addVariantTemplates(VariantTemplates.Planks.ARRAY_PLANKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)))
			.build();
	
	public static final CarvingGroup OBSIDIAN = namedDefault("obsidian")
			.addBlockItems(new Identifier("obsidian"))
			.addVariantTemplates(VariantTemplates.Obsidian.ARRAY_OBSIDIAN)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.OBSIDIAN)))
			.build();
	
	public static final CarvingGroup PRISMARINE = namedDefault("prismarine")
			.addBlockItems(new Identifier("prismarine"), new Identifier("prismarine_bricks"))
			.addVariantTemplates(VariantTemplates.Rock.ARRAY_ROCK)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.PRISMARINE)))
			.build();
	
	public static final CarvingGroup QUARTZ = namedDefault("quartz")
			.addBlockItems(new Identifier("quartz_block"), new Identifier("quartz_bricks"))
			.addVariantTemplates(VariantTemplates.Rock.ARRAY_ROCK)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK)))
			.build();
	
	public static final CarvingGroup PURPUR = namedDefault("purpur")
			.addBlockItems(new Identifier("purpur_block"), new Identifier("purpur_pillar"))
			.addVariantTemplates(VariantTemplates.Rock.ARRAY_ROCK)
			.removeVariantTemplates(VariantTemplates.Rock.PILLAR, VariantTemplates.Rock.TILES_MEDIUM)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.PURPUR_BLOCK)))
			.build();
	
	public static final CarvingGroup RED_SANDSTONE = namedDefault("red_sandstone") // TODO: Add scribbles
			.addBlockItems(new Identifier("red_sandstone"))
			.addVariantTemplates(VariantTemplates.Rock.ARRAY_ROCK)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.RED_SANDSTONE)))
			.build();
	
	public static final CarvingGroup SANDSTONE = namedDefault("sandstone") // TODO: Add scribbles
			.addBlockItems(new Identifier("sandstone"))
			.addVariantTemplates(VariantTemplates.Rock.ARRAY_ROCK)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.SANDSTONE)))
			.build();
	
	public static final CarvingGroup SPRUCE_PLANKS = namedDefault("spruce_planks")
			.addBlockItems(new Identifier("spruce_planks"))
			.addVariantTemplates(VariantTemplates.Planks.ARRAY_PLANKS)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.SPRUCE_PLANKS)))
			.build();
	
	public static final CarvingGroup STONE = namedDefault("stone")
			.addBlockItems(new Identifier("stone"), new Identifier("stone_bricks"), new Identifier("chiseled_stone_bricks"))
			.addVariantTemplates(VariantTemplates.Rock.ARRAY_ROCK)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.STONE)))
			.build();
	
	public static final CarvingGroup TERRACOTTA = namedDefault("terracotta")
			.addBlockItems(new Identifier("terracotta"))
			.addVariantTemplates(VariantTemplates.Rock.ARRAY_ROCK)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.copyOf(Blocks.TERRACOTTA)))
			.build();
	
	// wool
	// carpet
	// concrete
	// bookshelves
	
	// metals
	// TODO set correct MaterialColor
	
	public static final CarvingGroup ALUMINUM = createMetalGroup("aluminum");
	public static final CarvingGroup BRONZE = createMetalGroup("bronze");
	public static final CarvingGroup COBALT = createMetalGroup("cobalt");
	public static final CarvingGroup COPPER = createMetalGroup("copper");
	public static final CarvingGroup ELECTRUM = createMetalGroup("electrum");
	public static final CarvingGroup GOLD = namedMetal("gold")
			.addVariantTemplates(VariantTemplates.ExtraMetal.ARRAY_EXTRA_METAL) // TODO: Add rest
			.build();
	public static final CarvingGroup INVAR = createMetalGroup("invar");
	public static final CarvingGroup IRON = namedMetal("iron")
			.addVariantTemplates(VariantTemplates.ExtraIron.ARRAY_EXTRA_IRON)
			.build();
	public static final CarvingGroup LEAD = createMetalGroup("lead");
	public static final CarvingGroup NICKEL = createMetalGroup("nickel");
	public static final CarvingGroup PLATINUM = createMetalGroup("platinum");
	public static final CarvingGroup SILVER = createMetalGroup("silver");
	public static final CarvingGroup STEEL = createMetalGroup("steel");
	public static final CarvingGroup TIN = createMetalGroup("tin");
	public static final CarvingGroup URANIUM = createMetalGroup("uranium");
	
	// custom
	
	public static final CarvingGroup BROWNSTONE = namedDefault("brownstone")
			.addVariantTemplates(VariantTemplates.Brownstone.ARRAY_BROWNSTONE)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.of(Material.STONE)
					.sounds(BlockSoundGroup.STONE)
					.requiresTool()))
			.build();
	
	public static final CarvingGroup CERTUS = namedDefault("certus")
			.addVariantTemplates(VariantTemplates.RAW)
			.addVariantTemplates(VariantTemplates.Rock.ARRAY_ROCK)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.of(Material.STONE)
					.sounds(BlockSoundGroup.STONE)
					.requiresTool()))
			.build();
	
	public static final CarvingGroup CHARCOAL = namedDefault("charcoal")
			.addVariantTemplates(VariantTemplates.RAW)
			.addVariantTemplates(VariantTemplates.Rock.ARRAY_ROCK)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.of(Material.STONE)
					.sounds(BlockSoundGroup.STONE)
					.requiresTool()))
			.build();
	
	public static final CarvingGroup CLOUD = namedDefault("cloud")
			.addVariantTemplates(VariantTemplates.Cloud.ARRAY_CLOUD)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.of(Material.WOOL)
					.sounds(BlockSoundGroup.WOOL)
					.nonOpaque()))
			.build();
	
	public static final CarvingGroup COAL_COKE = namedDefault("coal_coke")
			.addVariantTemplates(VariantTemplates.RAW)
			.addVariantTemplates(VariantTemplates.Rock.ARRAY_ROCK)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.of(Material.STONE)
					.sounds(BlockSoundGroup.STONE)
					.requiresTool()))
			.build();
	
	public static final CarvingGroup FACTORY = namedDefault("factory")
			.addVariantTemplates(VariantTemplates.Factory.ARRAY_FACTORY)
			.blockProvider((carvingVariant) -> new Block(FabricBlockSettings.of(Material.METAL)
					.sounds(BlockSoundGroups.METAL)
					.requiresTool()))
			.build();
	
	// groups end
	
	private static CarvingGroupBuilder setNamed(CarvingGroupBuilder builder, String name) {
		return builder
				.identifier(new Identifier(Chisel.MOD_ID, name))
				.texturePath(new Identifier(Chisel.RESOURCE_NAMESPACE, "block/"+name));
	}
	
	private static CarvingGroupBuilder namedDefault(String name) {
		return setNamed(DEFAULT_BUILDER.copy(), name);
	}
	
	private static CarvingGroupBuilder namedMetal(String name) {
		return setNamed(METAL_BUILDER.copy(), name);
	}
	
	private static CarvingGroup createMetalGroup(String name) {
		return namedMetal(name).build();
	}
	
	public static void init() {
		DRR.init();
	}
}
