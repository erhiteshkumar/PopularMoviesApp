package com.hiteshkumar.popularmoviesapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.hiteshkumar.popularmoviesapp.fragment.HomeActivityFragment;
import com.hiteshkumar.popularmoviesapp.fragment.MovieDetailFragment;
import com.hiteshkumar.popularmoviesapp.model.MovieBasic;

public class HomeActivity extends AppCompatActivity implements HomeActivityFragment.OnFragmentMovieClickListener, MovieDetailFragment.OnFragmentInteractionListener{

    private static final String DETAILFRAGMENT_TAG = "MOVIETAG";
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new MovieDetailFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(MovieBasic movie) {
//        MovieDetailFragment movieDetailFragment = MovieDetailFragment.newInstance("","");
//        getSupportFragmentManager().beginTransaction().addToBackStack(null).commit();
        Intent intent = new Intent(this, DetailActivity.class);
//                .setData(movie);
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie_detail", movie);
        intent.putExtra("movie_detail",bundle);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
