package com.worldwide.movie;

import android.app.Application;
import timber.log.Timber;

/**
 * Created by Anand on 09-03-2018.
 */

public class MovieApp extends Application {
  @Override public void onCreate() {
    super.onCreate();
    Timber.plant(new Timber.DebugTree());
  }
}
