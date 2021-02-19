package team.chisel.chisel.client.util;

import net.minecraft.text.TranslatableText;

public enum ChiselLangKeys {
	TOOLTIP_BLOCK("tooltip.block"),

	TOOLTIP_CHISEL_OPEN_GUI_RIGHT_CLICK("tooltip.chisel.open_gui.right_click"),
	TOOLTIP_CHISEL_IN_WORLD_LEFT_CLICK("tooltip.chisel.in_world.left_click"),
	TOOLTIP_CHISEL_IN_INVENTORY_TARGET_BLOCK("tooltip.chisel.in_inventory.target_block"),
	TOOLTIP_CHISEL_OPEN_GUI("tooltip.chisel.open_gui"),
	TOOLTIP_CHISEL_IN_WORLD("tooltip.chisel.in_world"),
	TOOLTIP_CHISEL_IN_INVENTORY("tooltip.chisel.in_inventory"),
	TOOLTIP_CHISEL_MODES("tooltip.chisel.modes"),
	TOOLTIP_CHISEL_SELECTED_MODE("tooltip.chisel.selected_mode"),

	CHISEL_HITECH_PREVIEW("chisel.hitech.preview"),

	CHISEL_HITECH_BUTTON_CHISEL("chisel.hitech.button.chisel"),
	CHISEL_HITECH_BUTTON_CHISEL_ALL("chisel.hitech.button.chisel_all"),

	/*JEI_TITLE("jei.title"),
	JEI_DESC_CHISEL_GENERIC("jei.desc.chisel.generic"),
	JEI_DESC_CHISEL_IRON("jei.desc.chisel.iron"),
	JEI_DESC_CHISEL_DIAMOND("jei.desc.chisel.diamond"),
	JEI_DESC_CHISEL_HITECH("jei.desc.chisel.hitech"),*/
	;

	private final String key;
	private TranslatableText text;

	private ChiselLangKeys(String key) {
		this.key = key;
		this.text = new TranslatableText(key);
	}

	public TranslatableText getText() {
		return text;
	}

	public TranslatableText format(Object... args) {
		return new TranslatableText(key, args);
	}
}
