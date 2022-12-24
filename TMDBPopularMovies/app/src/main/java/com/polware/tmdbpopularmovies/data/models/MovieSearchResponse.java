package com.polware.tmdbpopularmovies.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieSearchResponse {

    @SerializedName("total_results")
    @Expose()
    private int total_count;

    @SerializedName("results")
    @Expose()
    private List<PopularMoviesModel> popularMovies;

    public int getTotal_count(){
        return total_count;
    }

    public List<PopularMoviesModel> getMovies(){
        return popularMovies;
    }

    @Override
    public String toString() {
        return "MovieSearchResponse{" +
                "total_count=" + total_count +
                ", popularMovies=" + popularMovies +
                '}';
    }
}
