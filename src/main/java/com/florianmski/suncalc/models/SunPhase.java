package com.florianmski.suncalc.models;

import com.florianmski.suncalc.utils.Constants.SunAngles;

import java.security.InvalidParameterException;
import java.util.*;

/**
 * Phases of the Sun for a particular day
 */
public class SunPhase
{
    public enum Name
    {
        NIGHT_MORNING("Night Morning"),
        TWILIGHT_ASTRONOMICAL_MORNING("Twilight Astronomical Morning"),
        TWILIGHT_NAUTICAL_MORNING("Twilight Nautical Morning"),
        TWILIGHT_CIVIL_MORNING("Twilight Civil Morning"),
        SUNRISE("Sunrise"),
        GOLDEN_HOUR_MORNING("Golden Hour Morning"),
        DAYLIGHT("Daylight"),
            DAYLIGHT_RISING("Daylight Rising"),
            DAYLIGHT_SETTING("Daylight Setting"),
        GOLDEN_HOUR_EVENING("Golden Hour Evening"),
        SUNSET("Sunset"),
        TWILIGHT_CIVIL_EVENING("Twilight Civil Evening"),
        TWILIGHT_NAUTICAL_EVENING("Twilight Nautical Evening"),
        TWILIGHT_ASTRONOMICAL_EVENING("Twilight Astronomical Evening"),
        NIGHT_EVENING("Night Evening"),
            NIGHT_SETTING("Night Setting"),
            NIGHT_RISING("Night Rising");

        private final String value;
        Name(String value)
        {
            this.value = value;
        }

        @Override
        public String toString()
        {
            return this.value;
        }

        private static final Map<String, Name> STRING_MAPPING = new HashMap<String, Name>();

        static
        {
            for (Name via : Name.values())
            {
                STRING_MAPPING.put(via.toString().toUpperCase(), via);
            }
        }

        public static Name fromValue(String value)
        {
            return STRING_MAPPING.get(value.toUpperCase());
        }

    }

    private Name name;
    private double startAngle, endAngle;
    private boolean startRise, endRise;
    private Calendar startDate, endDate;

    /**
     * Describes the sun's position between two angles
     *
     * @param name common name
     * @param startAngle zenith angle in degrees at the start. See {@link SunAngles} for details
     * @param startRise is the sun rising during the starting angle? (direction second derivative)
     * @param endAngle  zenith angle in degrees at the end. See {@link SunAngles} for details
     * @param endRise is the sun rising during the starting angle? (direction of second derivative)
     */
    private SunPhase(Name name, double startAngle, boolean startRise, double endAngle, boolean endRise)
    {
        this.name = name;
        this.startAngle = startAngle;
        this.startRise = startRise;
        this.endAngle = endAngle;
        this.endRise = endRise;
    }

    /**
     * Retrieves the interval of a sun phase
     *
     * @param name the name of the phase
     * @return the phase itself, containing its starting and ending zenith angles
     */
    public static SunPhase get(Name name)
    {
        switch(name)
        {
            case SUNRISE:
                return new SunPhase(name, SunAngles.SUNRISE_START, true, SunAngles.SUNRISE_END, true);
            case GOLDEN_HOUR_MORNING:
                return new SunPhase(name, SunAngles.GOLDEN_HOUR_MORNING_START, true, SunAngles.GOLDEN_HOUR_MORNING_END, true);

            case DAYLIGHT:
                return new SunPhase(name, SunAngles.DAYLIGHT_START, true, SunAngles.DAYLIGHT_END, false);
            case DAYLIGHT_RISING:
                return new SunPhase(name, SunAngles.DAYLIGHT_START, true, SunAngles.SOLAR_NOON, true);
            case DAYLIGHT_SETTING:
                return new SunPhase(name, SunAngles.SOLAR_NOON, false, SunAngles.DAYLIGHT_END, false);

            case GOLDEN_HOUR_EVENING:
                return new SunPhase(name, SunAngles.GOLDEN_HOUR_EVENING_START, false, SunAngles.GOLDEN_HOUR_EVENING_END, false);
            case SUNSET:
                return new SunPhase(name, SunAngles.SUNSET_START, false, SunAngles.SUNSET_END, false);
            case TWILIGHT_ASTRONOMICAL_EVENING:
                return new SunPhase(name, SunAngles.TWILIGHT_ASTRONOMICAL_EVENING_START, false, SunAngles.TWILIGHT_ASTRONOMICAL_EVENING_END, false);
            case TWILIGHT_NAUTICAL_EVENING:
                return new SunPhase(name, SunAngles.TWILIGHT_NAUTICAL_EVENING_START, false, SunAngles.TWILIGHT_NAUTICAL_EVENING_END, false);
            case TWILIGHT_CIVIL_EVENING:
                return new SunPhase(name, SunAngles.TWILIGHT_CIVIL_EVENING_START, false, SunAngles.TWILIGHT_CIVIL_EVENING_END, false);

            case NIGHT_EVENING:
                return new SunPhase(name, SunAngles.NIGHT_START, false, SunAngles.NIGHT_END, true);
            case NIGHT_MORNING:
                return new SunPhase(name, SunAngles.NIGHT_START, false, SunAngles.NIGHT_END, true);
            case NIGHT_SETTING:
                return new SunPhase(name, SunAngles.NIGHT_START, false, SunAngles.NADIR, false);
            case NIGHT_RISING:
                return new SunPhase(name, SunAngles.NADIR, true, SunAngles.NIGHT_END, true);

            case TWILIGHT_CIVIL_MORNING:
                return new SunPhase(name, SunAngles.TWILIGHT_CIVIL_MORNING_START, true, SunAngles.TWILIGHT_CIVIL_MORNING_END, true);
            case TWILIGHT_NAUTICAL_MORNING:
                return new SunPhase(name, SunAngles.TWILIGHT_NAUTICAL_MORNING_START, true, SunAngles.TWILIGHT_NAUTICAL_MORNING_END, true);
            case TWILIGHT_ASTRONOMICAL_MORNING:
                return new SunPhase(name, SunAngles.TWILIGHT_ASTRONOMICAL_MORNING_START, true, SunAngles.TWILIGHT_ASTRONOMICAL_MORNING_END, true);
            default:
                throw new InvalidParameterException(name.value + " is not supported");
        }
    }

    public static List<SunPhase> all()
    {
        List<SunPhase> results = new ArrayList<SunPhase>();
        for(Name n : Name.values())
            results.add(get(n));
        return results;
    }

    public Name getName()
    {
        return name;
    }

    /**
     * @return is the sun rising during the starting angle? (direction second derivative)
     */
    public boolean isStartRise()
    {
        return startRise;
    }

    /**
     * @return is the sun rising during the starting angle? (direction of second derivative)
     */
    public boolean isEndRise()
    {
        return endRise;
    }

    /**
     * @return zenith angle in degrees at the start of the phase. See {@link SunAngles} for details
     */
    public double getStartAngle()
    {
        return startAngle;
    }

    /**
     * @return zenith angle in degrees at the end of the phase. See {@link SunAngles} for details
     */
    public double getEndAngle()
    {
        return endAngle;
    }

    public Calendar getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Calendar startDate)
    {
        this.startDate = startDate;
    }

    public Calendar getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Calendar endDate)
    {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "SunPhase{" +
                "name=" + name +
                ", startAngle=" + startAngle +
                ", endAngle=" + endAngle +
                ", startRise=" + startRise +
                ", endRise=" + endRise +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
