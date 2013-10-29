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
        long offset = date.get(Calendar.ZONE_OFFSET) + date.get(Calendar.DST_OFFSET);
        return (((float)date.getTimeInMillis() + offset) / DAY_MS) - 0.5 + J1970;
    }

    public static Calendar fromJulian(double j)
    {
        Calendar d = Calendar.getInstance();
        d.setTimeInMillis((long) ((j + 0.5 - J1970) * DAY_MS));
        return d;
    }

    public static double toDays(Calendar date)
    {
        return toJulian(date) - J2000;
    }
}
