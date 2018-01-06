package com.florianmski.suncalc.utils;

/**
 * Calculations for the position of the celestial bodies.
 */
public class PositionUtils
{

    /** Sidereal time from Earth, in <strong>degrees</strong> at longitude 0 degrees at the instant of J2000 (table 5) */
    private static final double THETA_0 = 280.16;

    /** Rate of change of sidereal time from Earth, in <strong>degrees/day</strong> (table 5) */
    private static final double THETA_1 = 360.9856235;

    public static double getRightAscension(double l, double b)
    {
        return Math.atan2(Math.sin(l) * Math.cos(Constants.EARTH_OBLIQUITY) - Math.tan(b) * Math.sin(Constants.EARTH_OBLIQUITY), Math.cos(l));
    }

    /**
     * Declination of a celestial body, with respect to an observer on the Earth (eq. 12)
     *
     * @param l ecliptic longitude of the celestial body, in radians
     * @param b ecliptic latitude of the sun for the celestial body
     * @return declination for an Earth observer, in radians
     */
    public static double getDeclination(double l, double b)
    {
        return Math.asin(Math.sin(b) * Math.cos(Constants.EARTH_OBLIQUITY) + Math.cos(b) * Math.sin(Constants.EARTH_OBLIQUITY) * Math.sin(l));
    }

    /**
     * The azimuth angle of the celestial body (eq. 25)
     * <p>
     *  Note this returns the angle <strong>taking NORTH as zero</strong>, unlike the original SunCalcJS. See <a
     *  href="https://github.com/mourner/suncalc/issues/6">https://github.com/mourner/suncalc/issues/6</a> for more info
     * </p>
     * @param H the hour angle, measured in radians
     * @param phi latitude, in radians
     * @param dec declination, in radians
     * @return the azimuth in radians, with NORTH as zero
     */
    public static double getAzimuth(double H, double phi, double dec)
    {
        return Math.PI + Math.atan2(Math.sin(H), Math.cos(H) * Math.sin(phi) - Math.tan(dec) * Math.cos(phi));
    //  return           Math.atan2(Math.sin(H), Math.cos(H) * Math.sin(phi) - Math.tan(dec) * Math.cos(phi));
    }

    /**
     * The altitude above the horizon of the celestial body (eq. 23)
     *
     * @param H the hour angle, measured in radians
     * @param phi latitude, in radians
     * @param dec declination, in radians
     * @return the altitude in radians
     */
    public static double getAltitude(double H, double phi, double dec)
    {
        return Math.asin(Math.sin(phi) * Math.sin(dec) + Math.cos(phi) * Math.cos(dec) * Math.cos(H));
    }

    /**
     * Sidereal time, from the perspective of the Earth (eq. 20)
     *
     * @param d number of Julian days since Jan 1, 2000
     * @param lw West longitude, in radians
     * @return the sidereal time, in radians
     */
    public static double getSiderealTime(double d, double lw)
    {
        return Constants.TO_RAD * (THETA_0 + THETA_1 * d) - lw;
    }
}
