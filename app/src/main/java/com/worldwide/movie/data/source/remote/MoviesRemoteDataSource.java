package com.worldwide.movie.data.source.remote;

import android.support.annotation.NonNull;
import com.worldwide.movie.data.Movie;
import com.worldwide.movie.data.MovieList;
import com.worldwide.movie.data.VideoList;
import com.worldwide.movie.data.source.MoviesDataSource;
import com.worldwide.movie.networking.MoviesService;
import com.worldwide.movie.networking.NetworkModule;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

/**
 * Created by Anand on 09-03-2018.
 */

public class MoviesRemoteDataSource implements MoviesDataSource {
  private static volatile MoviesRemoteDataSource INSTANCE;

  List<Movie> movies = new ArrayList<>();

  public static MoviesRemoteDataSource getInstance() {
    if (INSTANCE == null) {
      synchronized (MoviesRemoteDataSource.class) {
        if (INSTANCE == null) {
          INSTANCE = new MoviesRemoteDataSource();
        }
      }
    }

    return INSTANCE;
  }

  @Override public void getMovies(@NonNull LoadCallback callback) {
    Observable<MovieList> listObservable =
        NetworkModule.getInstance().create(MoviesService.class).movieList(1);
    listObservable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(movieList -> {
          if (movieList.getMovies() != null && movieList.getMovies().size() > 0) {
            callback.onLoaded(movieList.getMovies());
          } else {
            callback.onDataNotAvailable();
          }
        }, error -> {
          Timber.e(error);
          callback.onDataNotAvailable();
        });
  }

  @Override public void getMovie(@NonNull String taskId, @NonNull GetCallback callback) {

  }

  @Override public void getVideos(@NonNull Movie taskId, LoadCallback callback) {
    Observable<VideoList> listObservable =
        NetworkModule.getInstance().create(MoviesService.class).videoList(taskId.getId());
    listObservable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(videoList -> {
          if (videoList.getVideos() != null && videoList.getVideos().size() > 0) {
            callback.onLoaded(videoList.getVideos());
          } else {
            callback.onDataNotAvailable();
          }
        }, error -> callback.onDataNotAvailable());
  }

  @Override public void saveMovie(@NonNull Movie task) {

  }

  @Override public void deleteAll() {

  }

  @Override public void deleteMovie(@NonNull String taskId) {

  }
}
