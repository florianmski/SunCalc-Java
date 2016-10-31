package com.florianmski.suncalc

import com.florianmski.suncalc.models.SunPhase
import com.florianmski.suncalc.models.SunPosition
import spock.lang.Ignore

import static com.florianmski.suncalc.models.SunPhase.Name.*

/**
 * Spock tests methods for {@link SunCalc}
 */
class SunCalcSpec extends spock.lang.Specification {


    def "should calculate correct sun position for #location.description"() {

        when:
        SunPosition sunPosition = SunCalc.getSunPosition(location.d, location.lat, location.lon)

        then:
        sunPosition.azimuth == azimuth
        sunPosition.altitude == altitude

        where:
        location       || azimuth | altitude
        TestData.PARIS || 0.18313767314092333 | -1.0930109733694826

    }

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
        TestData.PARIS | NIGHT_EVENING                 | 'Sun Dec 01 12:52:12 EST 2013' | -18.0       | 'Sun Dec 01 23:59:59 EST 2013' | -18.0

    }

    @Ignore("original Moon test code ported, but not yet fully tested")
    def "moonPhaseTest"() {
        given:
        Calendar d = new GregorianCalendar(2013, 11, 1, 0, 1, 0);

        expect:
        System.out.println("fraction : " + SunCalc.getMoonFraction(d));

        for(int i = 0; i < 31; i++)
        {
            d.roll(Calendar.DAY_OF_YEAR, 1);
            System.out.println("fraction : " + SunCalc.getMoonFraction(d));
        }
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


}
