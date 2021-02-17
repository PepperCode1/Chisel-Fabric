package team.chisel.chisel.api;

import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Implement this on items which can be used to chisel blocks.
 */
public interface ChiselItem {
	/**
	 * Checks whether the chisel can have its GUI opened, and will be called every tick to assure the GUI can <i>remain</i> open.
	 * 
	 * @param player
	 *			The player holding the chisel. It can always be assumed that the player's current item will be this.
	 * @param world
	 *			{@link World} object
	 * @param hand
	 *			The {@link Hand} which the chisel is in. Use this and the {@code player} parameter to get stack context.
	 * @return True if the GUI should open. False otherwise.
	 */
	boolean canOpenGui(PlayerEntity player, World world, Hand hand);

	/**
	 * Called when this chisel is ready to open a GUI.
	 * 
	 * @param player
	 *			The player holding the chisel. It can always be assumed that the player's current item will be this.
	 * @param world
	 *			{@link World} object
	 * @param hand
	 *			The {@link Hand} which the chisel is in. Use this and the {@code player} parameter to get stack context.
	 * @return The factory used the create the GUI.
	 */
	NamedScreenHandlerFactory getHandlerFactory(PlayerEntity player, World world, Hand hand);

	/**
	 * Called when an item is chiseled using this chisel.
	 * 
	 * @param player
	 *			The {@link PlayerEntity} performing the chiseling.
	 * @param world
	 *			{@link World} object.
	 * @param chisel
	 *			The {@link ItemStack} representing the chisel.
	 * @param target
	 *			The {@link CarvingVariant} representing the target item.
	 * @return Unused.
	 */
	boolean onChisel(PlayerEntity player, World world, ItemStack chisel, CarvingVariant target);

	/**
	 * Called to check if this {@link ItemStack} can be chiseled in this chisel. If not, there will be no possible variants displayed in the GUI.
	 * <p>
	 * It is not necessary to take into account whether this item <i>has</i> any variants, this method will only be called after that check.
	 * 
	 * @param player
	 *			The {@link PlayerEntity} performing the chiseling.
	 * @param world
	 *			{@link World} object.
	 * @param chisel
	 *			The {@link ItemStack} representing the chisel.
	 * @param target
	 *			The {@link CarvingVariant} representing the target item.
	 * @return True if the current target can be chiseled into anything. False otherwise.
	 */
	boolean canChisel(PlayerEntity player, World world, ItemStack chisel, CarvingVariant target);

	/**
	 * Allows you to control if your item can chisel this block in the world.
	 * 
	 * @param player
	 *			{@link PlayerEntity The player} holding the chisel.
	 * @param world
	 *			{@link World} object.
	 * @param hand
	 *			The {@link Hand} which the chisel is in. Use this and the {@code player} parameter to get stack context.
	 * @param pos
	 *			The {@link BlockPos position} of the block being chiseled.
	 * @param state
	 *			The {@link BlockState} of the block being chiseled.
	 * @return True if the chiseling should take place. False otherwise.
	 */
	boolean canChiselBlock(PlayerEntity player, World world, Hand hand, BlockPos pos, BlockState state);

	/**
	 * Allows you to control if your item supports a given chisel mode.
	 * 
	 * @param player
	 *			{@link PlayerEntity The player} holding the chisel.
	 * @param chisel
	 *			The {@link ItemStack} of the current chisel.
	 * @param mode
	 *			The current {@link ChiselMode}.
	 * @return True if the chisel supports this mode. False otherwise.
	 */
	boolean supportsMode(PlayerEntity player, ItemStack chisel, ChiselMode mode);
	
	/**
	 * Allows this chisel to provide a different sound for the given {@link CarvingVariant}.
	 * 
	 * @param world
	 *			{@link World} object.
	 * @param player
	 *			{@link PlayerEntity The player} holding the chisel.
	 * @param chisel
	 *			The {@link ItemStack} representing the chisel.
	 * @param target
	 *			The {@link CarvingVariant} representing the target block.
	 * @return A sound to play, instead of the variation's sound, or null for default.
	 */
	default @Nullable SoundEvent getOverrideSound(PlayerEntity player, World world, ItemStack chisel, CarvingVariant target) {
		return null;
	}
	
	/**
	 * Chisels a stack into another stack, taking into consideration this chisel's damage.
	 * 
	 * @param chisel
	 *			The {@link ItemStack} being used as a chisel.
	 * @param source
	 *			The source {@link ItemStack}. This is the stack of items that is being chiseled into something else. This must be modified by this function. i.e. if the chisel has only 1 damage
	 *			left, the source stack size should be decremented by 1.
	 * @param target
	 *			The target stack, the type of which the source stack is being chiseled into. Do NOT modify this stack, instead return the result of the craft.
	 * @param player
	 *			The player doing the chiseling.
	 * @param onBreak
	 *			A callback to perform when the chisel breaks.
	 * @return The result of the craft.
	 */
	default ItemStack craftItem(ItemStack chisel, ItemStack source, ItemStack target, PlayerEntity player, Consumer<PlayerEntity> onBreak) {
		if (chisel.isEmpty()) return ItemStack.EMPTY;
		int toCraft = Math.min(source.getCount(), target.getMaxCount());
		if (chisel.isDamageable()) {
			int damageLeft = chisel.getMaxDamage() - chisel.getDamage();
			toCraft = Math.min(toCraft, damageLeft);
			
			if (player.getEntityWorld().isClient()) { // simulate damage on client so interaction manager thinks block is still being broken
				if (!player.abilities.creativeMode) {
					chisel.damage(toCraft, player.getRandom(), null);
				}
			} else {
				chisel.damage(toCraft, player, (p) -> {
					p.sendToolBreakStatus(p.getActiveHand());
					if (onBreak != null) {
						onBreak.accept(p);
					}
				});
			}
		}
		ItemStack result = target.copy();
		source.decrement(toCraft);
		result.setCount(toCraft);
		return result;
	}
}
