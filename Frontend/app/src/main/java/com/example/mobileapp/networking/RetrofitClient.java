package com.example.mobileapp.networking;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * https://www.baeldung.com/retrofit
 */
public class RetrofitClient {
    //private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://ab93f19359eb.ngrok.io/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                   // .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
}
