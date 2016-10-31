package com.florianmski.suncalc.models;

public class EquatorialCoordinates
{
    private double rightAscension, declination;

    public EquatorialCoordinates(double rightAscension, double declination)
    {
        this.rightAscension = rightAscension;
        this.declination = declination;
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
}
