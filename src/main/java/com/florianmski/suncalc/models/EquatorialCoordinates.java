package com.florianmski.suncalc.models;

/**
 * Container class for the equatorial coordinate system. See
 * <a href="https://en.wikipedia.org/wiki/Equatorial_coordinate_system>
 * https://en.wikipedia.org/wiki/Equatorial_coordinate_system</a> for more info.
 */
public class EquatorialCoordinates
{
    private double rightAscension, declination;

    /**
     * @param rightAscension the angular distance measured <em>eastward</em>, in radians
     * @param declination in radians
     */
    public EquatorialCoordinates(double rightAscension, double declination)
    {
        this.rightAscension = rightAscension;
        this.declination = declination;
    }

    /**
     * @return the angular distance measured <em>eastward</em>, in radians
     */
    public double getRightAscension()
    {
        return rightAscension;
    }

    /**
     * @param rightAscension the angular distance measured <em>eastward</em>, in radians
     */
    public void setRightAscension(double rightAscension)
    {
        this.rightAscension = rightAscension;
    }

    /**
     * @return value in radians
     */
    public double getDeclination()
    {
        return declination;
    }

    /**
     * @param declination in radians
     */
    public void setDeclination(double declination)
    {
        this.declination = declination;
    }
}
