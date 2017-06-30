package com.example.sda.retrofitapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sda on 23.05.17.
 */

public class LoginResponse {

    @SerializedName("access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
