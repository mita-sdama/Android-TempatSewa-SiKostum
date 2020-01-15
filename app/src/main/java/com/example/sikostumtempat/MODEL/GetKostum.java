package com.example.sikostumtempat.MODEL;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetKostum {
    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private List<Kostum> result = new ArrayList<Kostum>();
    @SerializedName("message")
    private String message;

    public GetKostum() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Kostum> getResult() {
        return result;
    }

    public void setResult(List<Kostum> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
