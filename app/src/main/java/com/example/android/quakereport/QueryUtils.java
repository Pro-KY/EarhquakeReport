package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static android.R.attr.data;
import static com.example.android.quakereport.EarthquakeActivity.LOG_TAG;

public class QueryUtils {

    private static String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {}

    // Return the list of earthquakes from json string
    public static ArrayList<Earthquake> extractEarthquakesData(String requestUrl) {

        Log.d("in", "extractEarthquakesData");
        // 1. Create url
        URL url = createURL(requestUrl);

        // 2. perform http request to the server and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // 3. Extract relevant fields from the JSON response and create
        // an arrayList of earthquake objects
        return extractFeatureFromJson(jsonResponse);
    }

    // Returns new URL object from the given URL
    private static URL createURL(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    // Make an HTTP request to the given URL and return a String as the response.
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            Log.d("http code", String.valueOf(urlConnection.getResponseCode()));
            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    //Convert the nputStream into a String which contains the
    // whole JSON response from the server.
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    // Return a list of earthquakes objects from the input JSON string
    private static ArrayList<Earthquake> extractFeatureFromJson(String earthquakeJSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }

        try {
            // Create an empty ArrayList that we can start adding earthquakes to
            ArrayList<Earthquake> earthquakes = new ArrayList<>();

            final String FEATURES = "features";
            final String PROPERTIES = "properties";
            final String MAGNITUDE = "mag";
            final String LOCATION = "place";
            final String TIME = "time";
            final String URL = "url";

            Double magnitude;
            String location;
            Long date;
            String url;

            // build up a list of Earthquake objects with the corresponding data.
            // get the root json object
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);

            // get number of json objects in the json features array
            // get json array "features"
            JSONArray featuresArray = baseJsonResponse.getJSONArray(FEATURES);

            for(int i=0; i<featuresArray.length(); i++) {
                // get i-th object from the array
                JSONObject featuresArrayObject = featuresArray.getJSONObject(i);
                // get json "properties" object from featuresArray elements
                JSONObject propertiesObject = featuresArrayObject.getJSONObject(PROPERTIES);

                // get magnitude
                magnitude = propertiesObject.getDouble(MAGNITUDE);
                // get location
                location = propertiesObject.getString(LOCATION);
                // get data value
                date = propertiesObject.getLong(TIME);
                // get url
                url = propertiesObject.getString(URL);

                // add retrieved data from the json to earthquakes arrayList
                earthquakes.add(new Earthquake(magnitude, location, date, url));
            }

            return earthquakes;

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return null;
    }
}
