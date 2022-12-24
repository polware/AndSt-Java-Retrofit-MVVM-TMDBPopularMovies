package com.polware.tmdbpopularmovies.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.polware.tmdbpopularmovies.data.models.PopularMoviesModel;

import java.util.List;

public class MoviesViewModel extends ViewModel {
    private MoviesRepository moviesRepository;

    public MoviesViewModel() {
        moviesRepository = MoviesRepository.getInstance();
    }

    public LiveData<List<PopularMoviesModel>> getMovies(){
        return moviesRepository.getMovies();
    }

    public void searchMovieByName(String query, int pageNumber){
        moviesRepository.searchMovieByName(query, pageNumber);
    }

    public void searchNextPage(){
        moviesRepository.searchNextPage();
    }

    public LiveData<List<PopularMoviesModel>> getPopularMovies(){
        return moviesRepository.getPopularMovies();
    }

    public void searchPopularMovies(int pageNumber){
        moviesRepository.searchPopularMovies(pageNumber);
    }

}