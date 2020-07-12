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
    public Call<String> addTransactionToTrip(@Query("transactionId") String transactionId, @Query("tripId") String tripId,@Query("notificationId") String notification);

    @DELETE("transaction/deleteTransaction")
    public Call <String> deleteTransaction(@Query("transactionId") String transactionId,@Query("tripId") String tripId,@Query("notificationId")String notificationId);

    @GET("transaction/getAllTransactionsByTrip")
    public Call <List<Transaction>> getAllTransactionsByTrip(@Query("tripId") String tripId);

    @POST("transaction/addTransaction")
    public Call<String> addTransaction(@Body Transaction transaction, @Query("userId") String eMail,
                                       @Query("notificationMessage") String notificationMessage,
                                       @Query("tripId") String tripId);

    @DELETE("transaction/deleteTransactionInvite")
    public Call<String> deleteTransactionInvite(@Query("transactionId") String transactionId,@Query("notificationId")String notificationId);
}


