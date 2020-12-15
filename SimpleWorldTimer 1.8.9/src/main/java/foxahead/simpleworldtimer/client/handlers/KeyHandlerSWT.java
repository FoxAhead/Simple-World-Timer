package foxahead.simpleworldtimer.client.handlers;

import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import foxahead.simpleworldtimer.ConfigSWT;
import foxahead.simpleworldtimer.gui.GuiSWTOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class KeyHandlerSWT {

  private static Minecraft mc = Minecraft.getMinecraft();
  public static KeyBinding keySWT;

  public KeyHandlerSWT() {
    keySWT = new KeyBinding("options.swt.title", Keyboard.KEY_H, "Simple World Timer");
    ClientRegistry.registerKeyBinding(keySWT);
  }

  public void keyDown(KeyBinding kb) {
    if (kb == keySWT) {
      if (mc.gameSettings.keyBindSneak.isKeyDown()) {
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

  @SubscribeEvent
  public void onKeyInputEvent(KeyInputEvent event) {
    if (keySWT.isPressed()) {
      keyDown(keySWT);
      System.out.println("onKeyInputEvent");
    }

  }
}
