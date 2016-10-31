package com.florianmski.suncalc.utils;

import com.florianmski.suncalc.models.EquatorialCoordinates;

public class SunUtils
{
    public static double getSolarMeanAnomaly(double d)
    {
        return Constants.RAD * (357.5291 + 0.98560028 * d);
    }

    public static double getEquationOfCenter(double M)
    {
        return Constants.RAD * (1.9148 * Math.sin(M) + 0.02 * Math.sin(2 * M) + 0.0003 * Math.sin(3 * M));
    }

    public static double getEclipticLongitude(double M, double C)
    {
        double P = Constants.RAD * 102.9372; // perihelion of the Earth
        return M + C + P + Math.PI;
    }

    public static EquatorialCoordinates getSunCoords(double d)
    {
        double M = getSolarMeanAnomaly(d);
        double C = getEquationOfCenter(M);
        double L = getEclipticLongitude(M, C);

        return new EquatorialCoordinates(
                PositionUtils.getRightAscension(L, 0),
                PositionUtils.getDeclination(L, 0));
    }
}
