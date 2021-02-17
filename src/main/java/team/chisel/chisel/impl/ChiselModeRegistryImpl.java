package team.chisel.chisel.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import team.chisel.chisel.api.ChiselMode;
import team.chisel.chisel.api.ChiselModeRegistry;

public class ChiselModeRegistryImpl implements ChiselModeRegistry {
	public static final ChiselModeRegistryImpl INSTANCE = new ChiselModeRegistryImpl();
	
	private Map<String, ChiselMode> chiselModes = new HashMap<>();

	@Override
	public void register(@NotNull ChiselMode mode) {
		chiselModes.put(mode.name(), mode);
	}

	@Override
	public @NotNull Collection<ChiselMode> getAllModes() {
		return chiselModes.values();
	}

	@Override
	public @Nullable ChiselMode getModeByName(String name) {
		return chiselModes.get(name);
	}

}
