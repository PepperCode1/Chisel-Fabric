package team.chisel.chisel.api;

import java.util.Collection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import team.chisel.chisel.impl.ChiselModeRegistryImpl;

public interface ChiselModeRegistry {
	ChiselModeRegistry INSTANCE = ChiselModeRegistryImpl.INSTANCE;
	
	void register(@NotNull ChiselMode mode);
	
	@NotNull
	Collection<ChiselMode> getAllModes();
	
	@Nullable
	ChiselMode getModeByName(String name);
}
