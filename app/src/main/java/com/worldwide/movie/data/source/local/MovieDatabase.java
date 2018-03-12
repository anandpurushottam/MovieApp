package com.worldwide.movie.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.worldwide.movie.data.Movie;
import com.worldwide.movie.data.Video;

/**
 * Created by Anand on 11-03-2018.
 */
@Database(entities = { Movie.class }, version = 1) public abstract class MovieDatabase
    extends RoomDatabase {
  private static MovieDatabase INSTANCE;

  public abstract MovieDao movieDao();


  private static final Object sLock = new Object();

  public static MovieDatabase getInstance(Context context) {
    synchronized (sLock) {
      if (INSTANCE == null) {
        INSTANCE =
            Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, "Movies.db")
                .build();
      }
      return INSTANCE;
    }
  }
}
