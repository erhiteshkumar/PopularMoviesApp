package com.hiteshkumar.popularmoviesapp.fragment;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hiteshkumar.popularmoviesapp.ImageAdapter;
import com.hiteshkumar.popularmoviesapp.R;
import com.hiteshkumar.popularmoviesapp.api.ITMDBApi;
import com.hiteshkumar.popularmoviesapp.model.MovieBasic;
import com.hiteshkumar.popularmoviesapp.model.MoviesResponse;
import com.hiteshkumar.popularmoviesapp.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment {

    private GridView mGridview;
    private final String MOVIE_BUNDLE_KEY = "movie_list";
    private static  final String TAG = HomeActivityFragment.class.getName();
    private List<MovieBasic> mList;
    private String mSortingPreference;
    private LinearLayout mErrorLayout;
    private TextView mErrorTextView;
    private Button mRetryButton;
    private ProgressBar mProgressBar;
    public HomeActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
        {
            mList = (List<MovieBasic>)savedInstanceState.get(MOVIE_BUNDLE_KEY);
        }
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGridview = (GridView) view.findViewById(R.id.gridview);
        mErrorLayout = (LinearLayout) view.findViewById(R.id.errorLayout);
        mErrorTextView = (TextView)view.findViewById(R.id.errorTextView);
        mRetryButton = (Button) view.findViewById(R.id.retryButton);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            mGridview.setNumColumns(5);
        }
        mGridview.setOnItemClickListener((parent, view1, position, id) -> ((OnFragmentMovieClickListener) getActivity()).onFragmentInteraction(mList.get(position)));
        if (mList == null){
            connectAndFetch();

        }else{
            mGridview.setAdapter(new ImageAdapter(getActivity(), mList));
        }

        mRetryButton.setOnClickListener(v -> {
            mErrorLayout.setVisibility(View.GONE);
            connectAndFetch();
        });
    }

    private void connectAndFetch() {
        if(Util.isConnected(getActivity())){
            mProgressBar.setVisibility(View.VISIBLE);
            mGridview.setVisibility(View.VISIBLE);
            fetchMovieList();
        }else {
            mProgressBar.setVisibility(View.GONE);
            mGridview.setVisibility(View.GONE);
            mErrorTextView.setText(getString(R.string.no_internet));
            mErrorLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movie_list", (ArrayList<? extends Parcelable>) mList);
    }

    public interface OnFragmentMovieClickListener {
        public void onFragmentInteraction(MovieBasic movie);
    }

    private int getTypeOfStringTitle(String title) {
        int typeOfTitle;
        switch (title) {
            case "popularity.desc":
                typeOfTitle = R.string.popular_movies_fragment_title;
                break;
            case "vote_average.desc":
                typeOfTitle = R.string.popular_movies_fragment_title_by_ratings;
                break;
            case "revenue.desc":
                typeOfTitle = R.string.popular_movies_fragment_title_by_revenue;
                break;
            default:
                typeOfTitle = R.string.popular_movies_fragment_title;
        }
        return typeOfTitle;
    }


    private void fetchMovieList(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mSortingPreference = sharedPref.getString(getString(R.string.prefrence_sort_title), getString(R.string.prefrence_default_value));
        RestAdapter restAdapter;
        restAdapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.base_url)).build();

        ITMDBApi itmdbApi = restAdapter.create(ITMDBApi.class);
        Map<String, String > parameters = new HashMap<>(2);
        parameters.put("sort_by" ,mSortingPreference);
        parameters.put("api_key" ,getResources().getString(R.string.api_key));
        itmdbApi.getPopularMovies(parameters, new Callback<MoviesResponse>() {
            @Override
            public void success(MoviesResponse moviesResponse, Response response) {
                mProgressBar.setVisibility(View.GONE);
                Log.d(TAG, "" + moviesResponse.getResults().size());
                mList = moviesResponse.getResults();
                mGridview.setAdapter(new ImageAdapter(getActivity(), mList));
                getActivity().setTitle(getTypeOfStringTitle(mSortingPreference));
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                mGridview.setVisibility(View.GONE);
                mErrorLayout.setVisibility(View.VISIBLE);
                mErrorTextView.setText(getString(R.string.server_error));
            }
        });
    }

}
