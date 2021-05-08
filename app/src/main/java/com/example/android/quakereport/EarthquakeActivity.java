/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private EarthquakeAdapter mAdapter;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // Create a fake list of earthquake locations.
        //  ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();

        // Find a reference to the {@link ListView} in the layout
        if (networkInfo != null && networkInfo.isConnected()) {
            ListView earthquakeListView = (ListView) findViewById(R.id.list);
            mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());
            mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
            earthquakeListView.setEmptyView(mEmptyStateTextView);
            // Create a new {@link ArrayAdapter} of earthquakes
            // final EarthquakeAdapter adapter = new EarthquakeAdapter(this,earthquakes);

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            earthquakeListView.setAdapter(mAdapter);
            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Earthquake currentEarthquake = mAdapter.getItem(i);
                    Uri earthquakeUri = Uri.parse(currentEarthquake.getmUrl());
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                    startActivity(websiteIntent);

                }
            });
        }
        /*EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);*/
        }

        @Override
        public Loader<List<Earthquake>> onCreateLoader ( int i, Bundle bundle){
            return new EarthquakeLoader(this, USGS_REQUEST_URL);
        }

        @Override
        public void onLoadFinished
        (Loader < List < Earthquake >> loader, List < Earthquake > earthquakes){
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_earthquakes);
            mAdapter.clear();
            if (earthquakes != null && !earthquakes.isEmpty()) {
                mAdapter.addAll(earthquakes);

            }
        }

        @Override
        public void onLoaderReset (Loader < List < Earthquake >> loader) {
            mAdapter.clear();
        }

    /*private class EarthquakeAsyncTask extends AsyncTask<String,Void,List<Earthquake>> {
        @Override
                protected List<Earthquake> doInBackground(String... urls){
            if(urls.length<1||urls[0]==null){
                return null;
            }
            List<Earthquake> result =QueryUtils.fetchEarthquakeData(urls[0]);
            return result;
        }
        @Override
                protected void onPostExecute(List<Earthquake> data){
            mAdapter.clear();
            if(data!=null && !data.isEmpty()){
                mAdapter.addAll(data);
            }

        }

    }*/

    }
