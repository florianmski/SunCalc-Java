package com.florianmski.suncalc;


import com.florianmski.suncalc.models.*;
import com.florianmski.suncalc.utils.*;

import java.util.Calendar;
import java.util.List;

public class SunCalc
{
    public static SunPosition getSunPosition(Calendar date, double lat, double lng)
    {
        double lw  = Constants.RAD * -lng;
        double phi = Constants.RAD * lat;
        double d   = DateUtils.toDays(date);

        EquatorialCoordinates c = SunUtils.getSunCoords(d);
        double H  = PositionUtils.getSiderealTime(d, lw) - c.getRightAscension();

        return new SunPosition(
                PositionUtils.getAzimuth(H, phi, c.getDeclination()),
                PositionUtils.getAltitude(H, phi, c.getDeclination()));
    }

    public static MoonPosition getMoonPosition(Calendar date, double lat, double lng)
    {
        double lw  = Constants.RAD * -lng;
        double phi = Constants.RAD * lat;
        double d = DateUtils.toDays(date);

        GeocentricCoordinates c = MoonUtils.getMoonCoords(d);
        double H = PositionUtils.getSiderealTime(d, lw) - c.getRightAscension();
        double h = PositionUtils.getAltitude(H, phi, c.getDeclination());

        // altitude correction for refraction
        h = h + Constants.RAD * 0.017 / Math.tan(h + Constants.RAD * 10.26 / (h + Constants.RAD * 5.10));

        return new MoonPosition(PositionUtils.getAzimuth(H, phi, c.getDeclination()), h, c.getDistance());
    }

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

    public static List<SunPhase> getPhases(Calendar date, double lat, double lng)
    {
        double lw  = Constants.RAD * -lng;
        double phi = Constants.RAD * lat;
        double d   = DateUtils.toDays(date);

        double n  = TimeUtils.getJulianCycle(d, lw);
        double ds = TimeUtils.getApproxTransit(0, lw, n);

        double M = SunUtils.getSolarMeanAnomaly(ds);
        double C = SunUtils.getEquationOfCenter(M);
        double L = SunUtils.getEclipticLongitude(M, C);

        double dec = PositionUtils.getDeclination(L, 0);

        double jnoon = TimeUtils.getSolarTransitJ(ds, M, L);

        List<SunPhase> results = SunPhase.all();

        for(SunPhase sunPhase : results)
        {
            sunPhase.setStartDate(getPhaseDate(sunPhase.getStartAngle(), sunPhase.isStartRise(), jnoon, phi, dec, lw, n, M, L));
            sunPhase.setEndDate(getPhaseDate(sunPhase.getEndAngle(), sunPhase.isEndRise(), jnoon, phi, dec, lw, n, M, L));
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
        double h = angle * Constants.RAD;
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
