package com.polware.tmdbpopularmovies.presentation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.polware.tmdbpopularmovies.data.models.PopularMoviesModel;
import com.polware.tmdbpopularmovies.databinding.ActivityMovieDetailsBinding;

public class MovieDetailsActivity extends AppCompatActivity {
    private ActivityMovieDetailsBinding bindingMovieDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingMovieDetails = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(bindingMovieDetails.getRoot());
        getDataFromIntent();
    }

    private void getDataFromIntent() {
        if (getIntent().hasExtra("movie")) {
            PopularMoviesModel moviesModel = getIntent().getParcelableExtra("movie");
            Log.i("MovieDetail" ,"Id:"+ moviesModel.getMovie_id());
            bindingMovieDetails.textViewTitleDetails.setText(moviesModel.getTitle());
            bindingMovieDetails.textViewOverviewDetails.setText(moviesModel.getOverview());
            bindingMovieDetails.ratingBarDetails.setRating((moviesModel.getRating())/2);
            Glide.with(this).load("https://image.tmdb.org/t/p/w500/"
                            +moviesModel.getPoster()).into(bindingMovieDetails.imageViewDetails);
        }
    }

}