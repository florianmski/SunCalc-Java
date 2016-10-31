package com.florianmski.suncalc.utils;

import java.util.Calendar;

public class DateUtils
{
    public final static int DAY_MS = 1000 * 60 * 60 * 24;
    public final static int J1970 = 2440588;
    public final static int J2000 = 2451545;

    public static double toJulian(Calendar date)
    {
        // offset to add depending on user timezone
        // I'm not sure about that, it's not used in the JS lib but:
        // - I'm in France and between 00:00 and 01:00 the sunphases were still calculated for the day before
        // - I've tested the app with and without offset, with seems to be more accurate regarding the azimuth
        long offset = date.getTimeZone().getOffset(date.getTimeInMillis());
        return ((double)date.getTimeInMillis() + offset) / DAY_MS - 0.5 + J1970;
    }

    public static Calendar fromJulian(double j)
    {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis((long) (((j + 0.5 - J1970) * DAY_MS)));
        return date;
    }

    public static double toDays(Calendar date)
    {
        return toJulian(date) - J2000;
    }
}
