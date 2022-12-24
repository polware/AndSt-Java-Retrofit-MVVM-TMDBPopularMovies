package com.polware.tmdbpopularmovies.presentation.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.polware.tmdbpopularmovies.data.models.PopularMoviesModel;
import com.polware.tmdbpopularmovies.databinding.MovieListItemBinding;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder>{
    private List<PopularMoviesModel> moviesModelList;
    private OnMovieListener onMovieListener;
    private ImageView movieImage;
    private RatingBar ratingBar;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private MovieListItemBinding movieListItemBinding;
        public MyViewHolder(MovieListItemBinding binding) {
            super(binding.getRoot());
            this.movieListItemBinding = binding;
        }
    }

    public MoviesAdapter(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(MovieListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        movieImage = holder.movieListItemBinding.movieImage;
        ratingBar = holder.movieListItemBinding.ratingBarMovie;
        Glide.with(holder.itemView.getContext())
                .load( "https://image.tmdb.org/t/p/w500/"+moviesModelList.get(position)
                        .getPoster()).into(movieImage);
        ratingBar.setRating((moviesModelList.get(position).getRating())/2);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMovieListener.onMovieClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (moviesModelList != null){
            return moviesModelList.size();
        }
        return 0;
    }

    public void setMoviesList(List<PopularMoviesModel> mMovies) {
        moviesModelList = mMovies;
        notifyDataSetChanged();
    }

    // Getting the id of the movie clicked
    public PopularMoviesModel getSelectedMovie(int position){
        if (moviesModelList != null){
            if (moviesModelList.size() > 0){
                return moviesModelList.get(position);
            }
        }
        return null;
    }

}
