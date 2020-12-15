package foxahead.simpleworldtimer.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import foxahead.simpleworldtimer.gui.GuiSWTOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class CommandSWT {

  private static Minecraft mc = Minecraft.getInstance();

  public static void register(CommandDispatcher<CommandSource> dispatcher) {
    //System.out.println("CommandSWT.register");
    dispatcher.register(Commands.literal("swt").requires((commandSource) -> {
      return commandSource.getServer().isSinglePlayer() || commandSource.hasPermissionLevel(0);
    }).executes(CommandSWT::displayOptionsScreen));
  }

  private static int displayOptionsScreen(final CommandContext<CommandSource> context) {
    mc.displayGuiScreen(new GuiSWTOptions());
    return 0;
  }
}