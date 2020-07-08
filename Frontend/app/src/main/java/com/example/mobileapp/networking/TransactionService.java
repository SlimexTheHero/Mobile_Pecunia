package com.example.mobileapp.networking;

import com.example.mobileapp.model.Transaction;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TransactionService {

    @POST("/addAdminToTrip")
    public Call<String> addAdminToTrip(@Body Transaction transaction, @Query("tripId") String tripId);

    @DELETE("/deleteTransaction")
    public Call <String> deleteAdminFromTrip(@Query("transactionId") String transactionId,@Query("tripId") String tripId);
}
