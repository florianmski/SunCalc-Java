package com.florianmski.suncalc.utils;

import com.florianmski.suncalc.models.GeocentricCoordinates;

public class MoonUtils
{
    public static GeocentricCoordinates getMoonCoords(double d)
    {
        // geocentric ecliptic coordinates of the moon

        double L = Constants.RAD * (218.316 + 13.176396 * d);   // ecliptic longitude
        double M = Constants.RAD * (134.963 + 13.064993 * d);   // mean anomaly
        double F = Constants.RAD * (93.272 + 13.229350 * d);    // mean distance

        double l  = L + Constants.RAD * 6.289 * Math.sin(M);    // longitude
        double b  = Constants.RAD * 5.128 * Math.sin(F);        // latitude
        double dt = 385001 - 20905 * Math.cos(M);               // distance to the moon in km

        return new GeocentricCoordinates(
                PositionUtils.getRightAscension(l, b),
                PositionUtils.getDeclination(l, b),
                dt);
    }
}
