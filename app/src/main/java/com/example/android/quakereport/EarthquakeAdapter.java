package com.example.android.quakereport;


import android.content.Context;

import android.support.annotation.NonNull;
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
import java.util.Locale;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Context context, ArrayList<Earthquake> quakeData) {
        super(context, 0, quakeData);
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

        // Format the magnitude to show 1 decimal place
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
            Log.d("topStringValue", distanceSubstring);
            proximityTextView.setText(distanceSubstring);

            String placeSubstring = location.substring(location.lastIndexOf("of")+3);
            Log.d("bottomStringValue", placeSubstring);
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
        dateTextView.setText(currentEarthquake.formatDate(dateObject));
        timeTextView.setText(currentEarthquake.formatTime(dateObject));

        //Locale ukraineLocale = new Locale("ukr", "ua");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy", Locale.US);
        String dateToDisplay = dateFormatter.format(dateObject);

        // set the date into dateTextView
        dateTextView.setText(dateToDisplay);

        return listItemView;
    }
}
