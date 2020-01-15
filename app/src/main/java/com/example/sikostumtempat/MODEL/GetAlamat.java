package com.example.sikostumtempat.MODEL;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetAlamat {
    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private List<Alamat> result = new ArrayList<Alamat>();
    @SerializedName("message")
    private String message;

    public GetAlamat() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Alamat> getResult() {
        return result;
    }

    public void setResult(List<Alamat> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
