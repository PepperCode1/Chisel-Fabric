package team.chisel.chisel.util;

import java.util.Optional;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import team.chisel.chisel.api.ChiselMode;
import team.chisel.chisel.api.ChiselModeRegistry;
import team.chisel.chisel.client.util.PreviewType;
import team.chisel.chisel.impl.ChiselModeImpl;

public class ChiselNBT {
	public static final String KEY_TAG = "chisel_data";
	public static final String KEY_TARGET = "target";
	public static final String KEY_MODE = "mode";
	public static final String KEY_PREVIEW_TYPE = "preview_type";
	public static final String KEY_SELECTION_SLOT = "selection_slot";
	public static final String KEY_TARGET_SLOT = "target_slot";
	public static final String KEY_ROTATE = "rotate";

	public static CompoundTag getChiselTag(ItemStack stack) {
		return stack.getOrCreateSubTag(KEY_TAG);
	}
	
	public static void setChiselTag(ItemStack stack, CompoundTag tag) {
		stack.putSubTag(KEY_TAG, tag);
	}

	public static ItemStack getChiselTarget(ItemStack stack) {
		return ItemStack.fromTag(getChiselTag(stack).getCompound(KEY_TARGET));
	}

	public static void setChiselTarget(ItemStack chisel, ItemStack target) {
		getChiselTag(chisel).put(KEY_TARGET, target.toTag(new CompoundTag()));
	}
	
	public static ChiselMode getChiselMode(ItemStack chisel) {
		String mode = getChiselTag(chisel).getString(KEY_MODE);
		return Optional.ofNullable(ChiselModeRegistry.INSTANCE.getModeByName(mode))
				.orElse(ChiselModeImpl.SINGLE);
	}

	public static void setChiselMode(ItemStack chisel, ChiselMode mode) {
		getChiselTag(chisel).putString(KEY_MODE, mode.name());
	}
	
	public static PreviewType getHitechType(ItemStack stack) {
		return PreviewType.values()[getChiselTag(stack).getInt(KEY_PREVIEW_TYPE)];
	}
	
	public static void setHitechType(ItemStack stack, PreviewType type) {
		getChiselTag(stack).putInt(KEY_PREVIEW_TYPE, type.ordinal());
	}

	public static void setHitechType(ItemStack stack, int typeOrdinal) {
		getChiselTag(stack).putInt(KEY_PREVIEW_TYPE, typeOrdinal);
	}
	
	public static int getHitechSelection(ItemStack stack) {
		CompoundTag tag = getChiselTag(stack);
		return tag.contains(KEY_SELECTION_SLOT) ? tag.getInt(KEY_SELECTION_SLOT) : -1;
	}

	public static void setHitechSelection(ItemStack chisel, int slot) {
		getChiselTag(chisel).putInt(KEY_SELECTION_SLOT, slot);
	}
	
	public static int getHitechTarget(ItemStack stack) {
		CompoundTag tag = getChiselTag(stack);
		return tag.contains(KEY_TARGET_SLOT) ? tag.getInt(KEY_TARGET_SLOT) : -1;
	}

	public static void setHitechTarget(ItemStack chisel, int slot) {
		getChiselTag(chisel).putInt(KEY_TARGET_SLOT, slot);
	}
	
	public static boolean getHitechRotate(ItemStack stack) {
		return getChiselTag(stack).getBoolean(KEY_ROTATE);
	}

	public static void setHitechRotate(ItemStack chisel, boolean rotate) {
		getChiselTag(chisel).putBoolean(KEY_ROTATE, rotate);
	}
}
