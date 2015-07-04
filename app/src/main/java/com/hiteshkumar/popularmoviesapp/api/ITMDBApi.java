package com.hiteshkumar.popularmoviesapp.api;


import com.hiteshkumar.popularmoviesapp.model.MoviesResponse;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;

/**
 * Created by hitesh on 08/06/15.
 */
public interface ITMDBApi {

    @GET("/movie")
    public void getPopularMovies(@QueryMap Map<String, String> options, Callback<MoviesResponse> response);

}
