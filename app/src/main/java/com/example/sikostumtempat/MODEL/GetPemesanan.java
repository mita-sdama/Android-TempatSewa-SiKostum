package com.example.sikostumtempat.MODEL;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetPemesanan {
    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private List<Pemesanan> result = new ArrayList<Pemesanan>();
    @SerializedName("message")
    private String message;

    public GetPemesanan() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Pemesanan> getResult() {
        return result;
    }

    public void setResult(List<Pemesanan> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
