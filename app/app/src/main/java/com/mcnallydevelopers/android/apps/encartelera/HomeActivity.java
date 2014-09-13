package com.mcnallydevelopers.android.apps.encartelera;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.AlphaInAnimationAdapter;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


public class HomeActivity extends ActionBarActivity {

    /**
     * API Url
     */
    private static final String API = "http://movies.apinic.org/v1/movie/all";

    private Helper helper = null;

    private AdView adView;
    private AdRequest adRequest;
    private String deviceid;


    private static final String AD_UNIT_ID = "ca-app-pub-2015513932539714/1102672624";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        helper = new Helper(this);

        if(helper.isConnected()){
            getMovies();

            // admob
            adMobInit();
        }
        else{
            Toast.makeText(getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_LONG).show();
            finish();
        }

    }

    private void adMobInit() {
        // Create an ad.
        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(AD_UNIT_ID);

        // Add the AdView to the view hierarchy. The view will have no size
        // until the ad is loaded.
        LinearLayout layout = (LinearLayout) findViewById(R.id.ad);
        layout.removeAllViews();

        if (helper.isConnected()) {
            layout.setVisibility(View.VISIBLE);
            layout.addView(adView);

            final TelephonyManager tm = (TelephonyManager) getBaseContext()
                    .getSystemService(Context.TELEPHONY_SERVICE);

            deviceid = tm.getDeviceId();

            // Create an ad request. Check logcat output for the hashed device
            // ID to
            // get test ads on a physical device.
            adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice(deviceid).build();

            // Start loading the ad in the background.
            adView.loadAd(adRequest);
        } else {
            layout.setVisibility(View.GONE);
        }
    }

    private void getMovies() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(API, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                setProgressBarIndeterminateVisibility(Boolean.TRUE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                if (response.length() > 0) {

                    MoviesList[] moviesList = new MoviesList[response.length()];

                    for (int i = 0; i < response.length(); i++) {

                        try {

                            JSONObject movie = response.getJSONObject(i);

                            moviesList[i] = new MoviesList();
                            moviesList[i].setName(movie.getString("name"));
                            moviesList[i].setFront(movie.getString("front"));
                            moviesList[i].setSynopsis(movie.getString("synopsis"));
                            moviesList[i].setGenres(movie.getString("genres"));
                            moviesList[i].setClasification(movie.getString("clasification"));
                            moviesList[i].setDuration(movie.getString("duration"));

                        } catch (JSONException e) {

                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                        }

                    }

                    ListView listView = (ListView) findViewById(R.id.listview_movies);

                    MoviesAdapter adapter = new MoviesAdapter(getApplicationContext(), moviesList);
                    AnimationAdapter animAdapter = new AlphaInAnimationAdapter(adapter);
                    animAdapter.setAbsListView(listView);
                    listView.setAdapter(animAdapter);
                    adapter.addAll(moviesList);

                }
                setProgressBarIndeterminateVisibility(Boolean.FALSE);

            }

        });
    }


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */
}
