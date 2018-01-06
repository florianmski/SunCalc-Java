package com.florianmski.suncalc;


import com.florianmski.suncalc.models.*;
import com.florianmski.suncalc.utils.*;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Calculations for the sun and moon relative to earth
 */
public class SunCalc
{
    /**
     *
     * Calculates the sun's position at a particular location and moment
     *
     * @param date the day, time and timezone to calculate for
     * @param lat measured from North, in degrees
     * @param lng measured from East, in degrees
     * @return the sun's position in the sky relative to the location
     */
    public static SunPosition getSunPosition(Calendar date, double lat, double lng)
    {
        double lw  = Constants.TO_RAD * -lng;
        double phi = Constants.TO_RAD * lat;
        double d   = DateUtils.toDays(date);

        EquatorialCoordinates c = SunUtils.getSunCoords(d);
        double H  = PositionUtils.getSiderealTime(d, lw) - c.getRightAscension();

        return new SunPosition(
                PositionUtils.getAzimuth(H, phi, c.getDeclination()),
                PositionUtils.getAltitude(H, phi, c.getDeclination()));
    }

    /**
     *
     * Calculates the moon's position at a particular location and moment
     *
     * @param date the day, time and timezone to calculate for
     * @param lat measured from North, in degrees
     * @param lng measured from East, in degrees
     * @return the moon's position in the sky relative to the location
     */
    public static MoonPosition getMoonPosition(Calendar date, double lat, double lng)
    {
        double lw  = Constants.TO_RAD * -lng;
        double phi = Constants.TO_RAD * lat;
        double d = DateUtils.toDays(date);

        GeocentricCoordinates c = MoonUtils.getMoonCoords(d);
        double H = PositionUtils.getSiderealTime(d, lw) - c.getRightAscension();
        double h = PositionUtils.getAltitude(H, phi, c.getDeclination());

        // altitude correction for refraction
        h = h + Constants.TO_RAD * 0.017 / Math.tan(h + Constants.TO_RAD * 10.26 / (h + Constants.TO_RAD * 5.10));

        return new MoonPosition(PositionUtils.getAzimuth(H, phi, c.getDeclination()), h, c.getDistance());
    }

    /**
     * Calculates moon illumination for a particular day and time.
     * Location is not needed because percentage will be the same for
     * both Northern and Southern hemisphere.
     *
     * @param date the day, time and timezone to calculate for
     * @return fraction of moon's illuminated limb and phase
     */
    public static double getMoonFraction(Calendar date)
    {
        double d = DateUtils.toDays(date);
        EquatorialCoordinates s = SunUtils.getSunCoords(d);
        GeocentricCoordinates m = MoonUtils.getMoonCoords(d);

        int sdist = 149598000; // distance from Earth to Sun in km

        double phi = Math.acos(Math.sin(s.getDeclination()) * Math.sin(m.getDeclination()) + Math.cos(s.getDeclination()) * Math.cos(m.getDeclination()) * Math.cos(s.getRightAscension() - m.getRightAscension()));
        double inc = Math.atan2(sdist * Math.sin(phi), m.getDistance() - sdist * Math.cos(phi));

        return (1 + Math.cos(inc)) / 2;
    }

    /**
     * Calculates phases of the sun for a single day
     *
     * @param date the day and timezone to calculate sun positions for, time is ignored
     * @param lat measured from North, in degrees
     * @param lng measured from East, in degrees
     * @return phases by name, with their start/end angles and start/end times
     */
    public static List<SunPhase> getPhases(Calendar date, double lat, double lng)
    {
        double lw  = Constants.TO_RAD * -lng;
        double phi = Constants.TO_RAD * lat;
        double d   = DateUtils.toDays(date);

        double n  = TimeUtils.getJulianCycle(d, lw);
        double ds = TimeUtils.getApproxTransit(0, lw, n);

        double M = SunUtils.getSolarMeanAnomaly(d);
        double C = SunUtils.getEquationOfCenter(M);
        double L = SunUtils.getEclipticLongitude(M, C);

        double dec = PositionUtils.getDeclination(L, 0);

        double jnoon = TimeUtils.getSolarTransitJ(ds, M, L);

        List<SunPhase> results = SunPhase.all();

        TimeZone originalTimeZone = date.getTimeZone();
        for(SunPhase sunPhase : results)
        {
            // not pretty, this is to have correct timezones
            Calendar startDate = getPhaseDate(sunPhase.getStartAngle(), sunPhase.isStartRise(), jnoon, phi, dec, lw, n, M, L);
            startDate.setTimeZone(originalTimeZone);
            sunPhase.setStartDate(startDate);
            Calendar endDate = getPhaseDate(sunPhase.getEndAngle(), sunPhase.isEndRise(), jnoon, phi, dec, lw, n, M, L);
            endDate.setTimeZone(originalTimeZone);
            sunPhase.setEndDate(endDate);
        }

        // not pretty, this is to have correct dates
        Calendar nightMorningStartDate = (Calendar) date.clone();
        nightMorningStartDate.set(Calendar.HOUR_OF_DAY, 0);
        nightMorningStartDate.set(Calendar.MINUTE, 0);
        nightMorningStartDate.set(Calendar.SECOND, 0);
        nightMorningStartDate.set(Calendar.MILLISECOND, 0);
        results.get(0).setStartDate(nightMorningStartDate);

        Calendar nightEveningEndDate = (Calendar) date.clone();
        nightEveningEndDate.set(Calendar.HOUR_OF_DAY, 23);
        nightEveningEndDate.set(Calendar.MINUTE, 59);
        nightEveningEndDate.set(Calendar.SECOND, 59);
        nightEveningEndDate.set(Calendar.MILLISECOND, 999);
        results.get(results.size()-1).setEndDate(nightEveningEndDate);

        return results;
    }

    private static Calendar getPhaseDate(double angle, boolean rising, double jnoon, double phi, double dec, double lw, double n, double M, double L)
    {
        double h = angle * Constants.TO_RAD;
        double w = TimeUtils.getHourAngle(h, phi, dec);
        double a = TimeUtils.getApproxTransit(w, lw, n);

        // set time for the given sun altitude
        double jset = TimeUtils.getSolarTransitJ(a, M, L);

        if(rising)
        {
            double jrise = jnoon - (jset - jnoon);
            return DateUtils.fromJulian(jrise);
        }
        else
            return DateUtils.fromJulian(jset);
    }
}
