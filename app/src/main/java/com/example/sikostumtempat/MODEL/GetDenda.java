package com.example.sikostumtempat.MODEL;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetDenda {

    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private List<Denda> result = new ArrayList<Denda>();

    public GetDenda() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Denda> getResult() {
        return result;
    }

    public void setResult(List<Denda> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
