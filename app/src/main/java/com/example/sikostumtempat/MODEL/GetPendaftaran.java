package com.example.sikostumtempat.MODEL;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetPendaftaran {

    @SerializedName("status")
    String status;
    @SerializedName("result")
    private List<Pendaftaran> result = new ArrayList<Pendaftaran>();
    @SerializedName("message")
    String message;
    @SerializedName("value")
    String value;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public List<Pendaftaran> getResult() {
        return result;
    }
    public void setResult(List<Pendaftaran> result) {
        this.result = result;
    }
    public String getValue(){
        return value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
