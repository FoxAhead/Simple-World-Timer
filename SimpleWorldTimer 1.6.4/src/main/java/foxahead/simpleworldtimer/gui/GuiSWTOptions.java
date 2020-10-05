package foxahead.simpleworldtimer.gui;

import org.lwjgl.input.Keyboard;

import foxahead.simpleworldtimer.ConfigSWT;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

public class GuiSWTOptions extends GuiScreen implements ISWTSliderObserver {

  private static final int  COLOR1 = 0xFFFFFFFF;
  private static final int  COLOR2 = 0xFFE0E0E0;
  private GuiSWTSmallButton smallButtonPreset;
  private GuiButton         buttonCustom;
  private GuiSWTSmallButton smallButtonClockType;
  private GuiButton         buttonStart;
  private GuiButton         buttonPause;
  private GuiButton         buttonStop;
  private GuiTextField      startDateTextField;
  private GuiTextField      patternTextField1;
  private GuiTextField      patternTextField2;
  private GuiButton         buttonSwap;
  private boolean           initialized = false;
  private int               xCoord = 0;
  private int               yCoord = 0;
  private int               yStep  = 24;
  private int               focus  = -1;

  @Override
  public void updateScreen() {
    if (!initialized)
      return;
    this.startDateTextField.updateCursorCounter();
    this.patternTextField1.updateCursorCounter();
    this.patternTextField2.updateCursorCounter();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void initGui() {
    Keyboard.enableRepeatEvents(true);
    xCoord = this.width / 2 - 100;
    yCoord = (this.height - yStep * 9) / 2;
    this.buttonList.clear();
    this.buttonList.add(new GuiSWTCheckbox(11,
                                           xCoord,
                                           yCoord + yStep,
                                           100,
                                           20,
                                           I18n.getString("options.swt.enable"),
                                           ConfigSWT.getEnable()));
    this.buttonList.add(new GuiSWTCheckbox(12,
                                           xCoord + 100,
                                           yCoord + yStep,
                                           100,
                                           20,
                                           I18n.getString("options.swt.autoHide"),
                                           ConfigSWT.getAutoHide()));
    this.buttonList.add(new GuiSWTSlider(2, xCoord, yCoord + yStep * 2, "X: ", ConfigSWT.getxPosition(), this));
    this.buttonList.add(new GuiSWTSlider(3, xCoord, yCoord + yStep * 3, "Y: ", ConfigSWT.getyPosition(), this));
    this.buttonList.add(smallButtonPreset = new GuiSWTSmallButton(21,
                                                                  xCoord,
                                                                  yCoord + yStep * 4,
                                                                  176,
                                                                  20,
                                                                  I18n.getString("options.swt.preset"),
                                                                  ConfigSWT.getPresetList(),
                                                                  ConfigSWT.getPreset()));
    this.buttonList.add(buttonCustom = new GuiButton(4, xCoord + 180, yCoord + yStep * 4, 20, 20, "C"));
    this.buttonList.add(smallButtonClockType = new GuiSWTSmallButton(22,
                                                                     xCoord,
                                                                     yCoord + yStep * 5,
                                                                     75,
                                                                     20,
                                                                     "", // I18n.getString("options.swt.clockType"),
                                                                     ConfigSWT.getClockTypeList(),
                                                                     ConfigSWT.getClockType()));
    this.startDateTextField = new GuiTextField(this.fontRenderer, xCoord + 150, yCoord + yStep * 5, 50, 20);
    this.patternTextField1  = new GuiTextField(this.fontRenderer, xCoord, yCoord + yStep * 6, 176, 20);
    this.patternTextField2  = new GuiTextField(this.fontRenderer, xCoord, yCoord + yStep * 7, 176, 20);
    this.buttonList.add(buttonSwap = new GuiButton(5, xCoord + 180, (int) (yCoord + yStep * 6.5F), 20, 20, ")"));
    this.buttonList.add(new GuiButton(0, xCoord, yCoord + yStep * 8, I18n.getString("gui.done")));
    this.buttonList.add(buttonStart = new GuiButton(6, xCoord + 102, (int) (yCoord + yStep * 5), 30, 20, ">"));
    this.buttonList.add(buttonPause = new GuiButton(7, xCoord + 136, (int) (yCoord + yStep * 5), 30, 20, "||"));
    this.buttonList.add(buttonStop = new GuiButton(8, xCoord + 170, (int) (yCoord + yStep * 5), 30, 20, "[]"));
    updateButtons();
    initialized = true;
  }

  @Override
  public void onGuiClosed() {
    Keyboard.enableRepeatEvents(false);
  }

  @Override
  public void actionPerformed(GuiButton parGuiButton) {
    String pattern1 = "";
    String pattern2 = "";
    if (parGuiButton.id == 0) {
      this.closeMe();
    }
    if (parGuiButton.id == 4) {
      pattern1 = ConfigSWT.getPattern1();
      pattern2 = ConfigSWT.getPattern2();
      int preset = ConfigSWT.getPreset();
      ConfigSWT.setPreset(ConfigSWT.PRESET_CUSTOM);
      ConfigSWT.setClockType(preset);
      ConfigSWT.setPattern1(pattern1);
      ConfigSWT.setPattern2(pattern2);
    }
    if (parGuiButton.id == 5) {
      pattern1 = ConfigSWT.getPattern1();
      ConfigSWT.setPattern1(ConfigSWT.getPattern2());
      ConfigSWT.setPattern2(pattern1);
    }
    if (parGuiButton.id >= 6 || parGuiButton.id <= 8) {
      long start = ConfigSWT.getStopWatchStart();
      long stop  = ConfigSWT.getStopWatchStop();
      long now   = this.mc.theWorld.getTotalWorldTime();
      switch (parGuiButton.id) {
        case 6 :
          if (start <= stop) {
            ConfigSWT.setStopWatchStart(start + now - stop);
            ConfigSWT.setStopWatchStop(0);
          }
          break;
        case 7 :
          if (stop == 0) {
            ConfigSWT.setStopWatchStop(now);
          }
          break;
        case 8 :
          ConfigSWT.setStopWatchStart(now);
          ConfigSWT.setStopWatchStop(now);
          break;
      }
    }
    if (parGuiButton.id == 11) {
      ConfigSWT.setEnable(((GuiSWTCheckbox) parGuiButton).State);
    }
    if (parGuiButton.id == 12) {
      ConfigSWT.setAutoHide(((GuiSWTCheckbox) parGuiButton).State);
    }
    if (parGuiButton.id == 21) {
      ConfigSWT.setPreset(((GuiSWTSmallButton) parGuiButton).getIndex());
    }
    if (parGuiButton.id == 22) {
      ConfigSWT.setClockType(((GuiSWTSmallButton) parGuiButton).getIndex());
    }
    updateButtons();
  }

  @Override
  protected void keyTyped(char par1, int par2) {
    this.startDateTextField.textboxKeyTyped(par1, par2);
    this.patternTextField1.textboxKeyTyped(par1, par2);
    this.patternTextField2.textboxKeyTyped(par1, par2);
    try {
      ConfigSWT.setStartYear(Integer.parseInt(startDateTextField.getText()));
    } catch (Exception e) {
      ConfigSWT.setStartYear(0);
    }
    ConfigSWT.setPattern1(patternTextField1.getText());
    ConfigSWT.setPattern2(patternTextField2.getText());
    if (par2 == Keyboard.KEY_TAB) {
      if (startDateTextField.isFocused()) {
        focus = 0;
      } else if (patternTextField1.isFocused()) {
        focus = 1;
      } else if (patternTextField2.isFocused()) {
        focus = 2;
      }
      focus = (focus + 1) % 3;
      startDateTextField.setFocused(focus == 0);
      patternTextField1.setFocused(focus == 1);
      patternTextField2.setFocused(focus == 2);
    }
    if (par2 == Keyboard.KEY_ESCAPE) {
      this.closeMe();
    }
  }

  @Override
  protected void mouseClicked(int par1, int par2, int par3) {
    super.mouseClicked(par1, par2, par3);
    this.startDateTextField.mouseClicked(par1, par2, par3);
    this.patternTextField1.mouseClicked(par1, par2, par3);
    this.patternTextField2.mouseClicked(par1, par2, par3);
  }

  @Override
  public void sliderValueChanged(GuiSWTSlider slider) {
    if (slider.id == 2) {
      ConfigSWT.setxPosition(slider.sliderValue);
    }
    if (slider.id == 3) {
      ConfigSWT.setyPosition(slider.sliderValue);
    }
  }

  @Override
  public void drawScreen(int par1, int par2, float par3) {
    if (!initialized)
      return;
    this.drawDefaultBackground();
    this.drawCenteredString(this.fontRenderer, I18n.getString("options.swt.title"), this.width / 2, yCoord, COLOR1);
    if (smallButtonClockType.getIndex() == ConfigSWT.CLOCK_TYPE_MINECRAFT) {
      this.drawCenteredString(this.fontRenderer,
                              I18n.getString("options.swt.startYear"),
                              xCoord + 120,
                              yCoord + yStep * 5 + 6,
                              COLOR2);
    }
    this.startDateTextField.drawTextBox();
    this.patternTextField1.drawTextBox();
    this.patternTextField2.drawTextBox();
    super.drawScreen(par1, par2, par3);
  }

  public void closeMe() {
    ConfigSWT.syncConfig();
    this.mc.displayGuiScreen((GuiScreen) null);
  }

  public boolean isTyping() {
    return (patternTextField1.isFocused() || patternTextField2.isFocused());
  }

  private void updateButtons() {
    smallButtonPreset.setIndex(ConfigSWT.getPreset());
    smallButtonClockType.setIndex(ConfigSWT.getClockType());
    switch (smallButtonPreset.getIndex()) {
      case ConfigSWT.PRESET_CUSTOM :
        smallButtonClockType.enabled = true;
        patternTextField1.setEnabled(true);
        patternTextField2.setEnabled(true);
        smallButtonClockType.setIndex(ConfigSWT.getClockType());
        buttonCustom.enabled = false;
        buttonSwap.enabled = true;
        break;
      default :
        smallButtonClockType.enabled = false;
        patternTextField1.setEnabled(false);
        patternTextField2.setEnabled(false);
        smallButtonClockType.setIndex(smallButtonPreset.getIndex());
        buttonCustom.enabled = true;
        buttonSwap.enabled = false;
        break;
    }
    switch (smallButtonClockType.getIndex()) {
      case ConfigSWT.CLOCK_TYPE_MINECRAFT :
        startDateTextField.setEnabled(true);
        startDateTextField.setVisible(true);
        break;
      default :
        startDateTextField.setEnabled(false);
        startDateTextField.setVisible(false);
        break;
    }
    buttonStart.drawButton = buttonPause.drawButton = buttonStop.drawButton = smallButtonClockType.getIndex() == ConfigSWT.CLOCK_TYPE_STOPWATCH;
    this.startDateTextField.setText(String.valueOf(ConfigSWT.getStartYear()));
    this.patternTextField1.setText(ConfigSWT.getPattern1());
    this.patternTextField2.setText(ConfigSWT.getPattern2());
  }

  public boolean doesGuiPauseGame() {
    return true;
  }
}
