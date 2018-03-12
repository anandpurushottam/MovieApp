package com.worldwide.movie.movie;

import com.worldwide.movie.BasePresenter;
import com.worldwide.movie.BaseView;
import com.worldwide.movie.data.Movie;
import java.util.List;

/**
 * Created by Anand on 09-03-2018.
 */

public interface MovieContract {
  interface View extends BaseView<Presenter> {
    void setLoadingIndicator(boolean active);

    void showMovieList(List<Movie> movies);

    void showLoadingMovieError();

    void showNoMovies();
  }

  interface Presenter extends BasePresenter {

    void loadMovies(boolean forceUpdate);

  }
}
