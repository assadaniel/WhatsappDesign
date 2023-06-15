package com.example.whatsappdesign;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
    @POST("Users")
    Call<Void> createUser(@Body UserCreatePost userCreatePost);

    @POST("Tokens")
    Call<String> getToken(@Body UserCreateToken userCreateToken);

    @GET("Users/{id}")
    Call<UserCreatePost> getUser(@Path("id") String id,
                                 @Header("Authorization") String authHeader);

    @GET("Chats")
    Call<List<User>> getUsers(@Header("Authorization") String authHeader);

    @POST("Chats")
    Call<UserDataFromAdd> addChat(@Body OnlyUsername onlyUsername,
                                  @Header("Authorization") String authHeader);

    @GET("Chats/{id}/Messages")
    Call<List<Message>> getMessages(@Path("id") int id,
                                    @Header("Authorization") String authHeader);

    @POST("Chats/{id}/Messages")
    Call<Void> postMessage(@Path("id") int id,@Body MessageToSend message,
                           @Header("Authorization") String authHeader);
}
