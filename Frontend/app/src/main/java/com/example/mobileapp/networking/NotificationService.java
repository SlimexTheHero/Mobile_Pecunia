package com.example.mobileapp.networking;

import com.example.mobileapp.model.Notification;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NotificationService {

    @GET("notification/getNotificationsFromUser")
    Call<List<Notification>> getNotificationFromUser(@Query("userId") String userId);

    @DELETE("notification/deleteNotification")
    public Call<String> deleteNotification(@Query("notificationId") String notificationId);

    @POST("notification/createDeleteTransactionNotification")
    public Call<String> createDeleteTransactionNotification(@Body Notification notification);
}
