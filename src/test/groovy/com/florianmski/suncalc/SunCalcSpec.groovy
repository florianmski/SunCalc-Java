package com.florianmski.suncalc

import com.florianmski.suncalc.models.SunPhase
import com.florianmski.suncalc.models.SunPosition
import spock.lang.Shared

import java.text.DateFormat
import java.text.SimpleDateFormat

import static com.florianmski.suncalc.models.SunPhase.Name.*

/**
 * Unit tests for sun and moon calculations
 */
class SunCalcSpec extends spock.lang.Specification {

    // ------------------------ SUN CALCULATION TESTS -------------------------------

    def "should calculate correct sun position for #location.description"() {

        when:
        SunPosition sunPosition = SunCalc.getSunPosition(location.d, location.lat, location.lon)

        then:
        sunPosition.azimuth == azimuth
        sunPosition.altitude == altitude

        where:
        location       || azimuth             | altitude
        TestData.PARIS || 0.18313767314092333 | -1.0930109733694826

    }

    /**
     * <p>
     *  This test was written from the eastern timezone, using <a href="suncalc.net">suncalc.net</a>. The reason it looks
     *  funny is because although the region is in Paris, the times returned by the web API are always in the person's
     *  default timezone [perhaps by design]. A screenshot is attached in
     * </p>
     * <p>
     *  Depsite its quirks, this parameterized test serves as a solid regression of all calculated phases of the Java
     *  API.
     * </p>
     */
    def "should calculate correct phase #phase for #location.description"() {

        when:
        List<SunPhase> sunPhases = SunCalc.getPhases(location.d, location.lat, location.lon)

        then: "collect a single result"
        List<SunPhase> filteredResults = sunPhases.findAll { it.name == phase }
        filteredResults.size() == 1
        SunPhase sunPhase = filteredResults[0]

        and:
        sunPhase.startDate.time.toString() == start_datetime
        sunPhase.startAngle == start_angle
        sunPhase.endDate.time.toString() == end_datetime
        sunPhase.endAngle == end_angle

        where:
        location       | phase                         | start_datetime                 | start_angle | end_datetime                   | end_angle
        TestData.PARIS | NIGHT_MORNING                 | 'Sun Dec 01 00:00:00 EST 2013' | -18.0       | 'Sun Dec 01 00:30:22 EST 2013' | -18.0
        TestData.PARIS | TWILIGHT_ASTRONOMICAL_MORNING | 'Sun Dec 01 00:30:22 EST 2013' | -18.0       | 'Sun Dec 01 01:08:20 EST 2013' | -12.0
        TestData.PARIS | TWILIGHT_NAUTICAL_MORNING     | 'Sun Dec 01 01:08:20 EST 2013' | -12.0       | 'Sun Dec 01 01:47:59 EST 2013' | -6.0
        TestData.PARIS | TWILIGHT_CIVIL_MORNING        | 'Sun Dec 01 01:47:59 EST 2013' | -6.0        | 'Sun Dec 01 02:24:12 EST 2013' | -0.833
        TestData.PARIS | SUNRISE                       | 'Sun Dec 01 02:24:12 EST 2013' | -0.833      | 'Sun Dec 01 02:28:06 EST 2013' | -0.3
        TestData.PARIS | GOLDEN_HOUR_MORNING           | 'Sun Dec 01 02:28:06 EST 2013' | -0.3        | 'Sun Dec 01 03:17:10 EST 2013' | 6.0
        TestData.PARIS | DAYLIGHT                      | 'Sun Dec 01 03:17:10 EST 2013' | 6.0         | 'Sun Dec 01 10:05:23 EST 2013' | 6.0
        TestData.PARIS | GOLDEN_HOUR_EVENING           | 'Sun Dec 01 10:05:23 EST 2013' | 6.0         | 'Sun Dec 01 10:54:28 EST 2013' | -0.3
        TestData.PARIS | SUNSET                        | 'Sun Dec 01 10:54:28 EST 2013' | -0.3        | 'Sun Dec 01 10:58:21 EST 2013' | -0.833
        TestData.PARIS | TWILIGHT_CIVIL_EVENING        | 'Sun Dec 01 10:58:21 EST 2013' | -0.833      | 'Sun Dec 01 11:34:35 EST 2013' | -6.0
        TestData.PARIS | TWILIGHT_NAUTICAL_EVENING     | 'Sun Dec 01 11:34:35 EST 2013' | -6.0        | 'Sun Dec 01 12:14:14 EST 2013' | -12.0
        TestData.PARIS | TWILIGHT_ASTRONOMICAL_EVENING | 'Sun Dec 01 12:14:14 EST 2013' | -12.0       | 'Sun Dec 01 12:52:12 EST 2013' | -18.0
        TestData.PARIS | NIGHT_EVENING                 | 'Sun Dec 01 12:52:12 EST 2013' | -18.0       | 'Sun Dec 01 00:30:22 EST 2013' | -18.0

    }

    enum TestData {

        PARIS("Paris", 48.818684, 2.323096, [2013, 11, 1, 0, 1, 0])

        TestData(String description, double latitude, double longitude, def datetime) {
            this.description = description
            this.lat = latitude
            this.lon = longitude
            this.d = datetime as GregorianCalendar
        }

        String description
        double lat
        double lon
        Calendar d
    }

    /**
     * port of original SunCalc-JS test
     * https://github.com/mourner/suncalc/blob/master/test.js
     */
    def "getPosition returns azimuth and altitude for the given time and location"() {

        given:
        // zero-based month
        Calendar d = new GregorianCalendar(2013, 2, 5, 0, 0, 0);
        d.setTimeZone(TimeZone.getTimeZone("UTC"))

        when:
        SunPosition actual = SunCalc.getSunPosition(d, 50.5, 30.5)

        then:
        // azimuth angle convention is 180 off, see https://github.com/mourner/suncalc/issues/6
        near(actual.azimuth, -2.5003175907168385 + Math.PI)
        near(actual.altitude, -0.7000406838781611)
    }

    /**
     * port of original SunCalc-JS test
     * https://github.com/mourner/suncalc/blob/master/test.js
     */
    def "getTimes returns sun phases for the given date and location: #description"() {

        given:
        double lat = 50.5
        double lng = 30.5
        DateFormat dtmFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss")
        TimeZone UTC = TimeZone.getTimeZone("UTC")
        dtmFormat.setTimeZone(UTC)
        timeFormat.setTimeZone(UTC)

        and:
        Date date = dtmFormat.parse(startdatetime)
        Calendar d = Calendar.getInstance(UTC)
        d.setTime(date)

        when:
        List<SunPhase> results = SunCalc.getPhases(d, lat, lng)

        then:
        List<SunPhase> actuals = results.findAll { it.name == sunPhaseName }
        // sanity check
        actuals.size == 1
        SunPhase actual = actuals[0]

        expect:
        timeFormat.format(actual.startDate.time) == timeFormat.format(date)

        where:
        sunPhaseName                                | description     | startdatetime
        SunPhase.Name.NIGHT_RISING                  | 'nadir'         | '2013-03-04T22:10:57Z'
        SunPhase.Name.TWILIGHT_ASTRONOMICAL_MORNING | 'nightEnd'      | '2013-03-05T02:46:17Z'
        SunPhase.Name.TWILIGHT_NAUTICAL_MORNING     | 'nauticalDawn'  | '2013-03-05T03:24:31Z'
        SunPhase.Name.TWILIGHT_CIVIL_MORNING        | 'dawn'          | '2013-03-05T04:02:17Z'
        SunPhase.Name.SUNRISE                       | 'sunrise'       | '2013-03-05T04:34:56Z'
        SunPhase.Name.GOLDEN_HOUR_MORNING           | 'sunriseEnd'    | '2013-03-05T04:38:19Z'
        SunPhase.Name.DAYLIGHT                      | 'goldenHourEnd' | '2013-03-05T05:19:01Z'
        SunPhase.Name.DAYLIGHT_SETTING              | 'solarNoon'     | '2013-03-05T10:10:57Z'
        SunPhase.Name.GOLDEN_HOUR_EVENING           | 'goldenHour'    | '2013-03-05T15:02:52Z'
        SunPhase.Name.SUNSET                        | 'sunsetStart'   | '2013-03-05T15:43:34Z'
        SunPhase.Name.TWILIGHT_CIVIL_EVENING        | 'sunset'        | '2013-03-05T15:46:57Z'
        SunPhase.Name.TWILIGHT_NAUTICAL_EVENING     | 'dusk'          | '2013-03-05T16:19:36Z'
        SunPhase.Name.TWILIGHT_ASTRONOMICAL_EVENING | 'nauticalDusk'  | '2013-03-05T16:57:22Z'
        SunPhase.Name.NIGHT_EVENING                 | 'night'         | '2013-03-05T17:35:36Z'


    }

    def "calculations should return same timezone as original: #timeZone"() {

        given:
        // Mumbai, India
        double lat = 19.08
        double lng = 72.88
        TimeZone originalTimeZone = TimeZone.getTimeZone(timeZone)
        Calendar d = Calendar.getInstance(originalTimeZone)
        d.setTime(new Date())

        when:
        List<SunPhase> phases = SunCalc.getPhases(d, lat, lng)

        then:
        phases.forEach {
            assert it.startDate.timeZone == originalTimeZone
            assert it.endDate.timeZone == originalTimeZone
        }

        where:
        // have at least two time zones for the test, in case one of them is the tester's native one
        timeZone   | _
        'GMT+5:50' | _
        'GMT-8:00' | _
    }

    // ----------------------- MOON CALCULATION TESTS -------------------------------

    @Shared
    double MOON_CALC_ACCURACY = 0.05

    def "calculate moon phase illumination for #description using Naval Observatory Data"() {
        given:
        Calendar d = new GregorianCalendar(year, month, day, hour, min, 0)
        d.setTimeZone(TimeZone.getTimeZone("UTC"))

        expect:
        near(SunCalc.getMoonFraction(d), expectedIllumination, MOON_CALC_ACCURACY)

        // expected data from US Naval Observatory
        // http://aa.usno.navy.mil/cgi-bin/aa_phases.pl?year=2013&month=11&day=1&nump=50&format=p
        where:
        year | month             | day | hour | min | expectedIllumination | description
        2013 | Calendar.NOVEMBER | 3   | 0    | 22  | 0.00                 | 'new moon on Nov 3, 2013'
        2013 | Calendar.NOVEMBER | 10  | 15   | 12  | 0.50                 | 'first quarter on Nov 10, 2013'
        2013 | Calendar.NOVEMBER | 17  | 9    | 28  | 1.00                 | 'full moon on Nov 17, 2013'
        2013 | Calendar.NOVEMBER | 25  | 13   | 48  | 0.50                 | 'last quarter on Nov 25, 2013'

    }

    private static double DEFAULT_MARGIN = Math.pow(10, -15)

    private static boolean near(double val1, double val2) {
        return near(val1, val2, DEFAULT_MARGIN)
    }

    private static boolean near(double val1, double val2, double margin) {
        return Math.abs(val1 - val2) < margin
    }


}
