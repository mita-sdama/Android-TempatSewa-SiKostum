package com.example.sikostumtempat.MODEL;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetTempat {
    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private List<Tempat> result = new ArrayList<Tempat>();
    @SerializedName("message")
    private String message;

    public GetTempat() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Tempat> getResult() {
        return result;
    }

    public void setResult(List<Tempat> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
