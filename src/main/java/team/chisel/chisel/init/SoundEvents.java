package team.chisel.chisel.init;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import team.chisel.chisel.Chisel;

public class SoundEvents {
	public static final SoundEvent CHISEL_DEFAULT = register("chisel.default");
	public static final SoundEvent CHISEL_DIRT = register("chisel.dirt");
	public static final SoundEvent CHISEL_WOOD = register("chisel.wood");
	
	public static final SoundEvent BLOCK_METAL_BREAK = register("block.metal.break");
	public static final SoundEvent BLOCK_METAL_FALL = register("block.metal.fall");
	public static final SoundEvent BLOCK_METAL_HIT = register("block.metal.hit");
	public static final SoundEvent BLOCK_METAL_PLACE = register("block.metal.place");
	public static final SoundEvent BLOCK_METAL_STEP = register("block.metal.step");
	
	private static SoundEvent register(String string) {
		Identifier identifier = new Identifier(Chisel.RESOURCE_NAMESPACE, string);
		return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
	}
	
	public static void init() {
	}
}
