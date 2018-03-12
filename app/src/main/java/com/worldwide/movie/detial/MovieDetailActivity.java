package com.worldwide.movie.detial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.worldwide.movie.R;
import com.worldwide.movie.data.Movie;
import com.worldwide.movie.util.ActivityUtils;

import static com.worldwide.movie.util.Config.BaseImageUrl;

public class MovieDetailActivity extends AppCompatActivity {
  MovieDetailFragment movieDetailFragment;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_item_detail);

    Toolbar toolbar = findViewById(R.id.detail_toolbar);
    setSupportActionBar(toolbar);

    // Show the Up button in the action bar.
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    Movie movie = (Movie) getIntent().getSerializableExtra(MovieDetailFragment.ARG_MOVIE);
    if (savedInstanceState == null) {
      movieDetailFragment = MovieDetailFragment.newInstance(movie);
      ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), movieDetailFragment,
          R.id.item_detail_container);
    }


  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      onBackPressed();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  public static Intent createIntent(Context context, Movie movie) {
    Intent intent = new Intent(context, MovieDetailActivity.class);
    Bundle bundle = new Bundle();
    bundle.putSerializable(MovieDetailFragment.ARG_MOVIE, movie);//TODO Replace with Parcelable
    intent.putExtras(bundle);
    return intent;
  }
}
