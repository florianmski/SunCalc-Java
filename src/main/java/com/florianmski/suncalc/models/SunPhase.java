package com.florianmski.suncalc.models;

import com.florianmski.suncalc.utils.Constants.SunAngles;

import java.security.InvalidParameterException;
import java.util.*;

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
        GOLDEN_HOUR_EVENING("Golden Hour Evening"),
        SUNSET("Sunset"),
        TWILIGHT_CIVIL_EVENING("Twilight Civil Evening"),
        TWILIGHT_NAUTICAL_EVENING("Twilight Nautical Evening"),
        TWILIGHT_ASTRONOMICAL_EVENING("Twilight Astronomical Evening"),
        NIGHT_EVENING("Night Evening");

        private final String value;
        private Name(String value)
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

    private SunPhase(Name name, double startAngle, boolean startRise, double endAngle, boolean endRise)
    {
        this.name = name;
        this.startAngle = startAngle;
        this.startRise = startRise;
        this.endAngle = endAngle;
        this.endRise = endRise;
    }

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

    public boolean isStartRise()
    {
        return startRise;
    }

    public boolean isEndRise()
    {
        return endRise;
    }

    public double getStartAngle()
    {
        return startAngle;
    }

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
