package com.example.android.quakereport;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    private String mUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        return QueryUtils.extractEarthquakesData(mUrl);
    }
}
