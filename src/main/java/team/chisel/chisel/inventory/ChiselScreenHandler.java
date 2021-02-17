package team.chisel.chisel.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import team.chisel.chisel.api.CarvingGroupRegistry;
import team.chisel.chisel.api.CarvingVariant;
import team.chisel.chisel.api.ChiselItem;
import team.chisel.chisel.util.ChiselNBT;
import team.chisel.chisel.util.SoundUtil;

public class ChiselScreenHandler extends ScreenHandler {
	protected final PlayerInventory playerInventory;
	protected final Hand hand;
	protected final ChiselSelectionInventory chiselInventory;
	protected final int chiselSlot;
	protected final ItemStack chisel;
	private SlotActionType currentSlotActionType;

	public ChiselScreenHandler(ScreenHandlerType<? extends ChiselScreenHandler> type, int syncId, PlayerInventory playerInventory, Hand hand) {
		this(type, syncId, playerInventory, hand, new ChiselSelectionInventory(60, hand));
	}
	
	public ChiselScreenHandler(ScreenHandlerType<? extends ChiselScreenHandler> type, int syncId, PlayerInventory playerInventory, Hand hand, ChiselSelectionInventory chiselInventory) {
		super(type, syncId);
		
		this.playerInventory = playerInventory;
		this.hand = hand;
		this.chiselInventory = chiselInventory;

		this.chiselSlot = this.hand == Hand.MAIN_HAND ? this.playerInventory.selectedSlot : this.playerInventory.size() - 1;
		this.chisel = this.playerInventory.getStack(chiselSlot);

		addSlots();

		if (!chisel.isEmpty() && chisel.hasTag()) {
			ItemStack stack = ChiselNBT.getChiselTarget(chisel);
			this.chiselInventory.setInputSlotStack(stack);
			this.chiselInventory.updateVariants();
		}
	}
	
	protected void addSlots() {
		int top = 8;
		int left = 62;

		// selection slots
		for (int i = 0; i < chiselInventory.getSelectionSize(); i++) {
			addSlot(new ChiselSelectionSlot(chiselInventory, i, left + ((i % 10) * 18), top + ((i / 10) * 18), this));
		}

		// main slot
		addSlot(new ChiselInputSlot(chiselInventory, chiselInventory.getInputSlotId(), 24, 24, this));

		top += 112;
		left += 9;
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
	public boolean canUse(PlayerEntity entityplayer) {
		return chiselInventory.canPlayerUse(entityplayer);
	}

	@Override
	public ItemStack onSlotClick(int slotIndex, int dragType, SlotActionType actionType, PlayerEntity player) {
		if (actionType != SlotActionType.QUICK_CRAFT && slotIndex >= 0) {
			// we need to subtract away all the other slots
			int clickedSlot = slotIndex - chiselInventory.size() - 27;
//			//Chisel.debug("Slot clicked is " + slotId + " and slot length is " + slots.size());
//			try {
//				Slot slot = getSlot(slotIndex);
//				//Chisel.debug("Slot is " + slot);
//			} catch (Exception exception) {
//				//Chisel.debug("Exception getting slot");
//				exception.printStackTrace();
//			}

			// if the player has clicked on the chisel or is trying to use a number key to force an itemstack into the slot the chisel is in
			if (clickedSlot == chiselSlot || actionType == SlotActionType.SWAP) {
				return ItemStack.EMPTY;
			}
		}
		
		currentSlotActionType = actionType;
		return super.onSlotClick(slotIndex, dragType, actionType, player);
	}

	@Override
	public void close(PlayerEntity player) {
		chiselInventory.clear();
		super.close(player);
	}

	@Override
	public ItemStack transferSlot(PlayerEntity player, int slotIndex) {
		Slot slot = getSlot(slotIndex);
		ItemStack itemStack = ItemStack.EMPTY;

		if (slot != null && slot.hasStack()) {
			ItemStack itemStack1 = slot.getStack();
			itemStack = itemStack1.copy();

			if (slotIndex > chiselInventory.getSelectionSize()) {
				if (!insertItem(itemStack1, chiselInventory.getSelectionSize(), chiselInventory.getSelectionSize() + 1, false)) {
					return ItemStack.EMPTY;
				}
			} else {
				if (slotIndex < chiselInventory.getSelectionSize() && !itemStack1.isEmpty()) {
					//ChiselSelectionSlot selectionSlot = (ChiselSelectionSlot) slot;
					ItemStack check = craft(player, itemStack1, true);
					if (check.isEmpty()) {
						return ItemStack.EMPTY;
					}
					if (!this.insertItem(check, chiselInventory.getSelectionSize() + 1, chiselInventory.getSelectionSize() + 1 + 36, true)) {
						return ItemStack.EMPTY;
					}
					SoundUtil.playSound(player, chisel, itemStack1);
					itemStack1 = craft(player, itemStack1, false);
					itemStack1.decrement(check.getCount());
					chiselInventory.setInputSlotStack(check);
				} else if (!insertItem(itemStack1, chiselInventory.getSelectionSize() + 1, chiselInventory.getSelectionSize() + 1 + 36, true)) {
					return ItemStack.EMPTY;
				}
			}
			
			boolean clearSlot = slotIndex >= chiselInventory.getSelectionSize() || chiselInventory.getInputSlotStack().isEmpty();

			slot.onStackChanged(itemStack1, itemStack);

			if (itemStack1.isEmpty()) {
				if (clearSlot) {
					slot.setStack(ItemStack.EMPTY);
				}
			} else {
				slot.markDirty();
			}

			chiselInventory.updateVariants();

			if (itemStack1.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}
			if (slotIndex >= chiselInventory.getSelectionSize()) {
				slot.onTakeItem(player, itemStack1);
			}
			if (itemStack1.isEmpty()) {
				if (clearSlot) {
					slot.setStack(ItemStack.EMPTY);
				}
				return ItemStack.EMPTY;
			} else {
				if (!clearSlot) {
					slot.setStack(itemStack1);
				}
				return itemStack1;
			}
		}
		
		return itemStack;
	}
	
	public PlayerInventory getPlayerInventory() {
		return playerInventory;
	}
	
	public Hand getHand() {
		return hand;
	}
	
	public ChiselSelectionInventory getChiselInventory() {
		return chiselInventory;
	}
	
	public int getChiselSlot() {
		return chiselSlot;
	}
	
	public ItemStack getChisel() {
		return chisel;
	}
	
	public SlotActionType getCurrentActionType() {
		return currentSlotActionType;
	}
	
	public Slot getInputSlot() {
		return getSlot(chiselInventory.getInputSlotId());
	}

	public void onInputSlotChanged() {
		ChiselNBT.setChiselTarget(chisel, chiselInventory.getInputSlotStack());
		chiselInventory.updateVariants();
	}

	public void onChiselBroken() {
		if (!playerInventory.player.world.isClient()) {
			ItemStack stack = chiselInventory.getInputSlotStack();
			chiselInventory.setInputSlotStack(ItemStack.EMPTY);
			playerInventory.main.set(chiselSlot, stack);
		}
	}
	
	public ItemStack craft(PlayerEntity player, ItemStack itemStack, boolean simulate) {
		ItemStack crafted = chiselInventory.getInputSlotStack();
		ItemStack chisel = this.chisel;
		if (simulate) {
			itemStack = itemStack.copy();
			crafted = crafted.isEmpty() ? ItemStack.EMPTY : crafted.copy();
			chisel = chisel.copy();
		}
		ItemStack result = ItemStack.EMPTY;
		if (!chisel.isEmpty() && !crafted.isEmpty()) {
			ChiselItem item = (ChiselItem) this.chisel.getItem();
			CarvingVariant variant = CarvingGroupRegistry.INSTANCE.getVariant(itemStack.getItem());
			if (variant == null) {
				throw new IllegalArgumentException("Variant for itemstack is null!");
			}
			if (!item.canChisel(player, player.world, chisel, variant)) {
				return result;
			}
			result = item.craftItem(chisel, crafted, itemStack, player, null);
			if (!simulate) {
				chiselInventory.setInputSlotStack(crafted.getCount() == 0 ? ItemStack.EMPTY : crafted);
				onInputSlotChanged();
				item.onChisel(player, player.world, chisel, variant);
				if (chisel.getCount() == 0) {
					playerInventory.setStack(chiselSlot, ItemStack.EMPTY);
				}
				if (!crafted.isEmpty() && !item.canChisel(player, player.world, chisel, variant)) {
					onChiselBroken();
				}

				sendContentUpdates();
			}
		}
		
		return result;
	}
}
