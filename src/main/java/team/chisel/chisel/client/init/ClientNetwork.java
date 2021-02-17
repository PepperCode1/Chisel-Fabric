package team.chisel.chisel.client.init;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import team.chisel.chisel.Chisel;
import team.chisel.chisel.api.ChiselMode;

public class ClientNetwork {
	public static final Identifier CHISEL_MODE = new Identifier(Chisel.MOD_ID, "chisel_mode");
	public static final Identifier CHISEL_BUTTON = new Identifier(Chisel.MOD_ID, "chisel_button");
	public static final Identifier HITECH_SETTINGS = new Identifier(Chisel.MOD_ID, "hitech_settings");

	public static void init() {
	}

	public static Packet<?> createChiselModePacket(int slot, ChiselMode mode) {
		PacketByteBuf buffer = PacketByteBufs.create();
		buffer.writeInt(slot);
		buffer.writeString(mode.name(), 256);
		return ClientPlayNetworking.createC2SPacket(CHISEL_MODE, buffer);
	}

	public static Packet<?> createChiselButtonPacket(int[] slots) {
		PacketByteBuf buffer = PacketByteBufs.create();
		buffer.writeIntArray(slots);
		return ClientPlayNetworking.createC2SPacket(CHISEL_BUTTON, buffer);
	}

	public static Packet<?> createHitechSettingsPacket(int chiselSlot, int type, boolean rotate) {
		PacketByteBuf buffer = PacketByteBufs.create();
		buffer.writeInt(chiselSlot);
		buffer.writeInt(type);
		buffer.writeBoolean(rotate);
		return ClientPlayNetworking.createC2SPacket(HITECH_SETTINGS, buffer);
	}
}
