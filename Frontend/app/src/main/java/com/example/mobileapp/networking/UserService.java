package com.example.mobileapp.networking;

import com.example.mobileapp.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {
    @GET("user/getByEMail")
    public Call<User> getUserByEmail(@Query("eMail") String eMail);

    @POST("user/registrateUser")
    public Call<String> registrateUser(@Body User user);

    @POST("user/addUserToTrip")
    public Call<String> addUserToTrip(@Body User user);
}
