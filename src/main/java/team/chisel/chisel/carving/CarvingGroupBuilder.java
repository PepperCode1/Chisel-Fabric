package team.chisel.chisel.carving;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourceType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import team.chisel.chisel.api.CarvingGroup;
import team.chisel.chisel.api.CarvingVariant;

//FabricModelPredicateProviderRegistry
public class CarvingGroupBuilder {
	private Identifier identifier;
	private String resourceNamespace;
	private List<Identifier> blockItems = new ArrayList<>();
	private List<VariantTemplate> variantTemplates = new ArrayList<>();
	private Identifier textureLocation;
	private SoundEvent soundEvent;
	private BlockProvider blockProvider;
	private ItemProvider itemProvider;
	private DynamicResourceRegistry drr;
	
	public CarvingGroupBuilder addBlockItems(Collection<Identifier> blockItems) {
		this.blockItems.addAll(blockItems);
		return this;
	}
	
	public CarvingGroupBuilder addBlockItems(Identifier... blockItems) {
		for (Identifier blockItem : blockItems) {
			this.blockItems.add(blockItem);
		}
		return this;
	}
	
	public CarvingGroupBuilder removeBlockItems(Identifier... blockItems) {
		for (Identifier blockItem : blockItems) {
			this.blockItems.remove(blockItem);
		}
		return this;
	}
	
	public CarvingGroupBuilder addVariantTemplates(Collection<VariantTemplate> templates) {
		variantTemplates.addAll(templates);
		return this;
	}
	
	public CarvingGroupBuilder addVariantTemplates(VariantTemplate... templates) {
		for (VariantTemplate template : templates) {
			variantTemplates.add(template);
		}
		return this;
	}
	
	public CarvingGroupBuilder removeVariantTemplates(VariantTemplate... templates) {
		for (VariantTemplate template : templates) {
			variantTemplates.remove(template);
		}
		return this;
	}
	
	public CarvingGroupBuilder identifier(Identifier identifier) {
		this.identifier = identifier;
		return this;
	}
	
	public CarvingGroupBuilder texturePath(Identifier texturePath) {
		this.textureLocation = texturePath;
		return this;
	}
	
	public CarvingGroupBuilder soundEvent(SoundEvent soundEvent) {
		this.soundEvent = soundEvent;
		return this;
	}
	
	public CarvingGroupBuilder blockProvider(BlockProvider blockProvider) {
		this.blockProvider = blockProvider;
		return this;
	}
	
	public CarvingGroupBuilder itemProvider(ItemProvider itemProvider) {
		this.itemProvider = itemProvider;
		return this;
	}
	
	public CarvingGroupBuilder resourceNamespace(String resourceNamespace) {
		this.resourceNamespace = resourceNamespace;
		return this;
	}
	
	public CarvingGroupBuilder drr(DynamicResourceRegistry drr) {
		this.drr = drr;
		return this;
	}
	
	public CarvingGroup build() {
		String groupNamespace = identifier.getNamespace();
		String groupPath = identifier.getPath();
		
		CarvingGroupImpl carvingGroup = new CarvingGroupImpl(soundEvent);
		List<CarvingVariant> carvingVariants = new ArrayList<>();
		carvingGroup.carvingVariants = carvingVariants;
		
		int i = 0;
		for (Identifier blockItem : blockItems) {
			String variantName = "minecraft_"+String.valueOf(i++);
			
			CarvingVariantImpl carvingVariant = new CarvingVariantImpl(carvingGroup, variantName);
			
			if (Registry.BLOCK.containsId(blockItem)) {
				carvingVariant.blockState = Registry.BLOCK.get(blockItem).getDefaultState();
			}
			if (Registry.ITEM.containsId(blockItem)) {
				carvingVariant.item = Registry.ITEM.get(blockItem);
			}
			
			carvingVariants.add(carvingVariant);
		}
		
		for (VariantTemplate template : variantTemplates) {
			String variantName = template.getName();
			
			CarvingVariantImpl carvingVariant = new CarvingVariantImpl(carvingGroup, variantName);
			
			Block block = blockProvider.createBlock(carvingVariant);
			Item item = itemProvider.createItem(block, carvingVariant);
			
			carvingVariant.blockState = block.getDefaultState();
			carvingVariant.item = item;
			
			String variantPath = groupPath+"_"+variantName;
			Identifier id = new Identifier(groupNamespace, variantPath);
			
			Registry.register(Registry.BLOCK, id, block);
			Registry.register(Registry.ITEM, id, item);
			
			drr.register(ResourceType.CLIENT_RESOURCES, new Identifier(resourceNamespace, "blockstates/"+variantPath+".json"), "{\"variants\":{\"\":{\"model\":\""+resourceNamespace+":block/"+variantPath+"\"}}}");
			drr.register(ResourceType.CLIENT_RESOURCES, new Identifier(resourceNamespace, "models/block/"+variantPath+".json"), template.getModel().getJson(textureLocation, groupPath, variantName));
			drr.register(ResourceType.CLIENT_RESOURCES, new Identifier(resourceNamespace, "models/item/"+variantPath+".json"), "{\"parent\":\""+resourceNamespace+":block/"+variantPath+"\"}");
			drr.register(ResourceType.SERVER_DATA, new Identifier(resourceNamespace, "loot_tables/blocks/"+variantPath+".json"), "{\"type\":\"minecraft:block\",\"pools\":[{\"rolls\":1,\"entries\":[{\"type\":\"minecraft:item\",\"name\":\""+id.toString()+"\"}],\"conditions\":[{\"condition\":\"minecraft:survives_explosion\"}]}]}");
			
			carvingVariants.add(carvingVariant);
		}
		
		return carvingGroup;
	}
	
	public CarvingGroupBuilder copy() {
		CarvingGroupBuilder builder = new CarvingGroupBuilder();
		builder.blockItems.addAll(blockItems);
		builder.variantTemplates.addAll(variantTemplates);
		builder.identifier = identifier;
		builder.textureLocation = textureLocation;
		builder.soundEvent = soundEvent;
		builder.blockProvider = blockProvider;
		builder.itemProvider = itemProvider;
		builder.resourceNamespace = resourceNamespace;
		builder.drr = drr;
		return builder;
	}
	
	private static class CarvingGroupImpl implements CarvingGroup {
		private List<CarvingVariant> carvingVariants;
		private SoundEvent soundEvent;
		
		public CarvingGroupImpl(SoundEvent soundEvent) {
			this.soundEvent = soundEvent;
		}

		@Override
		public List<CarvingVariant> getCarvingVariants() {
			return List.copyOf(carvingVariants);
		}
		
		@Override
		public SoundEvent getSoundEvent() {
			return soundEvent;
		}
	}
	
	private static class CarvingVariantImpl implements CarvingVariant {
		private CarvingGroup carvingGroup;
		private String name;
		private BlockState blockState;
		private Item item;
		
		public CarvingVariantImpl(CarvingGroup carvingGroup, String name) {
			this.carvingGroup = carvingGroup;
			this.name = name;
		}
		
		@Override
		public CarvingGroup getCarvingGroup() {
			return carvingGroup;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public BlockState getBlockState() {
			return blockState;
		}

		@Override
		public Item getItem() {
			return item;
		}
	}
	
	public interface BlockProvider {
		Block createBlock(CarvingVariant carvingVariant);
	}
	
	public interface ItemProvider {
		Item createItem(Block block, CarvingVariant carvingVariant);
	}
}
