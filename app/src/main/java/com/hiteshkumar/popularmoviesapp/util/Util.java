package com.hiteshkumar.popularmoviesapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.hiteshkumar.popularmoviesapp.R;
import com.hiteshkumar.popularmoviesapp.model.MovieBasic;

/**
 * Created by hitesh on 10/06/15.
 */
public class Util {

    private Util(){
        throw new AssertionError();
    }

    public static String getMoviePosterPath(Context context, MovieBasic movieInfo){
        return context.getResources().getString(R.string.image_base_url)+ movieInfo.getPosterPath();
    }

    public static String getMovieBackdropImagePath(Context context, MovieBasic movieInfo){
        return context.getResources().getString(R.string.image_backdrop_base_url)+ movieInfo.getBackdropPath();
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }


    public static boolean isConnected(Context context) {
        NetworkInfo info = Util.getNetworkInfo(context);
        return (info != null && info.isConnected());
    }
}
