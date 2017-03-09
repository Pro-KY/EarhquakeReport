package com.example.android.quakereport;


import android.content.Context;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.example.android.quakereport.R.id.place_text_view;

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
        magnitudeTextView.setText(currentEarthquake.getMagnitude());

        // get earthquake location from an object
        String location = currentEarthquake.getLocation();

        TextView distanceTextView = (TextView) listItemView.findViewById(R.id.distance_text_view);
        TextView placeTextView = (TextView) listItemView.findViewById(place_text_view);

        // if there is distance in location string, then set distance and place of the earthquake into
        // separate TextViews, otherwise display only place value
        if(location.lastIndexOf("of") != -1) {
            String distanceSubstring = location.substring(0, location.lastIndexOf("of")+2);
            Log.d("topStringValue", distanceSubstring);
            distanceTextView.setText(distanceSubstring);

            String placeSubstring = location.substring(location.lastIndexOf("of")+3);
            Log.d("bottomStringValue", placeSubstring);
            placeTextView.setText(placeSubstring);
        } else {
            distanceTextView.setText(location);
            placeTextView.setVisibility(View.GONE);
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

        return listItemView;
    }
}
