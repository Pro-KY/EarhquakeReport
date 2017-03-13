package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_week.geojson";
    private static final int EARTHQUAKE_LOADER_ID = 1;

    private EarthquakeAdapter mEarthQuakeAdapter;

    // TextView that is displayed when the list is empty
    private TextView mEmptyTextView;
    ProgressBar loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        mEarthQuakeAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Find a reference to the ListView in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        earthquakeListView.setAdapter(mEarthQuakeAdapter);

        mEmptyTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyTextView);

        // if there is internet connectivity then initialize the loader as usual, otherwise
        // hide loading indicator and display "no internet connection message"
        if(isInternetConnectivity()) {
            // initialize loader, onCreateLoader method will be run automatically
            getSupportLoaderManager().initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            loadingIndicator = (ProgressBar) findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyTextView.setText(R.string.no_inet_connection_text);
        }

        // set click listener to each item in the ListView
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // set an implicit intent with URL
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);

                // extract url from the earthquake object
                String url = mEarthQuakeAdapter.getItem(position).getUrl();
                Log.d("url", url);

                browserIntent.setData(Uri.parse(url));
                startActivity(browserIntent);
            }
        });
    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        Log.d("in", "onCreateLoader");
        return new EarthquakeLoader(EarthquakeActivity.this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakesData) {
        Log.d("in", "onLoadFinished");

        if (earthquakesData != null && !earthquakesData.isEmpty()) {
            // set earthquake data to the adapter
            mEarthQuakeAdapter.setEarthquakesData(earthquakesData);
        }

        loadingIndicator = (ProgressBar) findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyTextView.setText(R.string.no_earthquakes_found_text);
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        Log.d("in", "onLoaderReset");
        // Loader reset, so we can clear out our existing data.
        mEarthQuakeAdapter.clear();
    }

    private boolean isInternetConnectivity() {
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
