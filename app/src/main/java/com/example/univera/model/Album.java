package com.example.univera.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Album {
    @SerializedName("userId")
    @Expose
    private int mUserId;
    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("title")
    @Expose
    private String mTitle;

    public Album(int mUserId, int mId, String mTitle) {
        this.mUserId = mUserId;
        this.mId = mId;
        this.mTitle = mTitle;
    }

    public int getmUserId() {
        return mUserId;
    }

    public int getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }
}