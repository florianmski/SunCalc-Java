package com.florianmski.suncalc.utils;

public class TimeUtils
{
    private final static double J0 = 0.0009;

    public static double getJulianCycle(double d, double lw)
    {
        return Math.round(d - J0 - lw / (2 * Math.PI));
    }

    public static double getApproxTransit(double Ht, double lw, double n)
    {
        return J0 + (Ht + lw) / (2 * Math.PI) + n;
    }

    public static double getSolarTransitJ(double ds, double M, double L)
    {
        return DateUtils.J2000 + ds + 0.0053 * Math.sin(M) - 0.0069 * Math.sin(2 * L);
    }

    public static double getHourAngle(double h, double phi, double d)
    {
        return Math.acos((Math.sin(h) - Math.sin(phi) * Math.sin(d)) / (Math.cos(phi) * Math.cos(d)));
    }
}
