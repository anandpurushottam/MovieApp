package com.worldwide.movie.networking;

import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

import static java.lang.String.format;

public class NetworkInterceptor implements Interceptor {
  @Override public Response intercept(Interceptor.Chain chain) throws IOException {
    Request original = chain.request();
    HttpUrl originalHttpUrl = original.url();

    HttpUrl url = originalHttpUrl.newBuilder()
        .addQueryParameter("api_key", "ad2d2c053db123818223e84cba3c42b1")
        .build();

    Request.Builder requestBuilder = original.newBuilder().url(url);
    Request request = requestBuilder.build();



    long t1 = System.nanoTime();
    Timber.d("Sending request %s on %s%n%s",
        request.url(), chain.connection(), request.headers());

    Response response = chain.proceed(request);

    long t2 = System.nanoTime();
    Timber.d("Received response for %s in %.1fms%n%s",
        response.request().url(), (t2 - t1) / 1e6d, response.headers());


    return chain.proceed(request);
  }
}