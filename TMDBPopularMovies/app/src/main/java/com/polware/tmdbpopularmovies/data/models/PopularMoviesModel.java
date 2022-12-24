package com.polware.tmdbpopularmovies.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PopularMoviesModel implements Parcelable {
    private String title;
    @SerializedName("poster_path")
    private String poster;
    private String release_date;
    private int movie_id;
    @SerializedName("vote_average")
    private float rating;
    private String overview;

    public PopularMoviesModel(String title, String poster, String release_date,
                              int movie_id, float rating, String overview) {
        this.title = title;
        this.poster = poster;
        this.release_date = release_date;
        this.movie_id = movie_id;
        this.rating = rating;
        this.overview = overview;
    }

    protected PopularMoviesModel(Parcel in) {
        title = in.readString();
        poster = in.readString();
        release_date = in.readString();
        movie_id = in.readInt();
        rating = in.readFloat();
        overview = in.readString();
    }

    public static final Creator<PopularMoviesModel> CREATOR = new Creator<PopularMoviesModel>() {
        @Override
        public PopularMoviesModel createFromParcel(Parcel in) {
            return new PopularMoviesModel(in);
        }

        @Override
        public PopularMoviesModel[] newArray(int size) {
            return new PopularMoviesModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public float getRating() {
        return rating;
    }

    public String getOverview() {
        return overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster);
        parcel.writeString(release_date);
        parcel.writeInt(movie_id);
        parcel.writeFloat(rating);
        parcel.writeString(overview);
    }
}
