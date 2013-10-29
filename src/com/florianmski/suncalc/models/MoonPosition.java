package com.florianmski.suncalc.models;

public class MoonPosition
{
    private double azimuth, altitude, distance;

    public MoonPosition(double azimuth, double altitude, double distance)
    {
        this.azimuth = azimuth;
        this.altitude = altitude;
        this.distance = distance;
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

    public double getDistance()
    {
        return distance;
    }

    public void setDistance(double distance)
    {
        this.distance = distance;
    }
}
