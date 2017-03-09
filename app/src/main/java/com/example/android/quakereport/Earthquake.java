package com.example.android.quakereport;

public class Earthquake {
    private String mMagnitude;
    private String mLocation;
    private Long mDate;

    public Earthquake(String magnitude, String city, Long date) {
        mMagnitude = magnitude;
        mLocation = city;
        mDate = date;
    }

    public String getLocation() {
        return mLocation;
    }

    public Long getDate() {
        return mDate;
    }

    public String getMagnitude() {
        return mMagnitude;
    }
}
