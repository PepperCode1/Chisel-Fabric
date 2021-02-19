package team.chisel.chisel.inventory;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import team.chisel.chisel.api.CarvingGroup;
import team.chisel.chisel.api.CarvingGroupRegistry;
import team.chisel.chisel.api.ChiselItem;
import team.chisel.chisel.util.ChiselNBT;
import team.chisel.chisel.util.SoundUtil;

public class HitechChiselScreenHandler extends ChiselScreenHandler {
	private @Nullable Slot selection;
	private @Nullable Slot target;
	private List<Slot> selectionDuplicates = ImmutableList.of();
	private @Nullable CarvingGroup currentGroup;

	public HitechChiselScreenHandler(ScreenHandlerType<? extends ChiselScreenHandler> type, int syncId, PlayerInventory playerInventory, Hand hand) {
		this(type, syncId, playerInventory, hand, new ChiselSelectionInventory(63, hand));
	}

	public HitechChiselScreenHandler(ScreenHandlerType<? extends ChiselScreenHandler> type, int syncId, PlayerInventory playerInventory, Hand hand, ChiselSelectionInventory chiselInventory) {
		super(type, syncId, playerInventory, hand, chiselInventory);

		int selectionSlot = ChiselNBT.getHitechSelection(chisel);
		if (selectionSlot >= this.chiselInventory.size()) {
			setSelection(getSlot(selectionSlot));
		}

		int targetSlot = ChiselNBT.getHitechTarget(chisel);
		if (targetSlot >= 0 && targetSlot < this.chiselInventory.size() - 1) {
			setTarget(getSlot(targetSlot));
		}
	}

	@Override
	protected void addSlots() {
		int top = 8;
		int left = 88;

		// selection slots
		for (int i = 0; i < chiselInventory.getSelectionSize(); i++) {
			addSlot(new ChiselSelectionSlot(chiselInventory, i, left + ((i % 9) * 18), top + ((i / 9) * 18), this));
		}

		// main slot
		//addSlot(new ChiselInputSlot(chiselInventory, chiselInventory.getInputSlotId(), -1000, 0, this));

		top += 130;
		// main inv
		for (int i = 0; i < 27; i++) {
			addSlot(new Slot(playerInventory, i + 9, left + ((i % 9) * 18), top + (i / 9) * 18));
		}

		top += 58;
		for (int i = 0; i < 9; i++) {
			addSlot(new Slot(playerInventory, i, left + ((i % 9) * 18), top + (i / 9) * 18));
		}
	}

	@Override
	public ItemStack onSlotClick(int slotIndex, int dragType, SlotActionType actionType, PlayerEntity player) {
		if (slotIndex >= 0) {
			Slot slot = getSlot(slotIndex);
			if (slotIndex < chiselInventory.getSelectionSize()) {
				setTarget(slot);
			} else if (dragType == 1) {
				ItemStack toFind = slot.getStack();
				if (!toFind.isEmpty()) {
					for (int i = 0; i < chiselInventory.getSelectionSize(); i++) {
						if (ItemStack.areItemsEqual(toFind, chiselInventory.getStack(i))) {
							setTarget(getSlot(i));
						}
					}
				}
			} else {
				setSelection(slot);
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void close(PlayerEntity player) {
		ChiselNBT.setChiselTarget(chisel, getTargetStack());
		super.close(player);
	}

	@Override
	public void onChiselBroken() {
	}

	public Slot getSelection() {
		return selection;
	}

	public Slot getTarget() {
		return target;
	}

	public List<Slot> getSelectionDuplicates() {
		return selectionDuplicates;
	}

	public ItemStack getSelectionStack() {
		return selection == null ? ItemStack.EMPTY : selection.getStack();
	}

	public ItemStack getTargetStack() {
		return target == null ? ItemStack.EMPTY : target.getStack();
	}

	public void setSelection(@Nullable Slot slot) {
		selection = slot;

		if (slot == null || !slot.hasStack()) {
			currentGroup = null;
			selectionDuplicates = ImmutableList.of();
			setTarget(null);
		} else {
			ImmutableList.Builder<Slot> builder = ImmutableList.builder();
			for (int i = chiselInventory.getSelectionSize(); i < slots.size(); i++) {
				Slot s = getSlot(i);
				if (slot != s && ItemStack.areItemsEqual(slot.getStack(), s.getStack())) {
					builder.add(s);
				}
			}
			selectionDuplicates = builder.build();

			CarvingGroup group = CarvingGroupRegistry.INSTANCE.getGroup(slot.getStack().getItem());
			if (currentGroup != null && group != currentGroup) {
				setTarget(null);
			}
			currentGroup = group;
		}

		ItemStack stack = slot == null ? ItemStack.EMPTY : slot.getStack();
		chiselInventory.setInputSlotStack(stack);
		chiselInventory.updateVariants();
		ChiselNBT.setHitechSelection(chisel, Optional.ofNullable(selection).map((s) -> s.id).orElse(-1));
	}

	public void setTarget(@Nullable Slot slot) {
		target = slot;
		ChiselNBT.setHitechTarget(chisel, Optional.ofNullable(selection).map((s) -> s.id).orElse(-1));
	}

	public void chiselAll(PlayerEntity player, int[] slots) {
		ItemStack chisel = this.chisel;
		ItemStack originalChisel = chisel.copy();
		ItemStack target = getTargetStack();

		if (chisel.isEmpty() || target.isEmpty()) {
			return;
		}

		if (!(chisel.getItem() instanceof ChiselItem)) {
			return;
		}

		CarvingGroupRegistry registry = CarvingGroupRegistry.INSTANCE;
		CarvingGroup targetGroup = registry.getGroup(target.getItem());
		boolean playSound = false;

		for (int id : slots) {
			Slot slot = getSlot(id);
			ItemStack stack = slot.getStack();
			if (!stack.isEmpty()) {
				if (targetGroup != registry.getGroup(stack.getItem())) {
					continue;
				}
				chiselInventory.setInputSlotStack(stack);
				ItemStack result = craft(player, target.copy(), false);
				if (!result.isEmpty()) {
					slot.setStack(result);
					playSound = true;
				}
			}
			if (chisel.isEmpty()) {
				break;
			}
		}

		chiselInventory.setInputSlotStack(getSelectionStack());
		chiselInventory.updateVariants();

		if (playSound) {
			SoundUtil.playSound(player, originalChisel, target);
		}
	}
}
