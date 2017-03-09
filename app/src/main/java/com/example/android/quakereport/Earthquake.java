package com.example.android.quakereport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Earthquake {
    private String mMagnitude;
    private String mLocation;
    private Long mDate;
    //Locale ukraineLocale = new Locale("ukr", "ua");

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

    // Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
    public String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy", Locale.US);
        return dateFormat.format(dateObject);
    }

    // Return the formatted date string (i.e. "4:30 PM") from a Date object.
    public String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.US);
        return timeFormat.format(dateObject);
    }
}
