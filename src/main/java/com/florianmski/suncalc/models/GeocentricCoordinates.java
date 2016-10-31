package com.florianmski.suncalc.models;

public class GeocentricCoordinates
{
    private double rightAscension, declination, distance;

    public GeocentricCoordinates(double rightAscension, double declination, double distance)
    {
        this.rightAscension = rightAscension;
        this.declination = declination;
        this.distance = distance;
    }

    public double getRightAscension()
    {
        return rightAscension;
    }

    public void setRightAscension(double rightAscension)
    {
        this.rightAscension = rightAscension;
    }

    public double getDeclination()
    {
        return declination;
    }

    public void setDeclination(double declination)
    {
        this.declination = declination;
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
