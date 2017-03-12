package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_week.geojson";

    //private ArrayAdapter<Earthquake> mEarthQuakeAdapter;
    private List<Earthquake> mEarthquakes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        new EarthquakeAsyncTask().execute(USGS_REQUEST_URL);
    }

    // first param: url; second: progress - Void; third: Event object
    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>> {
        @Override
        protected List<Earthquake> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            // initialize interface variable, ArrayList<Earthquake> instance
            mEarthquakes = QueryUtils.extractEarthquakesData(urls[0]);

            // Perform the HTTP request for earthquake data and process the response.
            return mEarthquakes;
        }

        @Override
        protected void onPostExecute(List<Earthquake> result) {
            // If there is no result, do nothing.
            if (result == null) {
                return;
            }

            // Update the information displayed to the user.
            updateUi(result);
        }
    }

    // Update the UI with the given earthquake information.
    private void updateUi(List<Earthquake> earthquakes) {
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Initialize EarthquakeAdapter
        ArrayAdapter<Earthquake> mEarthQuakeAdapter = new EarthquakeAdapter(this, earthquakes);

        // Set the adapter on the  EarthquakeActivity ListView
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mEarthQuakeAdapter);

        // set click listener to each item in the ListView
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // set an implicit intent with URL
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);

                // extract url from the earthquake object
                String url = mEarthquakes.get(position).getUrl();
                Log.d("url", url);

                browserIntent.setData(Uri.parse(url));
                startActivity(browserIntent);
            }
        });
    }
}
