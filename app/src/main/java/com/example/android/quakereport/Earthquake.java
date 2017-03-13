package com.example.android.quakereport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Earthquake {
    private double mMagnitude;
    private String mLocation;
    private Long mDate;
    // contains detail information about earthquake
    private String mUrl;
    //Locale ukraineLocale = new Locale("ukr", "ua");

    public Earthquake(double magnitude, String city, Long date, String url) {
        mMagnitude = magnitude;
        mLocation = city;
        mDate = date;
        mUrl = url;
    }

    public String getLocation() {
        return mLocation;
    }

    public Long getDate() {
        return mDate;
    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public String getUrl() {
        return mUrl;
    }
}

