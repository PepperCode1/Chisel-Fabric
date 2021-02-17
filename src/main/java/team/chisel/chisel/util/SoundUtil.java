package team.chisel.chisel.util;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import team.chisel.chisel.api.CarvingGroup;
import team.chisel.chisel.api.CarvingGroupRegistry;
import team.chisel.chisel.api.CarvingVariant;
import team.chisel.chisel.api.ChiselItem;
import team.chisel.chisel.init.SoundEvents;

public class SoundUtil {
	public static SoundEvent getSound(PlayerEntity player, ItemStack chisel, @Nullable BlockState target) {
		if (target != null && !chisel.isEmpty()) {
			SoundEvent sound = ((ChiselItem) chisel.getItem()).getOverrideSound(player, player.getEntityWorld(), chisel, CarvingGroupRegistry.INSTANCE.getVariant(target));
			if (sound != null) {
				return sound;
			}
		}
		CarvingGroup group = CarvingGroupRegistry.INSTANCE.getGroup(target != null ? target : Blocks.AIR.getDefaultState());
		SoundEvent sound = group.getSoundEvent();
		if (sound == null) {
			return SoundEvents.CHISEL_DEFAULT;
		} else {
			return sound;
		}
	}
	
	public static void playSound(PlayerEntity player, ItemStack chisel, ItemStack source) {
		CarvingVariant variant = CarvingGroupRegistry.INSTANCE.getVariant(source.getItem());
		BlockState blockState = variant == null ? null : variant.getBlockState();
		if (blockState == null) {
			if (source.getItem() instanceof BlockItem) {
				blockState = ((BlockItem) source.getItem()).getBlock().getDefaultState();
			} else {
				blockState = Blocks.STONE.getDefaultState(); // fallback
			}
		}
		playSound(player, chisel, blockState);
	}

	public static void playSound(PlayerEntity player, ItemStack chisel, @Nullable BlockState target) {
		SoundEvent sound = getSound(player, chisel, target);
		playSound(player, player.getBlockPos(), sound);
	}

	public static void playSound(PlayerEntity player, BlockPos pos, SoundEvent sound) {
		playSound(player, pos, sound, SoundCategory.BLOCKS);
	}
	
	public static void playSound(PlayerEntity player, BlockPos pos, SoundEvent sound, SoundCategory category) {
		World world = player.getEntityWorld();
		world.playSound(player, pos, sound, category, 0.3F + 0.7F * world.random.nextFloat(), 0.6F + 0.4F * world.random.nextFloat());
	}
}
