package foxahead.simpleworldtimer.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import foxahead.simpleworldtimer.gui.GuiSWTOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class CommandSWT {

  private static Minecraft mc = Minecraft.getInstance();

  public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
    System.out.println("CommandSWT.register");
    dispatcher.register(Commands.literal("swt").requires((p_198673_0_) -> {
      return p_198673_0_.getServer().isSingleplayer() || p_198673_0_.hasPermission(0);
    }).executes(CommandSWT::displayOptionsScreen));
  }

  private static int displayOptionsScreen(final CommandContext<CommandSourceStack> context) {
    mc.setScreen(new GuiSWTOptions());
    return 0;
  }
}
