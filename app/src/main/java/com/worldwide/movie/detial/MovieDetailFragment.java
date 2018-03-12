package com.worldwide.movie.detial;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.worldwide.movie.R;
import com.worldwide.movie.data.Movie;
import com.worldwide.movie.data.Video;
import com.worldwide.movie.data.source.MoviesRepository;
import com.worldwide.movie.data.source.local.MovieDatabase;
import com.worldwide.movie.data.source.local.MoviesLocalDataSource;
import com.worldwide.movie.data.source.remote.MoviesRemoteDataSource;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

import static com.worldwide.movie.util.Config.BaseImageUrl;

public class MovieDetailFragment extends Fragment
    implements VideoClickListener, MovieDetailContract.View {

  CollapsingToolbarLayout appBarLayout;
  public static final String ARG_MOVIE = "item_id";

  private Movie movie;

  private RecyclerView recyclerView;
  private VideoAdapter adapter;
  private MovieDetailContract.Presenter presenter;
  ProgressBar progressBar;
  TextView tvShowNoVideo;
  private List<Video> videoList;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments().containsKey(ARG_MOVIE)) {
      movie = (Movie) getArguments().getSerializable(ARG_MOVIE);
    }
    videoList = new ArrayList<>(0);
    MovieDatabase movieDatabase = MovieDatabase.getInstance(getActivity());

    // Create the presenter
    presenter = new MovieDetailPresenter(
        MoviesRepository.getInstance(MoviesRemoteDataSource.getInstance(),
            MoviesLocalDataSource.getInstance(movieDatabase.movieDao())), movie, this);
  }

  @Override public void onResume() {
    super.onResume();
    Timber.d("Called onResume");
    presenter.start();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.movie_details_frag, container, false);

    // Show the dummy content as text in a TextView.
    if (movie != null) {
      setTitle(movie.getOriginalTitle());

      ((TextView) rootView.findViewById(R.id.tvTitle)).setText(movie.getOriginalTitle());

      if ((ImageView) getActivity().findViewById(R.id.ivPoster_detials) != null) {
        Picasso.get()
            .load(BaseImageUrl + movie.getBackdropPath())
            .placeholder(R.drawable.placeholder)
            .fit()
            .into((ImageView) getActivity().findViewById(R.id.ivPoster_detials));
      }

      ((TextView) rootView.findViewById(R.id.tvRatingCount)).setText(
          String.valueOf(movie.getVoteAverage()));

      ((TextView) rootView.findViewById(R.id.tvReleaseDate)).setText(
          String.format("Release Date: %s", movie.getReleaseDate()));
      ((TextView) rootView.findViewById(R.id.tvOverview)).setText(movie.getOverview());

      Picasso.get()
          .load(BaseImageUrl + movie.getImage())
          .placeholder(R.drawable.placeholder)
          .fit()
          .into((ImageView) (rootView.findViewById(R.id.ivPoster_brief)));
    }
    FloatingActionButton btnsave = rootView.findViewById(R.id.btnSave);
    btnsave.setOnClickListener(v -> {
          presenter.saveMovie(movie);
          Toast.makeText(getActivity(), "Saved to favourite", Toast.LENGTH_LONG).show();
        }

    );

    tvShowNoVideo = rootView.findViewById(R.id.tvShowNoVideo);
    progressBar = rootView.findViewById(R.id.progressBar);
    recyclerView = rootView.findViewById(R.id.recyclerView);
    setupRecyclerView();

    return rootView;
  }

  private void setupRecyclerView() {
    LinearLayoutManager layoutManager =
        new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
    recyclerView.setLayoutManager(layoutManager);

    adapter = new VideoAdapter(videoList, this);
    recyclerView.setAdapter(adapter);
  }

  public static MovieDetailFragment newInstance(Movie item) {
    MovieDetailFragment fragment = new MovieDetailFragment();
    Bundle args = new Bundle();
    args.putSerializable(ARG_MOVIE, item);
    fragment.setArguments(args);
    return fragment;
  }

  void setTitle(String title) {
    Activity activity = getActivity();
    appBarLayout = activity.findViewById(R.id.toolbar_layout);
    if (appBarLayout != null) {
      appBarLayout.setTitle(title);
    }
  }

  @Override public void onVideoSelected(Video clickedTask) {
    watchYoutubeVideo(getActivity(), clickedTask.getKey());
  }

  public static void watchYoutubeVideo(Context context, String id) {
    Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
    Intent webIntent =
        new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id));
    try {
      context.startActivity(appIntent);
    } catch (ActivityNotFoundException ex) {
      context.startActivity(webIntent);
    }
  }

  @Override public void setPresenter(MovieDetailContract.Presenter presenter) {
    this.presenter = presenter;
  }

  @Override public void setLoadingIndicator(boolean active) {
    progressBar.setVisibility(active ? View.VISIBLE : View.GONE);
  }

  @Override public void showVideoList(List<Video> videos) {
    adapter.replaceData(videos);
  }

  @Override public void showLoadingVideoError() {
    Toast.makeText(getActivity(), "Error : Unable to load", Toast.LENGTH_LONG).show();
  }

  @Override public void showNoVideos() {
    tvShowNoVideo.setVisibility(View.VISIBLE);
  }

  @Override public Movie getMovie() {
    return movie;
  }
}
