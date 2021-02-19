package team.chisel.chisel.client.texture;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import team.chisel.ctm.client.texture.context.TextureContextPosition;

public class TextureContextAR extends TextureContextPosition {
	private static final Random RANDOM = new Random();

	private final int submapId;

	public TextureContextAR(BlockPos pos) {
		super(pos);

		submapId = getSubmapId(pos);
	}

	public int getSubmapId() {
		return submapId;
	}

	@Override
	public long getCompressedData() {
		return submapId;
	}

	public static int getSubmapId(BlockPos pos) {
		int evenCoords = 0;
		if (pos.getX() % 2 == 0) {
			evenCoords++;
		}
		if (pos.getY() % 2 == 0) {
			evenCoords++;
		}
		if (pos.getZ() % 2 == 0) {
			evenCoords++;
		}

		RANDOM.setSeed(MathHelper.hashCode(pos));
		RANDOM.nextBoolean();

		return RANDOM.nextInt(2) * 2 + evenCoords % 2;
	}
}
