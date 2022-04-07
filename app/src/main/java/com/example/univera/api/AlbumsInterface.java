package com.example.univera.api;

import com.example.univera.model.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AlbumsInterface {

    @GET("albums")
    Call<List<Album>> getAlbumsList();
}
