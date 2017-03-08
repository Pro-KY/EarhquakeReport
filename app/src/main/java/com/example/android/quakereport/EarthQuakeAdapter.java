package com.example.android.quakereport;


import android.content.Context;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {

    public EarthQuakeAdapter(Context context, ArrayList<EarthQuake> quakeData) {
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
        EarthQuake currentEarthQuake = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID magnitude_text_view
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude_text_view);
        magnitudeTextView.setText(currentEarthQuake.getMagnitude());

        // Find the TextView in the list_item.xml layout with the ID location_text_view
        TextView locationTextView = (TextView) listItemView.findViewById(R.id.location_text_view);
        locationTextView.setText(currentEarthQuake.getLocation());

        // Find the TextView in the list_item.xml layout with the ID date_text_view
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        dateTextView.setText(currentEarthQuake.getMagnitude());

        return listItemView;
    }
}
