package com.example.mobileapp.networking;

import com.example.mobileapp.model.Transaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TransactionService {

    @POST("transaction/addTransactionToTrip")
    public Call<String> addAdminToTrip(@Body Transaction transaction, @Query("tripId") String tripId);

    @DELETE("transaction/deleteTransaction")
    public Call <String> deleteAdminFromTrip(@Query("transactionId") String transactionId,@Query("tripId") String tripId);

    @GET("transaction/getAllTransactionsByTrip")
    public Call <List<Transaction>> getAllTransactionsByTrip(@Query("tripId") String tripId);
}
