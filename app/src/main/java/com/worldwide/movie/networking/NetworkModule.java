package com.worldwide.movie.networking;

import com.worldwide.movie.BuildConfig;
import com.worldwide.movie.util.NullChecker;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Anand on 09-03-2018.
 */

public class NetworkModule {
  private static OkHttpClient okHttpClient;
  private static Retrofit retrofit;

  public static Retrofit getInstance() {
    if (retrofit == null) {
      retrofit = new Retrofit.Builder()
          .baseUrl(BuildConfig.BASEURL)
          .client(getClient())
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .build();
    }
    return retrofit;
  }

  private static OkHttpClient getClient() {
    if (okHttpClient == null) {
      okHttpClient = new OkHttpClient.Builder().addInterceptor(new NetworkInterceptor()).build();
    }
    return okHttpClient;
  }
}
