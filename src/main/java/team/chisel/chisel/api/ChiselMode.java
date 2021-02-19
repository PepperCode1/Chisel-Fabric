package team.chisel.chisel.api;

import java.util.Locale;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import team.chisel.chisel.util.Point2i;

public interface ChiselMode {
	/**
	 * Retrieve all valid positions that can be chiseled from where the player is targeting. Must consider state equality, if necessary.
	 * 
	 * @param player
	 *			The player.
	 * @param pos
	 *			The position of the targeted block.
	 * @param side
	 *			The side of the block being targeted.
	 * @return All valid positions to be chiseled.
	 */
	Iterable<? extends BlockPos> getCandidates(PlayerEntity player, BlockPos pos, Direction side);

	Box getBounds(Direction side);

	/**
	 * Implemented implicitly by enums. If your ChiselMode is not an enum constant, this needs to be implemented explicitly.
	 * 
	 * @return The name of the mode.
	 */
	String name();

	default String getNameKey() {
		return "chisel.mode." + name().toLowerCase(Locale.ROOT);
	}

	default String getDescriptionKey() {
		return getNameKey() + ".desc";
	}

	default Text getNameText() {
		return new TranslatableText(getNameKey());
	}

	default Text getDescriptionText() {
		return new TranslatableText(getDescriptionKey());
	}

	default long[] getCacheState(BlockPos origin, Direction side) { // TODO: Remove?
		return new long[] {origin.asLong(), side.ordinal()};
	}

	Identifier getSpriteSheet();

	Point2i getSpritePos();
}
