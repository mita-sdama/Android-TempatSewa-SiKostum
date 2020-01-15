package com.example.sikostumtempat.MODEL;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetWilayah {
    @SerializedName("data")
    public List<Region> data;

    public GetWilayah(List<Region> data) {
        this.data = data;
    }

    public List<Region> getData() {
        return data;
    }

    public void setData(List<Region> data) {
        this.data = data;
    }
}
