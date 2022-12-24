package com.polware.tmdbpopularmovies.data.api;

import com.polware.tmdbpopularmovies.data.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();
    private static TMDBApiService tmdbApiService = retrofit.create(TMDBApiService.class);

    public static TMDBApiService serviceMovieDbApi() {
        return tmdbApiService;
    }

}
