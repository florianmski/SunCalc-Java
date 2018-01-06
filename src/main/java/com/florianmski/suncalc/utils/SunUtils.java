package com.florianmski.suncalc.utils;

import com.florianmski.suncalc.models.EquatorialCoordinates;

/**
 * Calculations for the Sun.
 */
public class SunUtils
{

    /** Perihelion of the Earth, measured in <strong>degrees</strong> (table 3) */
    private static final double PERIHELION = 102.9372;

    /** Kepler's coefficient for earth orbit, 1st order in <strong>degrees</strong> (table 2) */
    private static final double C1 = 1.9148;
    /** Kepler's coefficient for earth orbit, 2nd order in <strong>degrees</strong> (table 2) */
    private static final double C2 = 0.02;
    /** Kepler's coefficient for earth orbit, 3rd order in <strong>degrees</strong> (table 2) */
    private static final double C3 = 0.0003;

    /** Earth mean anomaly constant, in <strong>degrees</strong> (table 1) */
    private static final double M0 = 357.5291;
    /** Earth mean anomaly coefficient, in <strong>degrees/julian days</strong> (table 1) */
    private static final double M1 = 0.98560028;

    /**
     *
     * The solar mean anomaly (eq. 1)
     *
     * @param d the number of Julian days since Jan 1, 2000
     * @return anomaly, in radians
     */
    public static double getSolarMeanAnomaly(double d)
    {
        return Constants.TO_RAD * (M0 + M1 * d);
    }

    /**
     * Equation of the Center (eq. 4), used to correct the mean anomaly
     *
     * @param M the mean anomaly, in radians
     * @return equation of center, in radians, to three orders
     */
    public static double getEquationOfCenter(double M)
    {
        return Constants.TO_RAD * (C1 * Math.sin(M) + C2 * Math.sin(2 * M) + C3 * Math.sin(3 * M));
    }

    /**
     * Ecliptic longitude of the Sun (eq. 8), as seen from another planet
     *
     * @param M the mean anomaly, in radians
     * @param C equation of center, in radians
     * @return in radians
     */
    public static double getEclipticLongitude(double M, double C)
    {
        double P = Constants.TO_RAD * PERIHELION;
        return M + C + P + Math.PI;
    }

    /**
     * Helpful g for retuning the position of the sun for a given day
     *
     * @param d Julian days since Jan 1, 2000
     * @return equatorial coordinates of the sun
     */
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
