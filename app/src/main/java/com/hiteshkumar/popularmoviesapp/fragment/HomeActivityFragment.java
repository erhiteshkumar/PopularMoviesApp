package com.hiteshkumar.popularmoviesapp.fragment;

import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.hiteshkumar.popularmoviesapp.ImageAdapter;
import com.hiteshkumar.popularmoviesapp.R;
import com.hiteshkumar.popularmoviesapp.api.ITMDBApi;
import com.hiteshkumar.popularmoviesapp.model.MovieBasic;
import com.hiteshkumar.popularmoviesapp.model.MoviesResponse;

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


        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
                ((OnFragmentMovieClickListener)getActivity()).onFragmentInteraction(mList.get(position));
            }
        });
        if (mList == null){
            fetchMovieList();
        }else{
            mGridview.setAdapter(new ImageAdapter(getActivity(), mList));
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movie_list", (ArrayList<? extends Parcelable>) mList);
    }

    public interface OnFragmentMovieClickListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(MovieBasic movie);
    }

    private void fetchMovieList(){
        RestAdapter restAdapter;
        restAdapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.base_url)).build();

        ITMDBApi itmdbApi = restAdapter.create(ITMDBApi.class);
        Map<String, String > parameters = new HashMap<>(2);
        parameters.put("sort_by" ,"popularity.desc");
        parameters.put("api_key" ,getResources().getString(R.string.api_key));
        itmdbApi.getPopularMovies(parameters, new Callback<MoviesResponse>() {
            @Override
            public void success(MoviesResponse moviesResponse, Response response) {
                Log.d(TAG, "" + moviesResponse.getResults().size());
                mList = moviesResponse.getResults();
                mGridview.setAdapter(new ImageAdapter(getActivity(), mList));
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
}
