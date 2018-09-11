package com.florianmski.suncalc.utils;

public class Constants
{
    /** Multiplier to convert from degrees to radians */
    public final static double TO_RAD = Math.PI / 180.0;

    /** Measure of the tilt of the earth, in radians */
    public final static double EARTH_OBLIQUITY = TO_RAD * 23.4397;

    /**
     * <p>
     *     Solar elevation angles in degrees, i.e. the angle of the sun from the horizon.
     *     Defined as 90 deg - solar zenith angle.
     * </p>
     * <p>
     *     See <a href="https://en.wikipedia.org/wiki/Solar_zenith_angle">
     *     https://en.wikipedia.org/wiki/Solar_zenith_angle</a> and
     *     <a href="https://en.wikipedia.org/wiki/Twilight">https://en.wikipedia.org/wiki/Twilight</a>
     *     for more info.
     * </p>
     */
    public class SunAngles
    {
        /** sunrise (top edge of the sun appears on the horizon) */
        public final static double SUNRISE_START                        = -0.833;
        /** soft, warm light as sun is rising (best time for photography) */
         public final static double GOLDEN_HOUR_MORNING_START            = -0.3;
        /** daylight starts */
        public final static double DAYLIGHT_START                       = 6.0;
        /** soft, warm light as sun is setting (best time for photography) */
        public final static double GOLDEN_HOUR_EVENING_START            = DAYLIGHT_START;
        /** sunset starts (bottom edge of the sun touches the horizon) */
        public final static double SUNSET_START                         = GOLDEN_HOUR_MORNING_START;
        /** evening civil twilight starts (sun disappears below the horizon) */
        public final static double TWILIGHT_CIVIL_EVENING_START = SUNRISE_START;
        /** evening nautical twilight starts (many brighter stars start appearing, horizon faintly visible) */
        public final static double TWILIGHT_NAUTICAL_EVENING_START      = -6.0;
        /** evening astronomical twilight starts (fainter stars start appearing) */
        public final static double TWILIGHT_ASTRONOMICAL_EVENING_START  = -12.0;
        /** night starts (dark enough for astronomical observations) */
        public final static double NIGHT_START                          = -18.0;
        /** astronomical dawn (fainter stars start disappearing) */
        public final static double TWILIGHT_ASTRONOMICAL_MORNING_START  = NIGHT_START;
        /** nautical dawn (brighter stars start disappearing) */
        public final static double TWILIGHT_NAUTICAL_MORNING_START      = TWILIGHT_ASTRONOMICAL_EVENING_START;
        /** dawn (atmosphere begins to scatter light) */
        public final static double TWILIGHT_CIVIL_MORNING_START         = TWILIGHT_NAUTICAL_EVENING_START;

        /** sunrise ends (bottom edge of the sun touches the horizon) */
        public final static double SUNRISE_END                          = GOLDEN_HOUR_MORNING_START;
        /** morning golden hour (soft light, best time for photography) ends */
        public final static double GOLDEN_HOUR_MORNING_END              = DAYLIGHT_START;
        /** daylight ends, evening golden hour starts */
        public final static double DAYLIGHT_END                         = GOLDEN_HOUR_EVENING_START;
        /** sunset starts (bottom edge of the sun touches the horizon) */
        public final static double GOLDEN_HOUR_EVENING_END              = SUNSET_START;
        /** sunset ends (sun disappears below the horizon) */
        public final static double SUNSET_END                           = TWILIGHT_CIVIL_EVENING_START;
        /** dusk (many brighter stars start appearing, horizon faintly visible) */
        public final static double TWILIGHT_CIVIL_EVENING_END = TWILIGHT_NAUTICAL_EVENING_START;
        /** nautical dusk (fainter stars start appearing) */
        public final static double TWILIGHT_NAUTICAL_EVENING_END        = TWILIGHT_ASTRONOMICAL_EVENING_START;
        /** night starts, astronomical dusk (dark enough for astronomical observations) */
        public final static double TWILIGHT_ASTRONOMICAL_EVENING_END    = NIGHT_START;
        /** night ends (fainter stars start disappearing) */
        public final static double NIGHT_END                            = TWILIGHT_ASTRONOMICAL_MORNING_START;
        /** morning astronomical twilight ends (brighter stars start disappearing) */
        public final static double TWILIGHT_ASTRONOMICAL_MORNING_END    = TWILIGHT_NAUTICAL_MORNING_START;
        /** morning nautical twilight ends (atmosphere begins to scatter light) */
        public final static double TWILIGHT_NAUTICAL_MORNING_END        = TWILIGHT_CIVIL_MORNING_START;
        /** morning civil twilight ends (top edge of the sun appears on the horizon) */
        public final static double TWILIGHT_CIVIL_MORNING_END           = SUNRISE_START;

        /** sun is directly overhead */
        public final static double SOLAR_NOON                           = 90;
        /** sun is directly below */
        public final static double NADIR                                = -90;
    }
}
