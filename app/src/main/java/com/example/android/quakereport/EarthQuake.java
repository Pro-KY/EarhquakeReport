package com.example.android.quakereport;

public class EarthQuake {
    private String mMagnitude;
    private String mLocation;
    private String mDate;

    public EarthQuake(String magnitude, String city, String date) {
        mMagnitude = magnitude;
        mLocation = city;
        mDate = date;
    }

    public String getmCity() {
        return mLocation;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmMagnitude() {
        return mMagnitude;
    }
}
