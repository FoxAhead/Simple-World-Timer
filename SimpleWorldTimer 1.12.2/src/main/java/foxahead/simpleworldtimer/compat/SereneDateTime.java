package foxahead.simpleworldtimer.compat;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;

public final class SereneDateTime implements TemporalAccessor {

  private static final ZoneId ZONE_UTC            = ZoneId.of("UTC");
  private static final int    SUBSEASONS_PER_YEAR = 12;
  private static int          daysPerSubseason;
  private static int          ticksPerDay;
  private static int          ticksPerSubseason;
  private static int          ticksPerYear;
  private int                 year;
  private int                 month;
  private int                 day;
  private LocalTime           time;
  static {
    setParams(30, 24000);
  }

  public static void setParams(int daysPerSubseason, int ticksPerDay) {
    SereneDateTime.daysPerSubseason  = daysPerSubseason;
    SereneDateTime.ticksPerDay       = ticksPerDay;
    SereneDateTime.ticksPerSubseason = daysPerSubseason * ticksPerDay;
    SereneDateTime.ticksPerYear      = SUBSEASONS_PER_YEAR * daysPerSubseason * ticksPerDay;
  }

  private SereneDateTime(int year, int month, int day, LocalTime time) {
    this.year  = year;
    this.month = month;
    this.day   = day;
    this.time  = time;
  }

  public static SereneDateTime ofTicks(long ticks) {
    long localTicks = ticks % ticksPerYear;
    long month      = (localTicks / ticksPerSubseason + 2) % SUBSEASONS_PER_YEAR + 1; // Tick 0 = March-01
    localTicks %= ticksPerSubseason;
    long day = localTicks / ticksPerDay + 1;
    localTicks %= ticksPerDay;
    long nanoOfDay = localTicks * 86400000000000L / ticksPerDay; // 24*60*60*1000000000
    return new SereneDateTime(1, (int) month, (int) day, LocalTime.ofNanoOfDay(nanoOfDay));
  }

  public static SereneDateTime of(int year, int month, int day, int hour, int minute, int second) {
    return new SereneDateTime(year, month, day, LocalTime.of(hour, minute, second));
  }

  @Override
  public boolean isSupported(TemporalField field) {
    return true;
  }

  @Override
  public int get(TemporalField field) {
    if (field instanceof ChronoField) {
      return get0(field);
    }
    return 0;
  }

  @Override
  public long getLong(TemporalField field) {
    if (field instanceof ChronoField) {
      ChronoField f = (ChronoField) field;
      return (f.isTimeBased() ? time.getLong(field) : get0(field));
    }
    return field.getFrom(this);
  }

  @SuppressWarnings("incomplete-switch")
  private int get0(TemporalField field) {
    switch ((ChronoField) field) {
      case DAY_OF_WEEK :
        return 1; //getDayOfWeek().getValue();
      case ALIGNED_DAY_OF_WEEK_IN_MONTH :
        return ((day - 1) % 7) + 1;
      case ALIGNED_DAY_OF_WEEK_IN_YEAR :
        return ((getDayOfYear() - 1) % 7) + 1;
      case DAY_OF_MONTH :
        return day;
      case DAY_OF_YEAR :
        return getDayOfYear();
      case EPOCH_DAY :
        throw new UnsupportedTemporalTypeException("Invalid field 'EpochDay' for get() method, use getLong() instead");
      case ALIGNED_WEEK_OF_MONTH :
        return ((day - 1) / 7) + 1;
      case ALIGNED_WEEK_OF_YEAR :
        return ((getDayOfYear() - 1) / 7) + 1;
      case MONTH_OF_YEAR :
        return month;
      case PROLEPTIC_MONTH :
        throw new UnsupportedTemporalTypeException("Invalid field 'ProlepticMonth' for get() method, use getLong() instead");
      case YEAR_OF_ERA :
        return (year >= 1 ? year : 1 - year);
      case YEAR :
        return year;
      case ERA :
        return (year >= 1 ? 1 : 0);
      case INSTANT_SECONDS :
        return 0; //toEpochSecond();
      case OFFSET_SECONDS :
        return 0; //getOffset().getTotalSeconds();
    }
    throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
  }

  public int getDayOfYear() {
    return (month - 1) * daysPerSubseason + day;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <R> R query(TemporalQuery<R> query) {
    if (query == TemporalQueries.zone() || query == TemporalQueries.zoneId()) {
      return (R) ZONE_UTC;
    }
    return null;
  }
}
