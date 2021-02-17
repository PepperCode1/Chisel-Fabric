package team.chisel.chisel.inventory;

import org.jetbrains.annotations.NotNull;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import team.chisel.chisel.util.SoundUtil;

public class ChiselSelectionSlot extends Slot {
	private final @NotNull ChiselScreenHandler screenHandler;

	public ChiselSelectionSlot(ChiselSelectionInventory inventory, int index, int x, int y, ChiselScreenHandler screenHandler) {
		super(inventory, index, x, y);
		this.screenHandler = screenHandler;
	}

	@Override
	public boolean canInsert(ItemStack itemStack) {
		return false;
	}

	@Override
	public boolean canTakeItems(PlayerEntity player) {
		return player.inventory.getCursorStack().isEmpty();
	}

	@Override
	public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
		ItemStack chisel = screenHandler.getChisel().copy();
		ItemStack result = screenHandler.craft(player, stack, false);
		if (screenHandler.getCurrentActionType() != SlotActionType.PICKUP) {
			result.decrement(1);
		}
		if (!result.isEmpty()) {
			SoundUtil.playSound(player, chisel, stack);
			player.inventory.setCursorStack(result);
		}
		return ItemStack.EMPTY; // Return value seems to be ignored?
	}
}
