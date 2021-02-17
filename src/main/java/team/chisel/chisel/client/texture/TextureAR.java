package team.chisel.chisel.client.texture;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.util.math.Direction;
import team.chisel.ctm.api.client.Renderable;
import team.chisel.ctm.api.client.TextureContext;
import team.chisel.ctm.api.client.TextureInfo;
import team.chisel.ctm.client.render.SpriteUnbakedQuad;
import team.chisel.ctm.client.render.SubmapImpl;
import team.chisel.ctm.client.texture.AbstractTexture;

public class TextureAR extends AbstractTexture<TextureTypeAR> {
	public TextureAR(TextureTypeAR type, TextureInfo info) {
		super(type, info);
	}

	@Override
	public Renderable transformQuad(BakedQuad bakedQuad, Direction cullFace, @Nullable TextureContext context) {
		int submapId;
		if (context instanceof TextureContextAR) {
			submapId = ((TextureContextAR) context).getSubmapId();
		} else {
			submapId = 0;
		}

		SpriteUnbakedQuad quad = unbake(bakedQuad, cullFace);
		quad.setUVBounds(sprites[0]);
		quad.applySubmap(SubmapImpl.getX2Submap(submapId));
		return quad;
	}
}
