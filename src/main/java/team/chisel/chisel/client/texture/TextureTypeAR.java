package team.chisel.chisel.client.texture;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import team.chisel.ctm.api.client.CTMTexture;
import team.chisel.ctm.api.client.TextureContext;
import team.chisel.ctm.api.client.TextureInfo;
import team.chisel.ctm.api.client.TextureType;

// AR = Alternating Random // registered as AR
public class TextureTypeAR implements TextureType {
	@Override
	public TextureAR makeTexture(TextureInfo info) {
		return new TextureAR(this, info);
	}

	@Override
	public TextureContext getTextureContext(BlockState state, BlockRenderView world, BlockPos pos, CTMTexture<?> texture) {
		return new TextureContextAR(pos);
	}

	@Override
	public int requiredTextures() {
		return 1;
	}
}
