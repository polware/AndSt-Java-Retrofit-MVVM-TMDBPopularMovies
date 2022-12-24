package com.polware.tmdbpopularmovies.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieResponse {
    @SerializedName("results")
    @Expose
    private PopularMoviesModel movie;

    public PopularMoviesModel getMovie() {
        return movie;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movie=" + movie +
                '}';
    }

}
