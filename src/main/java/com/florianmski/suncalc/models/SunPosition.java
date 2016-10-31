package com.florianmski.suncalc.models;

public class SunPosition
{
    private double azimuth, altitude;

    public SunPosition(double azimuth, double altitude)
    {
        this.azimuth = azimuth;
        this.altitude = altitude;
    }

    public double getAzimuth()
    {
        return azimuth;
    }

    public void setAzimuth(double azimuth)
    {
        this.azimuth = azimuth;
    }

    public double getAltitude()
    {
        return altitude;
    }

    public void setAltitude(double altitude)
    {
        this.altitude = altitude;
    }
}
