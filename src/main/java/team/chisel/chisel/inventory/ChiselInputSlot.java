package team.chisel.chisel.inventory;

import org.jetbrains.annotations.NotNull;

import net.minecraft.screen.slot.Slot;

public class ChiselInputSlot extends Slot {
	private final ChiselScreenHandler screenHandler;

	public ChiselInputSlot(@NotNull ChiselSelectionInventory inventory, int index, int x, int y, ChiselScreenHandler screenHandler) {
		super(inventory, index, x, y);
		this.screenHandler = screenHandler;
	}

	@Override
	public void markDirty() {
		super.markDirty();
		screenHandler.onInputSlotChanged();
	}
}
