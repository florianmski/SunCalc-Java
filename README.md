SunCalc-Java
============

A Java port (with some tweaks) of the awesome [SunCalc JS lib](https://github.com/mourner/suncalc).

How to use
==========

```
// now
Calendar d = Calendar.getInstance();
// Paris coordinates
double LAT = 48.818684;
double LON = 2.323096;

// get a list of phases at a given location & day
List<SunPhase> sunPhases = SunCalc.getPhases(d, LAT, LON);
for(SunPhase e : SunCalc.getPhases(d, LAT, LON))
{
    System.out.println("Phase : " + e.getName());
    System.out.println("start at : " + e.getStartDate().getTime());
    System.out.println("end at   : " + e.getEndDate().getTime());
    System.out.println("===========================================");
}

// get the sun position (azimuth and elevation) at a given location & time
SunPosition sp = SunCalc.getSunPosition(d, LAT, LON);
```

Gotchas
=======

Currently supported sun phases are:

* Night (Morning)
* Twilight Astronomical (Morning)
* Twilight Nautical (Morning)
* Twilight Civil (Morning)
* Sunrise (Morning)
* Golden Hour (Morning)
* Daylight
* Golden Hour (Evening)
* Sunset (Evening)
* Twilight Civil (Evening)
* Twilight Nautical (Evening)
* Twilight Astronomical (Evening)
* Night (Evening)

***

There is an implementation of the moon phases but it has not been really tested, use it at your own risks.

***

If you try to get sun phases at extremes location (such as poles) you could get invalid dates (such as the famous January 1970)

Written By
============

* Florian Mierzejewski - <florian.pub@gmail.com>


License
=======

    "THE BEER-WARE LICENSE" (Revision 42):
    You can do whatever you want with this stuff. 
    If we meet some day, and you think this stuff is worth it, you can buy me a beer in return.