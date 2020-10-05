package foxahead.simpleworldtimer;

import static net.minecraftforge.common.config.Configuration.NEW_LINE;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;

public class ConfigSWT {

  public static final Configuration CONFIGURATION          = new Configuration(new File(Loader.instance().getConfigDir(),
                                                                                        "SimpleWorldTimer.cfg"));
  public static final int           PRESET_TOTAL_WORLD     = 0;
  public static final int           PRESET_MINECRAFT       = 1;
  public static final int           PRESET_STOPWATCH       = 2;
  public static final int           PRESET_SYSTEM          = 3;
  public static final int           PRESET_CUSTOM          = 4;
  public static final int           CLOCK_TYPE_TOTAL_WORLD = 0;
  public static final int           CLOCK_TYPE_MINECRAFT   = 1;
  public static final int           CLOCK_TYPE_STOPWATCH   = 2;
  public static final int           CLOCK_TYPE_SYSTEM      = 3;
  private static Property           enable;
  private static Property           autoHide;
  private static Property           xPosition;
  private static Property           yPosition;
  private static Property           preset;
  private static Property           clockType;
  private static Property           startYear;
  private static Property           pattern1;
  private static Property           pattern2;
  private static Property           stopWatchStart;
  private static Property           stopWatchStop;
  public static String              patterns[][]           = {{"'[&dd ]'HH:mm:ss", ""},                                  // Total world 
                                                              {"dd.MM.yyyy", "'Day &d' HH:mm"},                          // Minecraft
                                                              {"'[&dd ]'HH:mm:ss.SSS", "'Ticks &w'"},                    // Stopwatch
                                                              {"dd.MM.yyyy", "HH:mm:ss"}};                               // System

  public static List<String> getPresetList() {
    return Arrays.asList(I18n.format("options.swt.preset0"),
                         I18n.format("options.swt.preset1"),
                         I18n.format("options.swt.preset2"),
                         I18n.format("options.swt.preset3"),
                         I18n.format("options.swt.preset4"));
  }

  public static List<String> getClockTypeList() {
    return Arrays.asList(I18n.format("options.swt.clockType0"),
                         I18n.format("options.swt.clockType1"),
                         I18n.format("options.swt.clockType2"),
                         I18n.format("options.swt.clockType3"));
  }

  public static boolean getEnable() {
    return enable.getBoolean(true);
  }

  public static void setEnable(boolean enable) {
    ConfigSWT.enable.set(enable);
  }

  public static boolean getAutoHide() {
    return autoHide.getBoolean(true);
  }

  public static void setAutoHide(boolean autoHide) {
    ConfigSWT.autoHide.set(autoHide);
  }

  public static int getxPosition() {
    return xPosition.getInt(0);
  }

  public static void setxPosition(int xPosition) {
    ConfigSWT.xPosition.set(MathHelper.clamp_int(xPosition, 0, 100));
  }

  public static int getyPosition() {
    return yPosition.getInt(0);
  }

  public static void setyPosition(int yPosition) {
    ConfigSWT.yPosition.set(MathHelper.clamp_int(yPosition, 0, 100));
  }

  public static int getPreset() {
    return preset.getInt(0);
  }

  public static void setPreset(int preset) {
    ConfigSWT.preset.set(MathHelper.clamp_int(preset, 0, getPresetList().size() - 1));
  }

  public static int getClockType() {
    if (getPreset() == PRESET_CUSTOM) {
      return clockType.getInt(0);
    } else {
      return getPreset();
    }
  }

  public static void setClockType(int clockType) {
    if (getPreset() == PRESET_CUSTOM) {
      ConfigSWT.clockType.set(MathHelper.clamp_int(clockType, 0, getClockTypeList().size() - 1));
    }
  }

  public static int getStartYear() {
    return startYear.getInt(0);
  }

  public static void setStartYear(int startYear) {
    ConfigSWT.startYear.set(startYear);
  }

  public static String getPattern1() {
    if (getPreset() == PRESET_CUSTOM) {
      return pattern1.getString();
    } else {
      return patterns[getPreset()][0];
    }
  }

  public static void setPattern1(String pattern1) {
    if (getPreset() == PRESET_CUSTOM) {
      ConfigSWT.pattern1.set(pattern1.trim());
    }
  }

  public static String getPattern2() {
    if (getPreset() == PRESET_CUSTOM) {
      return pattern2.getString();
    } else {
      return patterns[getPreset()][1];
    }
  }

  public static void setPattern2(String pattern2) {
    if (getPreset() == PRESET_CUSTOM) {
      ConfigSWT.pattern2.set(pattern2.trim());
    }
  }

  public static long getStopWatchStart() {
    try {
      return Long.parseLong(stopWatchStart.getString());
    } catch (Exception e) {
      return 0;
    }
  }

  public static void setStopWatchStart(long stopWatchStart) {
    ConfigSWT.stopWatchStart.set(Long.toString(stopWatchStart));
  }

  public static long getStopWatchStop() {
    try {
      return Long.parseLong(stopWatchStop.getString());
    } catch (Exception e) {
      return 0;
    }
  }

  public static void setStopWatchStop(long stopWatchStop) {
    ConfigSWT.stopWatchStop.set(Long.toString(stopWatchStop));
  }

  static {
    CONFIGURATION.load();
    CONFIGURATION.save();
  }

  public static void loadConfig() {
    try {
      System.out.println();
      CONFIGURATION.load();
      enable    = CONFIGURATION.get("general", "enable", true, "Enable Simple World Timer");
      autoHide  = CONFIGURATION.get("general",
                                    "autoHide",
                                    true,
                                    "Auto hide timer on various screens like inventory, chat, debug, no-GUI mode (F1), main menu etc.");
      xPosition = CONFIGURATION.get("general", "xPosition", 0, "Relative horizontal position in %. From 0% to 100%.");
      setxPosition(getxPosition());
      yPosition = CONFIGURATION.get("general", "yPosition", 10, "Relative vertical position in %. From 0% to 100%.");
      setyPosition(getyPosition());
      preset         = CONFIGURATION.get("general",
                                         "preset",
                                         PRESET_TOTAL_WORLD,
                                         "Timer preset:" + NEW_LINE + "0 - Total World Time" + NEW_LINE
                                           + "1 - Minecraft Time" + NEW_LINE + "2 - Stopwatch" + NEW_LINE
                                           + "3 - System Time" + NEW_LINE + "4 - Custom configuration");
      clockType      = CONFIGURATION.get("general",
                                         "clockType",
                                         CLOCK_TYPE_TOTAL_WORLD,
                                         "Source of data for timer:" + NEW_LINE
                                           + "0 - Total time of world being ticked provided by getTotalWorldTime() function"
                                           + NEW_LINE
                                           + "1 - in-game time provided by getWorldTime() function. Used for day/night cycle."
                                           + NEW_LINE
                                           + "    Can be changed by 'time set' command. 0 ticks equals 6:00 AM. 24000 ticks for one Minecraft day."
                                           + NEW_LINE + "2 - Manual start/stop" + NEW_LINE + "3 - System clock;"
                                           + NEW_LINE);
      startYear      = CONFIGURATION.get("general",
                                         "startYear",
                                         1,
                                         "Starting year for Minecraft time. To make sense of its calendar representation.");
      pattern1       = CONFIGURATION.get("general",
                                         "pattern1",
                                         "",
                                         "Custom pattern for line 1" + NEW_LINE
                                           + "Pattern letters of standard java.text.SimpleDateFormat class used."
                                           + NEW_LINE + "Additional syntax take effect after:" + NEW_LINE
                                           + "&d - total days" + NEW_LINE + "&w - total ticks" + NEW_LINE
                                           + "&t - current two-sign ticks in current second (00-19)" + NEW_LINE
                                           + "If you additionally put it in square brackets then everything within will output only if included values are non-zero"
                                           + NEW_LINE
                                           + "Those should be quoted using single quotes (') to avoid initial interpretation by SimpleDateFormat"
                                           + NEW_LINE);
      pattern2       = CONFIGURATION.get("general", "pattern2", "", "Custom pattern for line 2");
      stopWatchStart = CONFIGURATION.get("general", "stopWatchStart", "0", "When was Stopwatch started");
      stopWatchStop  = CONFIGURATION.get("general", "stopWatchStop", "0", "When was Stopwatch stopped");
    } finally {
      syncConfig();
    }
  }

  public static void syncConfig() {
    if (CONFIGURATION.hasChanged()) {
      CONFIGURATION.save();
    }
  }
}
