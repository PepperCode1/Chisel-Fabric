package team.chisel.chisel.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.screen.EnchantmentScreenHandler;

@Mixin(EnchantmentScreenHandler.class)
public class EnchantmentScreenHandlerMixin {
	@Redirect(
			method = "method_17411(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
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
