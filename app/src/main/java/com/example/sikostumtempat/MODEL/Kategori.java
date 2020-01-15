package com.example.sikostumtempat.MODEL;

import com.google.gson.annotations.SerializedName;

public class Kategori {

    @SerializedName("id_kategori")
    public String id_kategori;
    @SerializedName("nama_kategori")
    public String nama_kategori;

    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public String getId() {
        return id_kategori;
    }

    public Kategori(String id_kategori, String nama_kategori){
        this.id_kategori= id_kategori;
        this.nama_kategori = nama_kategori;

    }


    @Override
    public String toString() {
        return this.nama_kategori;
    }

}
