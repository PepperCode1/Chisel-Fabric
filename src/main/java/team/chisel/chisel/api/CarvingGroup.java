package team.chisel.chisel.api;

import java.util.Iterator;
import java.util.List;

import net.minecraft.sound.SoundEvent;

public interface CarvingGroup extends Iterable<CarvingVariant> {
	List<CarvingVariant> getCarvingVariants();
	
	default Iterator<CarvingVariant> iterator() {
		return getCarvingVariants().iterator();
	}
	
	default SoundEvent getSoundEvent() {
		return null;
	}
}
