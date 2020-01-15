package com.example.sikostumtempat.MODEL;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetIdentitas {
    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private List<PostIdentitas> result = new ArrayList<PostIdentitas>();
    @SerializedName("message")
    private String message;
    public GetIdentitas() {}
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PostIdentitas> getResult() {
        return result;
    }

    public void setResult(List<PostIdentitas> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
