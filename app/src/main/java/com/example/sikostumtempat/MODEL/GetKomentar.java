package com.example.sikostumtempat.MODEL;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetKomentar {

    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private List<Komentar> result = new ArrayList<Komentar>();
    @SerializedName("message")
    private String message;

    public GetKomentar() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Komentar> getResult() {
        return result;
    }

    public void setResult(List<Komentar> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
