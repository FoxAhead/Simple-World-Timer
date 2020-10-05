package foxahead.simpleworldtimer.gui;

import java.util.List;

import net.minecraft.client.gui.widget.button.Button;

public class GuiSWTSmallButton extends Button {

  private int          index;
  private String       text = "";
  private int          listSize;
  private List<String> variants;

  public GuiSWTSmallButton(int x, int y, String text, List<String> variants, int index, Button.IPressable onPress) {
    this(x, y, 200, 20, text, variants, index, onPress);
  }

  public GuiSWTSmallButton(int x, int y, int width, int height, String text, List<String> variants, int index,
    Button.IPressable onPress) {
    super(x, y, width, height, text, onPress);
    this.variants = variants;
    this.text     = text;
    this.listSize = variants.size();
    this.setIndex(index);
  }

  @Override
  public void onPress() {
    index = (index + 1) % listSize;
    setDisplayString();
    super.onPress();
  }

  private void setDisplayString() {
    String displayString;
    displayString = (String) variants.get(index);
    if (!this.text.isEmpty()) {
      displayString = this.text + ": " + displayString;
    }
    this.setMessage(displayString);
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
