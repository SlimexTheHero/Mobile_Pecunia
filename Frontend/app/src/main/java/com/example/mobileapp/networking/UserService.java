package com.example.mobileapp.networking;

import com.example.mobileapp.model.Notification;
import com.example.mobileapp.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserService {
    @GET("user/getByEMail")
    public Call<User> getUserByEmail(@Query("eMail") String eMail);

    @POST("user/registrateUser")
    public Call<String> registrateUser(@Body User user);

    @POST("user/addUserToTrip")
    public Call<String> addUserToTrip(@Query("eMail") String eMail,@Query("tripId") String tripId,
                                      @Body Notification notification);

    @PUT("user/changeNameOfUser")
    public Call<String> changeNameOfUser(@Query("eMail") String eMail,@Query("newName") String newName);

    @PUT("user/changePWOfUser")
    public Call<String> changePWOfUser(@Query("eMail") String eMail,@Query("newPw") String newPw);

    @PUT("user/changeNameAndPWOfUser")
    public Call<String> changeNameAndPWOfUser(@Query("eMail") String eMail,@Query("newName") String newName,
                                              @Query("newPw") String newPw);

    @POST("user/deleteUser")
    public Call<String> deleteUser(@Query("eMail") String eMail);

    @Multipart
    @POST("user/addImgToUser")
    public Call<ResponseBody> addImgToUser(@Query("base64String") String base64, @Query("eMail") String eMail);
}
