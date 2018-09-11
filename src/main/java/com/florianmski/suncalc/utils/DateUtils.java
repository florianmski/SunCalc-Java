package com.florianmski.suncalc.utils;

import java.util.Calendar;

/**
 * Julian date utilities.
 */
public class DateUtils
{
    /** Number of millis in one day */
    public final static int DAY_MS = 1000 * 60 * 60 * 24;
    /** The Julian day of the POSIX epoch, i.e. Jan 1, 1970 */
    public final static int J1970 = 2440588;
    /** The Julian day of Jan 1, 2000 */
    public final static int J2000 = 2451545;

    /**
     * Converts a datetime into its Julian date
     *
     * @param date datetime with timezone information
     * @return the Julian date
     */
    public static double toJulian(Calendar date)
    {
        // offset to add depending on user timezone
        // I'm not sure about that, it's not used in the JS lib but:
        // - I'm in France and between 00:00 and 01:00 the sunphases were still calculated for the day before
        // - I've tested the app with and without offset, with seems to be more accurate regarding the azimuth
        long offset = date.getTimeZone().getOffset(date.getTimeInMillis());
        return ((double)date.getTimeInMillis() + offset) / DAY_MS - 0.5 + J1970;
    }

    /**
     * Converts a Julian date to a datetime <strong>ASSUMING the timezone of the current JVM</strong>
     *
     * @param j the Julian date
     * @return datetime using the current timezone system's timezone
     */
    public static Calendar fromJulian(double j)
    {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis((long) (((j + 0.5 - J1970) * DAY_MS)));
        return date;
    }

    /**
     * Number of Julian days since Jan 1, 2000. Often used in astronomical calculations
     *
     * @param date the current datetime with timezone info
     * @return number of Julian days
     */
    public static double toDays(Calendar date)
    {
        return toJulian(date) - J2000;
    }
}
