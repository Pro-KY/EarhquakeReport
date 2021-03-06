package com.example.android.quakereport;


import android.content.Context;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static android.R.attr.data;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private List<Earthquake> earthquakes = new ArrayList<>();

    public EarthquakeAdapter(Context context, List<Earthquake> earthquakesData) {
        super(context, 0, earthquakesData);
        this.earthquakes = earthquakesData;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item,
                    parent,
                    false
            );
        }

        // Get the object located at this position in the list
        Earthquake currentEarthquake = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID magnitude_text_view
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude_text_view);

        // Set the proper background color on the magnitude circle.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        DecimalFormat formatter = new DecimalFormat("0.0");
        String formattedMagnitude  = formatter.format(currentEarthquake.getMagnitude());

        // Display the magnitude of the current earthquake in the TextView
        magnitudeTextView.setText(formattedMagnitude);

        // get earthquake location from an object
        String location = currentEarthquake.getLocation();

        // displays the proximity of an earthquake
        TextView proximityTextView = (TextView) listItemView.findViewById(R.id.proximity_text_view);
        // displays place of the earthquake
        TextView placeTextView = (TextView) listItemView.findViewById(R.id.place_text_view);

        // if there is proximity in location string, then set distance and place of the earthquake into
        // 2 separate TextViews, otherwise display "Near the" instead of proximity information
        if(location.lastIndexOf("of") != -1) {
            String distanceSubstring = location.substring(0, location.lastIndexOf("of")+2);
            proximityTextView.setText(distanceSubstring);

            String placeSubstring = location.substring(location.lastIndexOf("of")+3);
            placeTextView.setText(placeSubstring);
        } else {
            proximityTextView.setText("Near the");
            placeTextView.setText(location);
        }

        // Find the TextView in the list_item.xml layout with the ID date_text_view
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time_text_view);

        // Format date in milliseconds into human readable format
        long dateInMilliSeconds = currentEarthquake.getDate();
        Date dateObject = new Date(dateInMilliSeconds);

        // set date and time into appropriate TextViews
        String formattedDate = formatDate(dateObject);
        // set the date into dateTextView
        dateTextView.setText(formattedDate);

        String formattedTime = formatTime(dateObject);
        // set the date into timeTextView
        timeTextView.setText(formattedTime);

        return listItemView;
    }

    private int getMagnitudeColor(double magnitude) {
        // convert double magnitude value into integer
        int magnitudeInteger = (int) magnitude;
        int magnitudeColorResourceId;

        switch (magnitudeInteger) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    public void setEarthquakesData(List<Earthquake> earthquakesData) {
        earthquakes.addAll(earthquakesData);
        // notify when underlying data has changed
        notifyDataSetChanged();
    }

    @Nullable
    @Override
    public Earthquake getItem(int position) {
        return earthquakes.get(position);
    }

    // Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
    public String formatDate(Date dateObject) {
        //Locale ukraineLocale = new Locale("ukr", "ua");
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy", Locale.US);
        return dateFormat.format(dateObject);
    }

    // Return the formatted date string (i.e. "4:30 PM") from a Date object.
    public String formatTime(Date dateObject) {
        //Locale ukraineLocale = new Locale("ukr", "ua");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.US);
        return timeFormat.format(dateObject);
    }
}
