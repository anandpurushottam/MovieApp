package com.worldwide.movie.detial;

import com.worldwide.movie.BasePresenter;
import com.worldwide.movie.BaseView;
import com.worldwide.movie.data.Movie;
import com.worldwide.movie.data.Video;
import java.util.List;

/**
 * Created by Anand on 11-03-2018.
 */

public interface MovieDetailContract {
  interface View extends BaseView<Presenter> {
    void setLoadingIndicator(boolean active);

    void showVideoList(List<Video> videos);

    void showLoadingVideoError();

    void showNoVideos();
    Movie getMovie();
  }

  interface Presenter extends BasePresenter {

    void loadVideos(boolean forceUpdate);

    void saveMovie(Movie movie);
  }
}
