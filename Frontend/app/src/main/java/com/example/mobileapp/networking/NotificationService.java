package com.example.mobileapp.networking;

import com.example.mobileapp.model.Notification;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NotificationService {

    @GET("notification/getNotificationFromUser")
    Call<List<Notification>> getNotificationFromUser(@Query("userId") String userId);
}
