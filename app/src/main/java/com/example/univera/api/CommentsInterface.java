package com.example.univera.api;

import com.example.univera.model.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface CommentsInterface {

    @GET
    Call<List<Comment>> getComments(@Url String url);
}
