package com.worldwide.movie.favourite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.worldwide.movie.R;
import com.worldwide.movie.data.Movie;
import com.worldwide.movie.data.source.MoviesDataSource;
import com.worldwide.movie.data.source.local.MovieDatabase;
import com.worldwide.movie.data.source.local.MoviesLocalDataSource;
import com.worldwide.movie.movie.MovieClickListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anand on 11-03-2018.
 */

public class OfflineMovieListActivity extends AppCompatActivity implements MovieClickListener {
  private FavouriteMovieAdater movieAdapter;
  private ArrayList<Movie> movies;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fav_movie_list);

    RecyclerView recyclerVie = findViewById(R.id.recyclerView);
    recyclerVie.setLayoutManager(new LinearLayoutManager(this));
    MovieDatabase movieDatabase = MovieDatabase.getInstance(this);

    movies = new ArrayList<Movie>(0);
    movieAdapter = new FavouriteMovieAdater(movies, this);
    recyclerVie.setAdapter(movieAdapter);

    MoviesLocalDataSource moviesLocalDataSource =
        MoviesLocalDataSource.getInstance(movieDatabase.movieDao());

    moviesLocalDataSource.getMovies(new MoviesDataSource.LoadCallback() {
      @Override public void onLoaded(List movies) {
        movieAdapter.replaceData(movies);
        movieAdapter.notifyDataSetChanged();
      }

      @Override public void onDataNotAvailable() {

      }
    });
  }

  @Override public void onMovieSelected(Movie clickedTask) {

  }
  public static Intent createIntent(Context context) {
    return new Intent(context, OfflineMovieListActivity.class);
  }
}
