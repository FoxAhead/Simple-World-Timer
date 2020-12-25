package foxahead.simpleworldtimer.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiSWTSmallButton extends GuiButton {

  private int          index;
  private String       text = "";
  private int          listSize;
  private List<String> variants;

  public GuiSWTSmallButton(int id, int x, int y, String text, List<String> variants, int index) {
    this(id, x, y, 200, 20, text, variants, index);
  }

  public GuiSWTSmallButton(int id, int x, int y, int width, int height, String text, List<String> variants, int index) {
    super(id, x, y, width, height, text);
    this.variants = variants;
    this.text     = text;
    this.listSize = variants.size();
    this.setIndex(index);
  }

  @Override
  public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
    if (super.mousePressed(par1Minecraft, par2, par3)) {
      index = (index + 1) % listSize;
      setDisplayString();
      return true;
    } else {
      return false;
    }
  }

  private void setDisplayString() {
    try {
      this.displayString = (String) variants.get(index);
      if (!this.text.isEmpty()) {
        this.displayString = this.text + ": " + this.displayString;
      }
    } catch (Exception e) {
    }
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
    setDisplayString();
  }

  public void setWidth(int width) {
    this.width = width;
  }
}
