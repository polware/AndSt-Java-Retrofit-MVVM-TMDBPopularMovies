package com.polware.tmdbpopularmovies.viewmodel;

import androidx.lifecycle.LiveData;

import com.polware.tmdbpopularmovies.data.api.MovieApiClient;
import com.polware.tmdbpopularmovies.data.models.PopularMoviesModel;

import java.util.List;

public class MoviesRepository {
    private static MoviesRepository instance;
    private MovieApiClient movieApiClient;
    private String mQuery;
    private int mPageNumber;

    // Singleton pattern
    public static MoviesRepository getInstance() {
        if (instance == null) {
            instance = new MoviesRepository();
        }
        return instance;
    }

    private MoviesRepository() {
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<PopularMoviesModel>> getMovies(){
        return movieApiClient.getMovies();
    }

    public void searchMovieByName(String query, int pageNumber){
        mQuery = query;
        mPageNumber = pageNumber;
        movieApiClient.searchMoviesByName(query, pageNumber);
    }

    public void searchNextPage(){
        searchMovieByName(mQuery, mPageNumber+1);
    }

    public LiveData<List<PopularMoviesModel>> getPopularMovies(){
        return movieApiClient.getPopularMovies();
    }

    public void searchPopularMovies(int pageNumber) {
        mPageNumber = pageNumber;
        movieApiClient.searchPopularMovies(pageNumber);
    }

}
