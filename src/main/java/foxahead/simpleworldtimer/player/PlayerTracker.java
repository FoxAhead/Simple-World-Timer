package foxahead.simpleworldtimer.player;

import cpw.mods.fml.common.IPlayerTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerTracker implements IPlayerTracker {

  @Override
  public void onPlayerLogin(EntityPlayer player) {
    NBTTagCompound nbt = player.getEntityData();
    if (nbt.hasKey("SimpleWorldTimer")) {
      nbt.setCompoundTag("SimpleWorldTimer", new NBTTagCompound());
    }
    //ConfigSWT.stopWatchStartTicks = nbt.getCompoundTag("SimpleWorldTimer").getLong("stopWatchStartTicks");
  }

  @Override
  public void onPlayerLogout(EntityPlayer player) {
    saveEntityPlayer(player);
  }

  @Override
  public void onPlayerChangedDimension(EntityPlayer player) {
    saveEntityPlayer(player);
  }

  @Override
  public void onPlayerRespawn(EntityPlayer player) {
  }

  private void saveEntityPlayer(EntityPlayer player) {
    NBTTagCompound nbt = new NBTTagCompound();
    //nbt.setLong("stopWatchStartTicks", ConfigSWT.stopWatchStartTicks);
    player.getEntityData().setCompoundTag("SimpleWorldTimer", nbt);
  }
}
