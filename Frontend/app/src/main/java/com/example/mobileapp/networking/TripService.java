package com.example.mobileapp.networking;



import com.example.mobileapp.model.Trip;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TripService {
    @GET("trip/getTripById")
    public Call<Trip> getTripId(@Query("id") String id);

    @GET("/trip/getTripsByUser")
    public Call<List<Trip>> getTripsByUser(@Query("eMail") String eMail);

    @POST("/trip/addTrip")
    public Call<String> addTrip(@Body Trip trip);
}
