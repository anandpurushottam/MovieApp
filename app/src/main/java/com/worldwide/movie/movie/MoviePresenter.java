package com.worldwide.movie.movie;

import android.support.annotation.NonNull;
import com.worldwide.movie.data.source.MoviesDataSource;
import com.worldwide.movie.data.source.MoviesRepository;
import java.util.List;

import static com.worldwide.movie.util.NullChecker.requireNonNull;

/**
 * Created by Anand on 09-03-2018.
 */

public class MoviePresenter implements MovieContract.Presenter {
  private MoviesRepository repository;
  private MovieContract.View view;
  private boolean firstLoad = true;

  public MoviePresenter(@NonNull MoviesRepository moviesRepository,
      @NonNull MovieContract.View moviesView) {

    this.repository = requireNonNull(moviesRepository, "MoviesRepository cannot be null");
    this.view = requireNonNull(moviesView, "MovieView cannot be null!");
    this.view.setPresenter(this);
  }

  @Override public void start() {
    loadMovies(false);
  }

  @Override public void loadMovies(boolean forceUpdate) {

    view.setLoadingIndicator(true);

    repository.getMovies(new MoviesDataSource.LoadCallback() {

      @Override public void onLoaded(List movies) {
        view.showMovieList(movies);
        if (view == null) {
          return;
        }

        view.setLoadingIndicator(false);
      }

      @Override public void onDataNotAvailable() {
        if (view == null) {
          return;
        }
        view.setLoadingIndicator(false);
        view.showLoadingMovieError();
      }
    });
  }
}
