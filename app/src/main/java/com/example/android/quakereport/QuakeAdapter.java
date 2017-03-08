package com.example.android.quakereport;


import android.content.Context;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class QuakeAdapter extends ArrayAdapter<Quake> {

    public QuakeAdapter(Context context, ArrayList<Quake> quakeData) {
        super(context, 0, quakeData);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

    }
}
