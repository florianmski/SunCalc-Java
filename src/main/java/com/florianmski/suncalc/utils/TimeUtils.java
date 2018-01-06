package com.florianmski.suncalc.utils;

/**
 * Time calculations for solar transit.
 */
public class TimeUtils
{
    /**
     * Provides the date and time of a transit of the Sun (eq. 32)
     */
    private final static double J0 = 0.0009;

    /**
     * Describes how much the time of transit can vary because of the eccentricity of the Earth's orbit (eq. 32)
     */
    private static final double J1 = 0.0053;

    /**
     * Calculates the Julian cycle (eq. 33) for a given day
     *
     * <p>
     *  This is not exactly equal to the days since Jan 1, 2000 since,
     *  depending on longitude, it may be a different number. See
     *  <a href="http://users.electromagnetic.net/bu/astro/sunrise-set.php">
     *  http://users.electromagnetic.net/bu/astro/sunrise-set.php</a>
     * </p>
     *
     * @param d the day to calculate for, number Julian days since Jan 1, 2000
     * @param lw West longitude, in radians
     * @return the rounded julian cycle
     */
    public static double getJulianCycle(double d, double lw)
    {
        return Math.round(d - J0 - lw / (2 * Math.PI));
    }

    /**
     * First order estimate for approximate solar transit (eq. 34)
     *
     * @param Ht
     * @param lw West longitude, in radians
     * @param n the julian cycle, see {@link #getJulianCycle(double, double)}
     * @return reasonable estimate for the date and time of the transit
     */
    public static double getApproxTransit(double Ht, double lw, double n)
    {
        return J0 + (Ht + lw) / (2 * Math.PI) + n;
    }

    /**
     * Better estimate for solar transit, with eccentricity and obliquity corrections (eq. 35)
     *
     * @param ds approximate first order solar transit, in Julian days
     * @param M Earth's mean anomaly, in radians (eq. 3)
     * @param L ecliptic longitude of the Sun, in radians (eq. 8)
     * @return solar transit, in Julian days
     */
    public static double getSolarTransitJ(double ds, double M, double L)
    {
        return DateUtils.J2000 + ds + J1 * Math.sin(M) - 0.0069 * Math.sin(2 * L);
    }

    /**
     * The hour angle (eq. 24). Indicates how long ago the celestial body has passed through the celestial meridian
     *
     * @param h altitude above the horizon, measured in radians. The altitude is zero at the horizon, PI/2 at the zenith
     *          (straight above your head) and -PI/2 at nadir (straight down)
     * @param phi latitude from the North (beginning of section 7)
     * @param d declination from Earth, measured in radians
     * @return hour angle, in radians
     */
    public static double getHourAngle(double h, double phi, double d)
    {
        return Math.acos((Math.sin(h) - Math.sin(phi) * Math.sin(d)) / (Math.cos(phi) * Math.cos(d)));
    }
}
