package team.chisel.chisel.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.entity.decoration.AbstractDecorationEntity;

@Mixin(AbstractDecorationEntity.class)
public interface AbstractDecorationEntityAccessor {
	@Invoker("updateAttachmentPosition")
	public void callUpdateAttachmentPosition();
}
