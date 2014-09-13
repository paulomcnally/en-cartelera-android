package com.mcnallydevelopers.android.apps.encartelera;

import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.AlphaInAnimationAdapter;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HomeActivity extends ActionBarActivity {

    /**
     * API Url
     */
    private static final String API = "http://movies.apinic.org/v1/movie/all";

    private Helper helper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        helper = new Helper(this);

        if(helper.isConnected()){
            getMovies();
        }
        else{
            Toast.makeText(getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_LONG).show();
            finish();
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
