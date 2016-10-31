package com.florianmski.suncalc.utils;

public class Constants
{
    public final static double RAD = Math.PI / 180.0;
    public final static double E = RAD * 23.4397; // obliquity of the Earth

    public class SunAngles
    {
        public final static double SUNRISE_START                        = -0.833;
        public final static double GOLDEN_HOUR_MORNING_START            = -0.3;
        public final static double DAYLIGHT_START                       = 6.0;
        public final static double GOLDEN_HOUR_EVENING_START            = DAYLIGHT_START;
        public final static double SUNSET_START                         = GOLDEN_HOUR_MORNING_START;
        public final static double TWILIGHT_CIVIC_EVENING_START         = SUNRISE_START;
        public final static double TWILIGHT_NAUTICAL_EVENING_START      = -6.0;
        public final static double TWILIGHT_ASTRONOMICAL_EVENING_START  = -12.0;
        public final static double NIGHT_START                          = -18.0;
        public final static double TWILIGHT_ASTRONOMICAL_MORNING_START  = NIGHT_START;
        public final static double TWILIGHT_NAUTICAL_MORNING_START      = TWILIGHT_ASTRONOMICAL_EVENING_START;
        public final static double TWILIGHT_CIVIL_MORNING_START         = TWILIGHT_NAUTICAL_EVENING_START;

        public final static double SUNRISE_END                          = GOLDEN_HOUR_MORNING_START;
        public final static double GOLDEN_HOUR_MORNING_END              = DAYLIGHT_START;
        public final static double DAYLIGHT_END                         = GOLDEN_HOUR_EVENING_START;
        public final static double GOLDEN_HOUR_EVENING_END              = SUNSET_START;
        public final static double SUNSET_END                           = TWILIGHT_CIVIC_EVENING_START;
        public final static double TWILIGHT_CIVIC_EVENING_END           = TWILIGHT_NAUTICAL_EVENING_START;
        public final static double TWILIGHT_NAUTICAL_EVENING_END        = TWILIGHT_ASTRONOMICAL_EVENING_START;
        public final static double TWILIGHT_ASTRONOMICAL_EVENING_END    = NIGHT_START;
        public final static double NIGHT_END                            = TWILIGHT_ASTRONOMICAL_MORNING_START;
        public final static double TWILIGHT_ASTRONOMICAL_MORNING_END    = TWILIGHT_NAUTICAL_MORNING_START;
        public final static double TWILIGHT_NAUTICAL_MORNING_END        = TWILIGHT_CIVIL_MORNING_START;
        public final static double TWILIGHT_CIVIL_MORNING_END           = SUNRISE_START;
    }
}
