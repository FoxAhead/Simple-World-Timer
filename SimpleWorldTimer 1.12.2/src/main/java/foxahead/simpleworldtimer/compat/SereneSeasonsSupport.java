package foxahead.simpleworldtimer.compat;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.SeasonHelper;

public class SereneSeasonsSupport {

  private static final boolean  isModLoaded     = Loader.isModLoaded("sereneseasons");;
  private static boolean        isInitialized   = false;
  private static ISeasonState   seasonState;
  private static SereneDateTime fdate;
  public static int             ticks           = 0;
  public static int             day             = 0;
  public static int             dayOfYear       = 0;
  public static String          subSeasonName   = "";
  public static String          seasonName      = "";
  public static List<String>    subSeasonsNames = Arrays.asList("sereneseason.swt.earlyspring",
                                                                "sereneseason.swt.midspring",
                                                                "sereneseason.swt.latespring",
                                                                "sereneseason.swt.earlysummer",
                                                                "sereneseason.swt.midsummer",
                                                                "sereneseason.swt.latesummer",
                                                                "sereneseason.swt.earlyautumn",
                                                                "sereneseason.swt.midautumn",
                                                                "sereneseason.swt.lateautumn",
                                                                "sereneseason.swt.earlywinter",
                                                                "sereneseason.swt.midwinter",
                                                                "sereneseason.swt.latewinter");
  public static List<String>    seasonsNames    = Arrays.asList("sereneseason.swt.spring",
                                                                "sereneseason.swt.summer",
                                                                "sereneseason.swt.autumn",
                                                                "sereneseason.swt.winter");

  private static void Initialize() {
    if (!isInitialized) {
      if (seasonState == null)
        return;
      int tickPerDay       = seasonState.getDayDuration();
      int daysPerSubseason = seasonState.getSubSeasonDuration() / tickPerDay;
      SereneDateTime.setParams(daysPerSubseason, tickPerDay);
      isInitialized = true;
    }
  }

  public static void fetchState(World world) {
    if (!isModLoaded)
      return;
    if (world == null)
      return;
    seasonState = SeasonHelper.getSeasonState(world);
    Initialize();
    ticks = seasonState.getSeasonCycleTicks();
    day   = seasonState.getDay();
    try {
      subSeasonName = I18n.format(subSeasonsNames.get(seasonState.getSubSeason().ordinal()));
      seasonName    = I18n.format(seasonsNames.get(seasonState.getSeason().ordinal()));
    } catch (IndexOutOfBoundsException e) {
      subSeasonName = "";
      seasonName    = "";
    }
    fdate     = SereneDateTime.ofTicks(ticks);
    dayOfYear = fdate.getDayOfYear();
  }

  public static String preFormatOutText(String pattern, String language) {
    if (seasonState == null)
      return "";
    if (fdate == null)
      return "";
    DateTimeFormatter dtf;
    try {
      dtf = DateTimeFormatter.ofPattern(pattern, new Locale(language.substring(0, 2), language.substring(3, 5)));
    } catch (Exception e) {
      dtf = DateTimeFormatter.ofPattern(pattern);
    }
    return dtf.format(fdate);
  }
}
