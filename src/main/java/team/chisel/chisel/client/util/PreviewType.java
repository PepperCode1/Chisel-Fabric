package team.chisel.chisel.client.util;

import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableSet;

import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

public enum PreviewType { // TODO: Make into registry
	PANEL(16, generateBetween(-1, -1, 0, 1, 1, 0)),
	HOLLOW(16, ArrayUtils.removeElement(generateBetween(-1, -1, 0, 1, 1, 0), new BlockPos(0, 0, 0))),
	PLUS(20,
		new BlockPos(0, -1, 0),
		new BlockPos(0, 0, 0),
		new BlockPos(1, 0, 0),
		new BlockPos(-1, 0, 0),
		new BlockPos(0, 1, 0)
	),
	SINGLE(50, new BlockPos(0, 0, 0))
	;
	
	private static @NotNull BlockPos[] generateBetween(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		BlockPos[] ret = new BlockPos[(maxX - minX + 1) * (maxY - minY + 1) * (maxZ - minZ + 1)];
		int i = 0;
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				for (int z = minZ; z <= maxZ; z++) {
					ret[i++] = new BlockPos(x, y, z);
				}
			}
		}
		return ret;
	}
	
	private final float scale;
	private final Set<BlockPos> positions;
	private final TranslatableText text;
	
	private PreviewType(float scale, @NotNull BlockPos... positions) {
		this.scale = scale;
		this.positions = ImmutableSet.copyOf(positions);
		this.text = new TranslatableText("chisel.hitech.preview."+name().toLowerCase());
	}
	
	public float getScale() {
		return scale;
	}
	
	public Set<BlockPos> getPositions() {
		return positions;
	}
	
	public TranslatableText getText() {
		return text;
	}
}