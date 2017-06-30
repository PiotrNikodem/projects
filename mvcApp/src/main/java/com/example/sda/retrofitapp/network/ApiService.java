package com.example.sda.retrofitapp.network;

import com.example.sda.retrofitapp.model.CallActivity;
import com.example.sda.retrofitapp.model.Client;
import com.example.sda.retrofitapp.model.Contact;
import com.example.sda.retrofitapp.model.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by sda on 23.05.17.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("Account/Login")
    Call<LoginResponse> login(@Field("Email") String email, @Field("Password") String password);

    @GET("Activities/Get")
    Call<List<CallActivity>> getActivities();

    @GET("Clients/Get")
    Call<List<Client>> getClients();

    @PUT("Clients/Put")
    Call<Client> updateClient(@Body Client client);

    @POST("Clients/Post")
    Call<List<Client>> createClient(@Body List<Client> clients);

    @GET("Contacts/Get")
    Call<List<Contact>> getContacts();
}
