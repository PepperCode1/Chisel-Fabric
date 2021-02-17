package team.chisel.chisel.item;

import static team.chisel.chisel.client.util.ChiselLangKeys.TOOLTIP_CHISEL_IN_INVENTORY;
import static team.chisel.chisel.client.util.ChiselLangKeys.TOOLTIP_CHISEL_IN_INVENTORY_TARGET_BLOCK;
import static team.chisel.chisel.client.util.ChiselLangKeys.TOOLTIP_CHISEL_IN_WORLD;
import static team.chisel.chisel.client.util.ChiselLangKeys.TOOLTIP_CHISEL_IN_WORLD_LEFT_CLICK;
import static team.chisel.chisel.client.util.ChiselLangKeys.TOOLTIP_CHISEL_MODES;
import static team.chisel.chisel.client.util.ChiselLangKeys.TOOLTIP_CHISEL_OPEN_GUI;
import static team.chisel.chisel.client.util.ChiselLangKeys.TOOLTIP_CHISEL_OPEN_GUI_RIGHT_CLICK;
import static team.chisel.chisel.client.util.ChiselLangKeys.TOOLTIP_CHISEL_SELECTED_MODE;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import team.chisel.chisel.api.CarvingVariant;
import team.chisel.chisel.api.ChiselItem;
import team.chisel.chisel.api.ChiselMode;
import team.chisel.chisel.config.Configurations;
import team.chisel.chisel.impl.ChiselModeImpl;
import team.chisel.chisel.init.ScreenHandlers;
import team.chisel.chisel.util.ChiselNBT;

public class ChiselItemImpl extends Item implements ChiselItem {
	private final ChiselType type;
	
	public ChiselItemImpl(ChiselType type, Settings settings) {
		super(applyMaxDamage(type, settings));
		this.type = type;
	}
	
	private static Settings applyMaxDamage(ChiselType type, Settings settings) {
		if (Configurations.allowChiselDamage) {
			return settings.maxDamage(type.maxDamage);
		}
		return settings;
	}
	
	public ChiselType getType() {
		return type;
	}

	@Override
	public boolean isDamageable() {
		return Configurations.allowChiselDamage;
	}

	@Override
	public boolean canRepair(ItemStack damagedItem, ItemStack repairMaterial) {
		switch (type) {
		case HITECH:
		case DIAMOND:
			return repairMaterial.getItem().equals(Items.DIAMOND);
		case IRON:
			return repairMaterial.getItem().equals(Items.IRON_INGOT);
		}
		
		return false;
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(TOOLTIP_CHISEL_OPEN_GUI.format(TOOLTIP_CHISEL_OPEN_GUI_RIGHT_CLICK.getText().formatted(Formatting.AQUA)).formatted(Formatting.GRAY));
		if (type != ChiselType.IRON || Configurations.ironChiselCanLeftClick) {
			tooltip.add(TOOLTIP_CHISEL_IN_WORLD.format(TOOLTIP_CHISEL_IN_WORLD_LEFT_CLICK.getText().formatted(Formatting.AQUA)).formatted(Formatting.GRAY));
			tooltip.add(TOOLTIP_CHISEL_IN_INVENTORY.format(TOOLTIP_CHISEL_IN_INVENTORY_TARGET_BLOCK.getText().formatted(Formatting.AQUA)).formatted(Formatting.GRAY));
		}
		if (type != ChiselType.IRON || Configurations.ironChiselHasModes) {
			tooltip.add(LiteralText.EMPTY);
			tooltip.add(TOOLTIP_CHISEL_MODES.getText().formatted(Formatting.GRAY));
			tooltip.add(TOOLTIP_CHISEL_SELECTED_MODE.format(new TranslatableText(ChiselNBT.getChiselMode(stack).getNameKey()).formatted(Formatting.GREEN)).formatted(Formatting.GRAY));
		}
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		Multimap<EntityAttribute, EntityAttributeModifier> multimap = HashMultimap.create();
		if (slot == EquipmentSlot.MAINHAND) {
			multimap.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Chisel damage", type.attackDamage, Operation.ADDITION));
		}
		return multimap;
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damage(1, attacker, (p) -> {
			p.sendToolBreakStatus(p.getActiveHand());
		});
		return true;
	}
	
	@Override
	public boolean canOpenGui(PlayerEntity player, World world, Hand hand) {
		return true;
	}
	
	@Override
	public NamedScreenHandlerFactory getHandlerFactory(PlayerEntity player, World world, Hand hand) {
		return new ExtendedScreenHandlerFactory() {
			@Override
			public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
				if (type == ChiselType.HITECH) {
					return ScreenHandlers.createHitechChisel(syncId, inv, hand);
				} else {
					return ScreenHandlers.createNormalChisel(syncId, inv, hand);
				}
			}

			@Override
			public Text getDisplayName() {
				return player.getStackInHand(hand).getName();
			}

			@Override
			public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
				buf.writeInt(hand.ordinal());
			}
		};
	}

	@Override
	public boolean canChisel(PlayerEntity player, World world, ItemStack chisel, CarvingVariant target) {
		return !chisel.isEmpty();
	}

	@Override
	public boolean onChisel(PlayerEntity player, World world, ItemStack chisel, CarvingVariant target) {
		return isDamageable();
	}

	@Override
	public boolean canChiselBlock(PlayerEntity player, World world, Hand hand, BlockPos pos, BlockState state) {
		return type == ChiselType.HITECH || type == ChiselType.DIAMOND || Configurations.ironChiselCanLeftClick;
	}

	@Override
	public boolean supportsMode(PlayerEntity player, ItemStack chisel, ChiselMode mode) {
		return type == ChiselType.HITECH || ((type == ChiselType.DIAMOND || Configurations.ironChiselHasModes) && mode != ChiselModeImpl.CONTIGUOUS && mode != ChiselModeImpl.CONTIGUOUS_2D);
	}
	
	public enum ChiselType {
		IRON(Configurations.ironChiselMaxDamage, Configurations.ironChiselAttackDamage),
		DIAMOND(Configurations.diamondChiselMaxDamage, Configurations.diamondChiselAttackDamage),
		HITECH(Configurations.hitechChiselMaxDamage, Configurations.hitechChiselAttackDamage)
		;

		final int maxDamage;
		final int attackDamage;

		private ChiselType(int maxDamage, int attackDamage) {
			this.maxDamage = maxDamage;
			this.attackDamage = attackDamage;
		}
	}
}
