package com.worldwide.movie.detial;

import com.worldwide.movie.data.Movie;
import com.worldwide.movie.data.source.MoviesDataSource;
import com.worldwide.movie.data.source.MoviesRepository;
import java.util.List;
import timber.log.Timber;

import static com.worldwide.movie.util.NullChecker.requireNonNull;

/**
 * Created by Anand on 11-03-2018.
 */

public class MovieDetailPresenter implements MovieDetailContract.Presenter {
  private MoviesRepository repository;
  private MovieDetailContract.View view;
  private boolean firstLoad = true;
  private Movie movie;
  private Movie prevMovie;

  public MovieDetailPresenter(MoviesRepository repository, Movie movie,
      MovieDetailContract.View view) {
    this.repository = requireNonNull(repository, "MovieDetailRepository cannot be null");
    this.view = requireNonNull(view, "MovieDetailView cannot be null!");
    this.movie = requireNonNull(movie);
    this.movie = movie;
    this.view.setPresenter(this);
  }

  @Override public void start() {
    loadVideos(false);
  }

  @Override public void loadVideos(boolean forceUpdate) {

    if (firstLoad) {
      view.setLoadingIndicator(true);
      firstLoad = false;
    }

    repository.getVideos(view.getMovie(), new MoviesDataSource.LoadCallback() {

      @Override public void onLoaded(List video) {
        Timber.d("getVideos: " + video.size());

        if (view == null) {
          return;
        }
        view.showVideoList(video);
        view.setLoadingIndicator(false);
      }

      @Override public void onDataNotAvailable() {
        if (view == null) {
          return;
        }

        view.setLoadingIndicator(false);

        view.showLoadingVideoError();
      }
    });
  }

  @Override public void saveMovie(Movie movie) {
    repository.saveMovie(movie);

  }
}
