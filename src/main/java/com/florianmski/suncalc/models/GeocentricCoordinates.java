package com.florianmski.suncalc.models;

/**
 * Container class for the equatorial coordinate system, when using the Earth as the point of reference. See
 * <a href="https://en.wikipedia.org/wiki/Equatorial_coordinate_system#Geocentric_equatorial_coordinates>
 * https://en.wikipedia.org/wiki/Equatorial_coordinate_system#Geocentric_equatorial_coordinates</a> for more info.
 */
public class GeocentricCoordinates
{
    private double rightAscension, declination, distance;


    /**
     * @param rightAscension the angular distance measured <em>eastward</em>, in radians
     * @param declination in radians
     * @param distance in km, to the celestial body in question
     */
    public GeocentricCoordinates(double rightAscension, double declination, double distance)
    {
        this.rightAscension = rightAscension;
        this.declination = declination;
        this.distance = distance;
    }

    /**
     * @return the angular distance measured <em>eastward</em>, in radians
     */
    public double getRightAscension()
    {
        return rightAscension;
    }

    /**
     *
     * @param rightAscension the angular distance measured <em>eastward</em>, in radians
     */
    public void setRightAscension(double rightAscension)
    {
        this.rightAscension = rightAscension;
    }

    /**
     * @return in radians
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

    /**
     * @return in km, to the celestial body in question
     */
    public double getDistance()
    {
        return distance;
    }

    /**
     * @param distance in km, to the celestial body in question
     */
    public void setDistance(double distance)
    {
        this.distance = distance;
    }
}
