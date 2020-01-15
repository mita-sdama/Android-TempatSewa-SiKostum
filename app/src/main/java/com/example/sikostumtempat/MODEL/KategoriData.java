package com.example.sikostumtempat.MODEL;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KategoriData {
    @SerializedName("data")
    public List<Kategori> data;

    public KategoriData(List<Kategori> data) {
        this.data = data;
    }

    public List<Kategori> getData() {
        return data;
    }

    public void setData(List<Kategori> data) {
        this.data = data;
    }
}


