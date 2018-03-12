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
import com.worldwide.movie.data.Video;
import java.util.List;


public interface MoviesDataSource {

  interface LoadCallback<T> {

    void onLoaded(List<T> movies);

    void onDataNotAvailable();
  }

  interface GetCallback<T> {

    void onLoaded(T t);

    void onDataNotAvailable();
  }

  void getMovies(@NonNull LoadCallback callback);

  void getMovie(@NonNull String taskId, @NonNull GetCallback callback);


  void getVideos(@NonNull Movie taskId, LoadCallback callback);

  void saveMovie(@NonNull Movie movie);

  void deleteAll();


  void deleteMovie(@NonNull String taskId);
}
