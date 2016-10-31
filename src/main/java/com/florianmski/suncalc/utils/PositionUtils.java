package com.florianmski.suncalc.utils;

public class PositionUtils
{
    public static double getRightAscension(double l, double b)
    {
        return Math.atan2(Math.sin(l) * Math.cos(Constants.E) - Math.tan(b) * Math.sin(Constants.E), Math.cos(l));
    }

    public static double getDeclination(double l, double b)
    {
        return Math.asin(Math.sin(b) * Math.cos(Constants.E) + Math.cos(b) * Math.sin(Constants.E) * Math.sin(l));
    }

    public static double getAzimuth(double H, double phi, double dec)
    {
        return Math.PI + Math.atan2(Math.sin(H), Math.cos(H) * Math.sin(phi) - Math.tan(dec) * Math.cos(phi));
//        return Math.atan2(Math.sin(H), Math.cos(H) * Math.sin(phi) - Math.tan(dec) * Math.cos(phi));
    }

    public static double getAltitude(double H, double phi, double dec)
    {
        return Math.asin(Math.sin(phi) * Math.sin(dec) + Math.cos(phi) * Math.cos(dec) * Math.cos(H));
    }

    public static double getSiderealTime(double d, double lw)
    {
        return Constants.RAD * (280.16 + 360.9856235 * d) - lw;
    }
}
