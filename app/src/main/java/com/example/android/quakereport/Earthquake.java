package com.example.android.quakereport;

public class Earthquake {
    private String mMagnitude;
    private String mLocation;
    private String mDate;

    public Earthquake(String magnitude, String city, String date) {
        mMagnitude = magnitude;
        mLocation = city;
        mDate = date;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getDate() {
        return mDate;
    }

    public String getMagnitude() {
        return mMagnitude;
    }
}
