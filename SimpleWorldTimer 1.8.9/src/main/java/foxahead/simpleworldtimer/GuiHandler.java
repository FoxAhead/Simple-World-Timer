package foxahead.simpleworldtimer;

import net.minecraftforge.fml.common.network.IGuiHandler;
import foxahead.simpleworldtimer.gui.GuiSWTOptions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case 0:
			return new GuiSWTOptions();
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case 0:
			return new GuiSWTOptions();
		default:
			return null;
		}
	}

}
