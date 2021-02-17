package team.chisel.chisel.carving;

import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

public class ARRPResourceRegistry implements DynamicResourceRegistry {
	private RuntimeResourcePack pack;
	
	public ARRPResourceRegistry(RuntimeResourcePack pack) {
		this.pack = pack;
	}

	@Override
	public void init() {
		RRPCallback.EVENT.register(packs -> packs.add(pack));
	}
	
	@Override
	public void register(ResourceType type, Identifier identifier, String data) {
		pack.addResource(type, identifier, data.getBytes());
	}
	
	public RuntimeResourcePack getPack() {
		return pack;
	}
}
