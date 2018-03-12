package com.worldwide.movie.networking;

import com.worldwide.movie.data.MovieList;
import com.worldwide.movie.data.VideoList;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Anand on 09-03-2018.
 */

public interface MoviesService {
  @GET("movie/popular") Observable<MovieList> movieList(@Query("page") Integer page);

  @GET("movie/{movie_id}/videos") Observable<VideoList> videoList(
      @Path("movie_id") Integer movieId);
}
