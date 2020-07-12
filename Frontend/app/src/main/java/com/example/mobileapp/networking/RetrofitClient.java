package com.example.mobileapp.networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * https://www.baeldung.com/retrofit
 */
public class RetrofitClient {
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit retrofit;
    private static Gson gson;
    private static final String BASE_URL = "https://3f39736a4293.ngrok.io/";


    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            gson = new GsonBuilder().setLenient().create();
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                     .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
}
