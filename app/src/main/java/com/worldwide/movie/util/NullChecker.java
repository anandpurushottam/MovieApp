package com.worldwide.movie.util;

/**
 * Created by Anand on 09-03-2018.
 */

public class NullChecker {

  public static <T> T requireNonNull(T obj, String message) {
    if (obj == null)
      throw new NullPointerException(message);
    return obj;
  }

  public static <T> T requireNonNull(T obj) {
    if (obj == null)
      throw new NullPointerException();
    return obj;
  }
}