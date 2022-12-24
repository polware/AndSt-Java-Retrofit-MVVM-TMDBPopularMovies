package com.polware.tmdbpopularmovies.data.api;

import com.polware.tmdbpopularmovies.data.models.MovieSearchResponse;
import com.polware.tmdbpopularmovies.data.models.PopularMoviesModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBApiService {

    //Search for movies
    //https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher
    @GET("search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

    // https://api.themoviedb.org/3/movie/popular?api_key=52a18783ed514602a5facb15a0177e61&language=en-US&page=1
    @GET("movie/popular")
    Call<MovieSearchResponse> getPopularMovies(
            @Query("api_key") String key,
            @Query("page") int page
    );

    // Making Search with ID
    // https://api.themoviedb.org/3/movie/550?api_key=52a18783ed514602a5facb15a0177e61
    // movie_id= 550 is for Fight Club movie
    @GET("movie/{movie_id}?")
    Call<PopularMoviesModel> getMovieById(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

}
