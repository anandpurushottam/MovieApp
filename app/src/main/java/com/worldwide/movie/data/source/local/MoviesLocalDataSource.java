package com.worldwide.movie.data.source.local;

import android.support.annotation.NonNull;
import com.worldwide.movie.data.Movie;
import com.worldwide.movie.data.Video;
import com.worldwide.movie.data.source.MoviesDataSource;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

import static com.worldwide.movie.util.NullChecker.requireNonNull;

/**
 * Created by Anand on 09-03-2018.
 */

public class MoviesLocalDataSource implements MoviesDataSource {
  private static volatile MoviesLocalDataSource INSTANCE;

  List<Movie> movies = new ArrayList<>();
  MovieDao movieDao;

  private MoviesLocalDataSource(@NonNull MovieDao movieDao) {
    this.movieDao = movieDao;
  }

  public static MoviesLocalDataSource getInstance(MovieDao movieDao) {
    if (INSTANCE == null) {
      synchronized (MoviesLocalDataSource.class) {
        if (INSTANCE == null) {
          INSTANCE = new MoviesLocalDataSource(movieDao);
        }
      }
    }

    return INSTANCE;
  }

  @Override public void getMovies(@NonNull LoadCallback callback) {

    Observable.just(true)
        .map(id -> movieDao.getMoives())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableObserver<List<Movie>>() {
          @Override public void onNext(List<Movie> movies) {
            if (movies != null && movies.size() > 0) {
              callback.onLoaded(movies);
            } else {
              callback.onDataNotAvailable();
            }
          }

          @Override public void onError(Throwable e) {
            callback.onDataNotAvailable();
          }

          @Override public void onComplete() {

          }
        });
  }

  @Override public void getMovie(@NonNull String movieId, @NonNull GetCallback callback) {
    Observable.just(movieId)
        .map(id -> movieDao.getMovieById(id))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableObserver<Movie>() {
          @Override public void onNext(Movie movie) {
            if (movie != null) {
              callback.onLoaded(movie);
            } else {
              callback.onDataNotAvailable();
            }
          }

          @Override public void onError(Throwable e) {
            callback.onDataNotAvailable();
          }

          @Override public void onComplete() {

          }
        });
  }

  @Override public void getVideos(@NonNull Movie taskId, LoadCallback callback) {

  }

  @Override public void saveMovie(@NonNull Movie movie) {
    requireNonNull(movie);
    Observable.just(movie)
        .map(m -> {
          movieDao.insertTask(m);
          return true;
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(getDisposableObserver());
  }

  @Override public void deleteAll() {
    Observable.just(true)
        .map(s -> {
          movieDao.deleteMovie();
          return s;
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(getDisposableObserver());
  }

  @Override public void deleteMovie(@NonNull String taskId) {
    Observable.just(true)
        .map(s -> {
          movieDao.deleteMovieById(taskId);
          return s;
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(getDisposableObserver());
  }

  public DisposableObserver<Boolean> getDisposableObserver() {
    return new DisposableObserver<Boolean>() {
      @Override public void onNext(Boolean aBoolean) {
        Timber.d("Operation Completed");
      }

      @Override public void onError(Throwable e) {
        Timber.e(e);
      }

      @Override public void onComplete() {

      }
    };
  }
}
