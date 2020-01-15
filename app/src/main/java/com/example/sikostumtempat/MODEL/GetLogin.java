package com.example.sikostumtempat.MODEL;

import com.google.gson.annotations.SerializedName;

public class GetLogin {

    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private String result;

    @SerializedName("message")
    private String message;

    public GetLogin() {}

    public String getStatus() {
        return status;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

}
