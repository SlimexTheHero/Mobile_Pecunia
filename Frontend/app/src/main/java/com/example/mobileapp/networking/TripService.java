package com.example.mobileapp.networking;



import com.example.mobileapp.model.CompleteTrip;
import com.example.mobileapp.model.Notification;
import com.example.mobileapp.model.Trip;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TripService {

    @GET("trip/getTripById")
    public Call<Trip> getTripId(@Query("id") String id);

    @GET("trip/getTripsByUser")
    public Call<List<Trip>> getTripsByUser(@Query("eMail") String eMail);

    @POST("trip/addTrip")
    public Call<String> addTrip(@Body Trip trip);

    @POST("trip/addAdminToTrip")
    public Call<String> addAdminToTrip(@Query("eMail") String eMail,@Query("TripId") String tripId);

    @DELETE("trip/deleteAdmin")
    public Call <String> deleteAdminFromTrip(@Query("eMail") String eMail,@Query("TripId") String tripId);

    @POST("trip/getBillFromTrip")
    public Call<String> getBillFromTrip(@Query("tripId") String tripId);

    @GET("trip/getCompleteTripById")
    public Call<CompleteTrip> getCompleteTripById(@Query("tripId") String tripId);

    @HTTP(method = "DELETE", path = "trip/deleteUserFromTrip", hasBody = true)
    public Call<String> deleteUserFromTrip(@Query("eMail") String eMail, @Query("tripId") String tripId,
                                           @Body Notification notification);
}
