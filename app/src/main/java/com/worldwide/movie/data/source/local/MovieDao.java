package com.worldwide.movie.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.worldwide.movie.data.Movie;
import java.util.List;

/**
 * Created by Anand on 11-03-2018.
 */
@Dao public interface MovieDao {
  @Query("SELECT * FROM Movie") List<Movie> getMoives();

  @Query("SELECT * FROM Movie WHERE id = :id") Movie getMovieById(String id);

  @Insert(onConflict = OnConflictStrategy.REPLACE) void insertTask(Movie movie);

  @Update int updateTask(Movie movie);

  @Query("DELETE FROM Movie WHERE id = :id") int deleteMovieById(String id);

  @Query("DELETE FROM Movie") void deleteMovie();
}
