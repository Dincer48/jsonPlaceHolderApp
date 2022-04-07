package com.example.univera.api;

import com.example.univera.model.user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("users")
    Call<List<User>> getUsers();

}
