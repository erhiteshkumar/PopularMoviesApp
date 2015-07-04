package com.hiteshkumar.popularmoviesapp.util;

import android.content.Context;

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
}
