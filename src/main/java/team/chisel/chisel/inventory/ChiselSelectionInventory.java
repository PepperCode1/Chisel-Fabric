package team.chisel.chisel.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import team.chisel.chisel.api.CarvingGroup;
import team.chisel.chisel.api.CarvingGroupRegistry;
import team.chisel.chisel.api.CarvingVariant;
import team.chisel.chisel.api.ChiselItem;
import team.chisel.chisel.item.ChiselItemImpl;

public class ChiselSelectionInventory implements Inventory {
	private final int size;
	private final int selectionSize;
	private final int inputSlotId;
	private final DefaultedList<ItemStack> stacks;
	private final Hand chiselHand;
	private int activeVariants = 0;

	public ChiselSelectionInventory(int selectionSize, Hand chiselHand) {
		this.selectionSize = selectionSize;
		this.inputSlotId = this.selectionSize;
		this.size = this.selectionSize + 1;
		stacks = DefaultedList.ofSize(this.size, ItemStack.EMPTY);
		this.chiselHand = chiselHand;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack stack : this.stacks) {
			if (!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStack(int slot) {
		return isSlotValid(slot) ? this.stacks.get(slot) : ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
		ItemStack stack = stacks.get(slot);
		if (!stack.isEmpty()) {
			if (stack.getCount() <= amount) {
				setStack(slot, ItemStack.EMPTY);
				onInventoryUpdate(slot);
				return stack;
			} else {
				ItemStack split = stack.split(amount);

				if (stack.getCount() == 0) {
					setStack(slot, ItemStack.EMPTY);
				}
				
				onInventoryUpdate(slot);

				return split;
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStack(int slot) {
		ItemStack stack = getStack(slot);
		setStack(slot, ItemStack.EMPTY);
		return stack;
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		stacks.set(slot, stack);
		onInventoryUpdate(slot);
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		ItemStack chisel = player.getStackInHand(chiselHand);
		return !chisel.isEmpty() && chisel.getItem() instanceof ChiselItem && ((ChiselItem) chisel.getItem()).canOpenGui(player, player.world, chiselHand);
	}

	@Override
	public void clear() {
		activeVariants = 0;
		stacks.clear();
	}

	@Override
	public boolean isValid(int slot, ItemStack stack) {
		return slot == inputSlotId && (stack.isEmpty() || !(stack.getItem() instanceof ChiselItemImpl));
	}
	
	public boolean isSlotValid(int slot) {
		return slot >= 0 && slot < this.stacks.size();
	}
	
	public int getActiveVariants() {
		return activeVariants;
	}
	
	public int getSelectionSize() {
		return selectionSize;
	}
	
	public int getInputSlotId() {
		return inputSlotId;
	}

	public ItemStack getInputSlotStack() {
		return stacks.get(inputSlotId);
	}
	
	public void setInputSlotStack(ItemStack stack) {
		setStack(inputSlotId, stack);
	}
	
	public void clearVariants() {
		activeVariants = 0;
		for (int i = 0; i < this.selectionSize; ++i) {
			stacks.set(i, ItemStack.EMPTY);
		}
	}

	public void updateVariants() {
		clearVariants();
		
		ItemStack chiseledItem = getInputSlotStack();
		if (chiseledItem.isEmpty()) {
			return;
		}
		
		CarvingGroup group = CarvingGroupRegistry.INSTANCE.getGroup(chiseledItem.getItem());
		if (group != null) {
			for (CarvingVariant variant : group.getCarvingVariants()) {
				if (activeVariants >= selectionSize) {
					break;
				}
				ItemStack stack = new ItemStack(variant.getItem());
				setStack(activeVariants, stack);
				activeVariants++;
			}
		}
	}

	public void onInventoryUpdate(int slot) {
	}
}
