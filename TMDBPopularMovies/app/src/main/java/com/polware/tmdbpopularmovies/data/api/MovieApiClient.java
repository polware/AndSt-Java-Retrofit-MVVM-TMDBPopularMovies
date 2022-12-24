package com.polware.tmdbpopularmovies.data.api;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.polware.tmdbpopularmovies.data.AppExecutors;
import com.polware.tmdbpopularmovies.data.Constants;
import com.polware.tmdbpopularmovies.data.models.MovieSearchResponse;
import com.polware.tmdbpopularmovies.data.models.PopularMoviesModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

// This class will work as the bridge between Retrofit and LiveData
public class MovieApiClient {
    private static MovieApiClient instance;
    private MutableLiveData<List<PopularMoviesModel>> moviesResultLiveData;
    private MutableLiveData<List<PopularMoviesModel>> popularMoviesLiveData;
    private SearchingMoviesRunnable searchingMoviesRunnable;
    private LoadPopularMoviesRunnable loadPopularMoviesRunnable;

    // Singleton pattern
    public static MovieApiClient getInstance(){
        if (instance == null){
            instance = new MovieApiClient();
        }
        return  instance;
    }

    private MovieApiClient(){
        moviesResultLiveData = new MutableLiveData<>();
        popularMoviesLiveData = new MutableLiveData<>();
    }

    public LiveData<List<PopularMoviesModel>> getMovies(){
        return moviesResultLiveData;
    }

    public LiveData<List<PopularMoviesModel>> getPopularMovies(){
        return popularMoviesLiveData;
    }

    public void searchMoviesByName(String query, int pageNumber) {
        if (searchingMoviesRunnable != null){
            searchingMoviesRunnable = null;
        }
        searchingMoviesRunnable = new SearchingMoviesRunnable(query, pageNumber);

        // Future represents the result of an asynchronous task
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(searchingMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancel the retrofit call
                myHandler.cancel(true);
            }
        }, 4000, TimeUnit.MILLISECONDS);
    }

    // Retrieving data from API by runnable class
    private class SearchingMoviesRunnable implements Runnable {
        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public SearchingMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = responseCallMovies(query, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200){
                    List<PopularMoviesModel> list = new
                            ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if (pageNumber == 1){
                        // Sending data to live data
                        // PostValue: used for background thread
                        moviesResultLiveData.postValue(list);
                    }
                    else{
                        List<PopularMoviesModel> currentMovies = moviesResultLiveData.getValue();
                        currentMovies.addAll(list);
                        moviesResultLiveData.postValue(currentMovies);
                    }
                }
                else{
                    Log.e("ResponseError: ", response.errorBody().toString());
                    moviesResultLiveData.postValue(null);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Search Method (Query)
        private Call<MovieSearchResponse> responseCallMovies(String query, int pageNumber){
            return RetrofitClient.serviceMovieDbApi().searchMovie(Constants.API_KEY, query, pageNumber);
        }

        private void cancelRequest(){
            Log.v("MovieSearch", "Cancelling Search Request" );
            cancelRequest = true;
        }

    }

    public void searchPopularMovies(int pageNumber) {
        if (loadPopularMoviesRunnable != null){
            loadPopularMoviesRunnable = null;
        }
        loadPopularMoviesRunnable = new LoadPopularMoviesRunnable(pageNumber);
        // Future represents the result of an asynchronous task
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(loadPopularMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling retrofit call
                myHandler.cancel(true);
            }
        }, 2000, TimeUnit.MILLISECONDS);
    }

    private class LoadPopularMoviesRunnable implements Runnable {
        private int pageNumber;
        boolean cancelRequest;

        public LoadPopularMoviesRunnable(int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            // Getting the response objects
            try{
                Response response = responseCallAllMovies(pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200){
                    List<PopularMoviesModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if (pageNumber == 1) {
                        // Sending data to live data
                        // PostValue: used for background thread
                        popularMoviesLiveData.postValue(list);
                    }
                    else {
                        List<PopularMoviesModel> currentMovies = popularMoviesLiveData.getValue();
                        currentMovies.addAll(list);
                        popularMoviesLiveData.postValue(currentMovies);
                    }
                }
                else {
                    Log.e("ResponseError: ", response.errorBody().toString());
                    popularMoviesLiveData.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                popularMoviesLiveData.postValue(null);
            }
        }

        private Call<MovieSearchResponse> responseCallAllMovies(int pageNumber){
            return RetrofitClient.serviceMovieDbApi().getPopularMovies(Constants.API_KEY, pageNumber);
        }

        private void cancelRequest(){
            Log.v("MovieLoading", "Cancelling Request" );
            cancelRequest = true;
        }
    }

}
