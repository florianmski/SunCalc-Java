package com.florianmski.suncalc;

import com.florianmski.suncalc.models.SunPhase;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main
{
    private static Calendar d = new GregorianCalendar(2013, 11, 1, 0, 1, 0);
    // Paris coordinates
    private static final double LAT = 48.818684;
    private static final double LON = 2.323096;

    public static void main (String[] args)
    {
        sunPhaseTest();
//        moonPhaseTest();
    }

    private static void sunPhaseTest()
    {
        System.out.println("NOW : " + d.getTime().toString());

        for(SunPhase e : SunCalc.getPhases(d, LAT, LON))
        {
            System.out.println("Event : " + e.getName());
            System.out.println("    start at : " + e.getStartDate().getTime().toString() + " angle : " + e.getStartAngle());
            System.out.println("    end at   : " + e.getEndDate().getTime().toString() + " angle : " + e.getEndAngle());
            System.out.println("===========================================");
        }

        System.out.println("azimuth : " + SunCalc.getSunPosition(d, LAT, LON).getAzimuth());
        System.out.println("altitude : " + SunCalc.getSunPosition(d, LAT, LON).getAltitude());
    }

    private static void moonPhaseTest()
    {
        System.out.println("fraction : " + SunCalc.getMoonFraction(d));

        for(int i = 0; i < 31; i++)
        {
            d.roll(Calendar.DAY_OF_YEAR, 1);
            System.out.println("fraction : " + SunCalc.getMoonFraction(d));
        }
    }
}
