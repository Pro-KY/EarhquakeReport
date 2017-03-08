package com.example.android.quakereport;

public class Quake {
    private String mMagnitude;
    private String mCity;
    private String mDate;

    public Quake(String magnitude, String city, String date) {
        mMagnitude = magnitude;
        mCity = city;
        mDate = date;
    }

    public String getmCity() {
        return mCity;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmMagnitude() {
        return mMagnitude;
    }
}
