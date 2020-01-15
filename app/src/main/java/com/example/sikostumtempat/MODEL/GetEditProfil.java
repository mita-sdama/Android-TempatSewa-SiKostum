package com.example.sikostumtempat.MODEL;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetEditProfil {
    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private List<ProfilId> result = new ArrayList<ProfilId>();
    @SerializedName("message")
    private String message;
    public GetEditProfil() {}
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ProfilId> getResult() {
        return result;
    }

    public void setResult(List<ProfilId> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
