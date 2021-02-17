package team.chisel.chisel.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EnchantingTableBlock;

@Mixin(EnchantingTableBlock.class)
public class EnchantingTableBlockMixin {
	@Redirect(
			method = "randomDisplayTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"
			))
	public boolean onCheckBookshelf(BlockState blockState, Block block) {
		if (block == Blocks.BOOKSHELF) {
			return true;
		}
		return true;
	}
}
