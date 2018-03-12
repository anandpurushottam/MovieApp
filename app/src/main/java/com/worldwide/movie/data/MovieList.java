package com.worldwide.movie.data;

import com.worldwide.movie.data.Movie;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieList {

  @SerializedName("page") @Expose private Integer page;
  @SerializedName("total_results") @Expose private Integer totalResults;
  @SerializedName("total_pages") @Expose private Integer totalPages;
  @SerializedName("results") @Expose private List<Movie> movies = null;

  public Integer getPage() {
    return page;
  }

  public Integer getTotalResults() {
    return totalResults;
  }

  public Integer getTotalPages() {
    return totalPages;
  }

  public List<Movie> getMovies() {
    return movies;
  }
}
