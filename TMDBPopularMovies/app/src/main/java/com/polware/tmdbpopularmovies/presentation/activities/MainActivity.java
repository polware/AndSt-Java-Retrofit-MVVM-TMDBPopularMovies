package com.polware.tmdbpopularmovies.presentation.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.polware.tmdbpopularmovies.data.Constants;
import com.polware.tmdbpopularmovies.data.api.TMDBApiService;
import com.polware.tmdbpopularmovies.data.api.RetrofitClient;
import com.polware.tmdbpopularmovies.data.models.MovieSearchResponse;
import com.polware.tmdbpopularmovies.data.models.PopularMoviesModel;
import com.polware.tmdbpopularmovies.databinding.ActivityMainBinding;
import com.polware.tmdbpopularmovies.presentation.adapters.MoviesAdapter;
import com.polware.tmdbpopularmovies.presentation.adapters.OnMovieListener;
import com.polware.tmdbpopularmovies.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMovieListener {
    private ActivityMainBinding bindingMain;
    private MoviesViewModel moviesViewModel;
    private MoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingMain = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bindingMain.getRoot());
        setSupportActionBar(bindingMain.toolbar);

        moviesViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);
        moviesViewModel.searchPopularMovies(1);
        setupRecyclerView();
        ObservePopularMovies();
        ObserveSearchResults();
        setupSearchView();
    }

    private void ObservePopularMovies(){
        moviesViewModel.getPopularMovies().observe(this, new Observer<List<PopularMoviesModel>>() {
            @Override
            public void onChanged(List<PopularMoviesModel> popularMoviesModels) {
                // Observing for any data change
                if (popularMoviesModels != null){
                    for (PopularMoviesModel popularMoviesModel: popularMoviesModels){
                        Log.i("ObservePopular: ", popularMoviesModel.toString());
                        adapter.setMoviesList(popularMoviesModels);
                    }
                }
            }
        });
    }

    private void ObserveSearchResults() {
        moviesViewModel.getMovies().observe(this, new Observer<List<PopularMoviesModel>>() {
            @Override
            public void onChanged(List<PopularMoviesModel> resultSearch) {
                if (resultSearch != null) {
                    for (PopularMoviesModel popularMoviesModel: resultSearch) {
                        Log.i("ObserveSearch: ", popularMoviesModel.getTitle());
                        adapter.setMoviesList(resultSearch);
                    }
                }
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new MoviesAdapter(this);
        bindingMain.recyclerViewMain.setAdapter(adapter);
        bindingMain.recyclerViewMain.setLayoutManager(new
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        bindingMain.recyclerViewMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollHorizontally(1)){
                    // Display the next results page of Api
                    moviesViewModel.searchNextPage();
                }
            }
        });
    }

    private void setupSearchView() {
        final SearchView searchView = bindingMain.searchView;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                moviesViewModel.searchMovieByName(query, 1);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Capture the event when clicking on the close/clear icon
        View closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery("", false); // reset Query text to be empty without submition
                searchView.setIconified(true); // Replace the x icon with the search icon
                // Reload the Activity
                finish();
                startActivity(getIntent());
            }
        });
    }

    @Override
    public void onMovieClick(int position) {
        //Toast.makeText(this, "Movie position: "+position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movie", adapter.getSelectedMovie(position));
        startActivity(intent);
    }

}