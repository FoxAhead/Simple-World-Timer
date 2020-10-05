package foxahead.simpleworldtimer.command;

import foxahead.simpleworldtimer.gui.GuiSWTOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandSWT extends CommandBase {

  private static Minecraft mc = Minecraft.getMinecraft();
  /*public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender) {
    return MinecraftServer.getServer().isSinglePlayer() || super.canCommandSenderUseCommand(par1ICommandSender);
  }*/

  @Override
  public int getRequiredPermissionLevel() {
    return 0;
  }

  @Override
  public String getName() {
    return "swt";
  }

  @Override
  public String getUsage(ICommandSender sender) {
    return "commands.swt.usage";
  }

  @Override
  public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
    mc.displayGuiScreen(new GuiSWTOptions());
  }
}
