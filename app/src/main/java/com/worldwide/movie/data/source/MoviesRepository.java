/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.worldwide.movie.data.source;

import android.support.annotation.NonNull;
import com.worldwide.movie.data.Movie;
import com.worldwide.movie.data.source.local.MoviesLocalDataSource;
import com.worldwide.movie.data.source.remote.MoviesRemoteDataSource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class MoviesRepository implements MoviesDataSource {

  private static MoviesRepository INSTANCE = null;

  private final MoviesRemoteDataSource mTasksRemoteDataSource;

  private final MoviesLocalDataSource mTasksLocalDataSource;

  Map<Integer, Movie> cachedMovies;

  // Prevent direct instantiation.
  private MoviesRepository(@NonNull MoviesRemoteDataSource tasksRemoteDataSource,
      @NonNull MoviesLocalDataSource tasksLocalDataSource) {
    mTasksRemoteDataSource = checkNotNull(tasksRemoteDataSource);
    mTasksLocalDataSource = checkNotNull(tasksLocalDataSource);
  }

  public static MoviesRepository getInstance(MoviesRemoteDataSource moviesRemoteDataSource,
      MoviesLocalDataSource moviesLocalDataSource) {
    if (INSTANCE == null) {
      INSTANCE = new MoviesRepository(moviesRemoteDataSource, moviesLocalDataSource);
    }
    return INSTANCE;
  }

  public static void destroyInstance() {
    INSTANCE = null;
  }

  @Override public void getMovies(@NonNull final LoadCallback callback) {
    checkNotNull(callback);

    if (cachedMovies != null) {
      callback.onLoaded(new ArrayList<>(cachedMovies.values()));
      return;
    }else {
      cachedMovies = new LinkedHashMap<>();
    }

    mTasksRemoteDataSource.getMovies(new LoadCallback() {

      @Override public void onLoaded(List movies) {
        callback.onLoaded(new ArrayList<>(movies));
        addToMemoryCache(movies);
      }

      @Override public void onDataNotAvailable() {
        mTasksLocalDataSource.getMovies(callback);
      }
    });
  }

  private void addToMemoryCache(List movies) {

    ArrayList<Movie> list = new ArrayList<>(movies);

    for (Movie movie : list) {
      cachedMovies.put(movie.getId(), movie);
    }
  }

  @Override public void saveMovie(@NonNull Movie movie) {
    checkNotNull(movie);
    mTasksRemoteDataSource.saveMovie(movie);
    mTasksLocalDataSource.saveMovie(movie);
  }

  @Override public void deleteAll() {

  }

  @Override
  public void getMovie(@NonNull final String taskId, @NonNull final GetCallback callback) {
    checkNotNull(taskId);
    checkNotNull(callback);

    mTasksLocalDataSource.getMovie(taskId, new GetCallback() {

      @Override public void onLoaded(Object o) {
        Movie movie = (Movie) o;
        callback.onLoaded(movie);
      }

      @Override public void onDataNotAvailable() {
        mTasksRemoteDataSource.getMovie(taskId, new GetCallback() {

          @Override public void onLoaded(Object o) {
            Movie movie = (Movie) o;
            callback.onLoaded(movie);
          }

          @Override public void onDataNotAvailable() {
            callback.onDataNotAvailable();
          }
        });
      }
    });
  }

  @Override public void getVideos(@NonNull Movie taskId, LoadCallback callback) {
    mTasksRemoteDataSource.getVideos(taskId, new LoadCallback() {

      @Override public void onLoaded(List video) {

        callback.onLoaded(new ArrayList<>(video));
      }

      @Override public void onDataNotAvailable() {
        callback.onDataNotAvailable();
      }
    });
  }

  @Override public void deleteMovie(@NonNull String taskId) {
    mTasksRemoteDataSource.deleteMovie(checkNotNull(taskId));
    mTasksLocalDataSource.deleteMovie(checkNotNull(taskId));
  }

  private void getTasksFromRemoteDataSource(@NonNull final LoadCallback callback) {
    mTasksRemoteDataSource.getMovies(new LoadCallback() {

      @Override public void onLoaded(List video) {

        callback.onLoaded(new ArrayList<>(video));
      }

      @Override public void onDataNotAvailable() {
        callback.onDataNotAvailable();
      }
    });
  }
}
