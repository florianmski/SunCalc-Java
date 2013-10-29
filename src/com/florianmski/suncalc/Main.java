package com.florianmski.suncalc;

import com.florianmski.suncalc.models.SunPhase;
import com.florianmski.suncalc.utils.DateUtils;

import java.util.Calendar;

public class Main
{
    private static Calendar d = Calendar.getInstance();
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

        System.out.println("position : " + SunCalc.getSunPosition(d, LAT, LON).getAzimuth() + "°");

        for(int i = 0; i < 10; i++)
        {
            d.roll(Calendar.HOUR_OF_DAY, 1);
            System.out.println("toDays : " + DateUtils.toDays(d));
            System.out.println("getSunPosition : " + SunCalc.getSunPosition(d, LAT, LON).getAzimuth());
        }
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
