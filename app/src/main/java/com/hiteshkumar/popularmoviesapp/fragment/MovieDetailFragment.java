package com.hiteshkumar.popularmoviesapp.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hiteshkumar.popularmoviesapp.R;
import com.hiteshkumar.popularmoviesapp.model.MovieBasic;
import com.hiteshkumar.popularmoviesapp.util.Util;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MovieDetailFragment extends Fragment {
    private static final String ARG_PARAM = "movie_detail";
    private MovieBasic mMovie;
    private TextView mMovieTitleTextView;
    private TextView mMovieRatingTextView;
    private TextView mMovieOverviewTextView;
    private TextView mMovieReleaseDateTextView;

    private ImageView mMovieBackdropImageView;
    private ImageView mMoviePosterImageView;

    private OnFragmentInteractionListener mListener;

    public static MovieDetailFragment newInstance(Bundle bundle) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMovieTitleTextView = (TextView)view.findViewById(R.id.movieTitleTextView);
        mMovieRatingTextView = (TextView)view.findViewById(R.id.movieRatingTextView);
        mMovieOverviewTextView = (TextView)view.findViewById(R.id.movieOverviewTextView);
        mMovieReleaseDateTextView = (TextView)view.findViewById(R.id.movieReleaseDateTextView);

        mMovieBackdropImageView = (ImageView)view.findViewById(R.id.movieBackdropImageView);
        mMoviePosterImageView = (ImageView)view.findViewById(R.id.moviePosterImageView);

        mMovieTitleTextView.setText(mMovie.getTitle());
        StringBuilder rating = new StringBuilder(getString(R.string.ratings));
        rating.append(" ");
        rating.append(mMovie.getVoteAverage());
        StringBuilder relaseDate = new StringBuilder(getString(R.string.release_date));
        relaseDate.append(" ");
        relaseDate.append(mMovie.getReleaseDate());
        mMovieReleaseDateTextView.setText(relaseDate);
        mMovieRatingTextView.setText(rating);
        mMovieOverviewTextView.setText(mMovie.getOverview());
        Picasso.with(getActivity()).load(Util.getMoviePosterPath(getActivity(), mMovie)).into(mMoviePosterImageView);

        Picasso.with(getActivity()).load(Util.getMovieBackdropImagePath(getActivity(),mMovie)).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                mMovieBackdropImageView.setImageBitmap(bitmap);
                Palette.generateAsync(bitmap, palette -> {
                    Palette.Swatch vibrant = palette.getVibrantSwatch();
                    if (vibrant != null) {
                        mMovieTitleTextView.setBackgroundColor(vibrant.getTitleTextColor());
                        mMovieTitleTextView.setTextColor(vibrant.getBodyTextColor());
                    }
                });
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                mMovieBackdropImageView.setImageDrawable(getResources().getDrawable(R.drawable.image_not_found));
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                mMovieBackdropImageView.setImageDrawable(getResources().getDrawable(R.drawable.loading_image));
            }
        });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
