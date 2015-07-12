package com.hiteshkumar.popularmoviesapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.GridView;

import com.hiteshkumar.popularmoviesapp.model.MovieBasic;
import com.hiteshkumar.popularmoviesapp.util.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hitesh on 08/06/15.
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    List<MovieBasic> list;
    public ImageAdapter(Context c, List<MovieBasic> list) {
        mContext = c;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext).load(Util.getMoviePosterPath(mContext,list.get(position))).placeholder(R.drawable.loading_thumbnail).error(R.drawable.not_available).into(imageView);
        return imageView;
    }
}
