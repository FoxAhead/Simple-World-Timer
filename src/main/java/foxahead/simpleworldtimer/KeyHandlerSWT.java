package foxahead.simpleworldtimer;

import java.util.EnumSet;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import foxahead.simpleworldtimer.gui.GuiSWTOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class KeyHandlerSWT extends KeyHandler {

	private static Minecraft mc = Minecraft.getMinecraft();
	public static KeyBinding keySWT = new KeyBinding("[SimpleWorldTimer] Options", Keyboard.KEY_H);

	public KeyHandlerSWT() {
		super(new KeyBinding[] { keySWT }, new boolean[] { false });
	}

	@Override
	public String getLabel() {
		return "SWTKeyHandler";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
		if (tickEnd) {
			// if (tickEnd && mc.inGameHasFocus) {
			if (kb == keySWT) {
				if (mc.gameSettings.keyBindSneak.pressed) {
					ConfigSWT.setEnable(!ConfigSWT.getEnable());
				} else {
					if (mc.currentScreen == null) {
						if (mc.inGameHasFocus) {
							mc.displayGuiScreen(new GuiSWTOptions());
						}
					} else if (mc.currentScreen instanceof GuiSWTOptions) {
						if (!((GuiSWTOptions) mc.currentScreen).isTyping()) {
							((GuiSWTOptions) mc.currentScreen).closeMe();
						}
					}
				}
			}
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

}
