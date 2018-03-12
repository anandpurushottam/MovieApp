package com.worldwide.movie.movie;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.worldwide.movie.R;
import com.worldwide.movie.data.Movie;
import com.worldwide.movie.data.source.MoviesRepository;
import com.worldwide.movie.data.source.local.MovieDatabase;
import com.worldwide.movie.data.source.local.MoviesLocalDataSource;
import com.worldwide.movie.data.source.remote.MoviesRemoteDataSource;
import com.worldwide.movie.detial.MovieDetailFragment;
import com.worldwide.movie.favourite.OfflineMovieListActivity;
import com.worldwide.movie.util.ActivityUtils;
import java.util.ArrayList;
import java.util.List;

import static com.worldwide.movie.detial.MovieDetailActivity.createIntent;
import static com.worldwide.movie.util.NullChecker.requireNonNull;

public class MovieListActivity extends AppCompatActivity
    implements MovieContract.View, MovieClickListener,
    NavigationView.OnNavigationItemSelectedListener {

  private boolean isTwoPane;
  private ArrayList<Movie> movieList;
  private MovieAdapter adapter;
  private MovieContract.Presenter presenter;
  private ProgressBar progressBar;
  RecyclerView recyclerView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_item_list);

    progressBar = findViewById(R.id.progressBar);

    if (findViewById(R.id.item_detail_container) != null) {
      isTwoPane = true;
    }

    movieList = new ArrayList<>(0);

    recyclerView = findViewById(R.id.item_list);
    setupRecyclerView();

    MovieDatabase database = MovieDatabase.getInstance(getBaseContext());
    // Create the presenter
    presenter = new MoviePresenter(
        MoviesRepository.getInstance(MoviesRemoteDataSource.getInstance(),
            MoviesLocalDataSource.getInstance(database.movieDao())), this);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
  }

  private void setupRecyclerView() {
    GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
    recyclerView.setLayoutManager(layoutManager);

    adapter = new MovieAdapter(movieList, this);
    recyclerView.setAdapter(adapter);
  }

  @Override public void setPresenter(MovieContract.Presenter presenter) {
    this.presenter = requireNonNull(presenter);
  }

  @Override public void setLoadingIndicator(boolean active) {
    progressBar.setVisibility(active ? View.VISIBLE : View.GONE);
  }

  @Override public void showMovieList(List<Movie> movies) {
    adapter.replaceData(movies);
  }

  @Override public void showLoadingMovieError() {
    Toast.makeText(getApplicationContext(), "Error : Unable to load", Toast.LENGTH_LONG).show();
  }

  @Override public void showNoMovies() {
    movieList.clear();
    adapter.notifyDataSetChanged();
  }

  @Override public void onMovieSelected(Movie movie) {

    if (isTwoPane) {

      MovieDetailFragment movieDetailFragment = MovieDetailFragment.newInstance(movie);
      ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), movieDetailFragment,
          R.id.item_detail_container);
    } else {
      startActivity(createIntent(this, movie));
    }
  }

  @Override protected void onResume() {
    super.onResume();
    presenter.start();
  }

  //@Override public boolean onOptionsItemSelected(MenuItem item) {
  //
  //  int id = item.getItemId();
  //
  //  //noinspection SimplifiableIfStatement
  //  if (id == R.id.action_filter) {
  //
  //    PopupMenu popup = new PopupMenu(getBaseContext(), findViewById(R.id.action_filter));
  //    //Inflating the Popup using xml file
  //    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
  //
  //    //registering popup with OnMenuItemClickListener
  //    popup.setOnMenuItemClickListener(item1 -> {
  //
  //      int seltedID = item1.getItemId();
  //      if (seltedID == R.id.action_popular) {
  //
  //      } else if (seltedID == R.id.action_mostrated) {
  //
  //      }
  //      return false;
  //    });
  //
  //    popup.show();//showing popup menu
  //  }
  //  return super.onOptionsItemSelected(item);
  //}
  //
  //@Override public boolean onCreateOptionsMenu(Menu menu) {
  //  // Inflate the menu; this adds items to the action bar if it is present.
  //  getMenuInflater().inflate(R.menu.main, menu);//Menu Resource, Menu
  //  return true;
  //}

  @Override public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_fav) {
      startActivity(OfflineMovieListActivity.createIntent(this));
    } else if (id == R.id.nav_about) {
      new LibsBuilder()
          //provide a style (optional) (LIGHT, DARK, LIGHT_DARK_TOOLBAR)
          .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
          //start the activity
          .start(this);
    }
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
}
