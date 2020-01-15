package com.example.sikostumtempat.MODEL;

import com.google.gson.annotations.SerializedName;

public class Kostum {
    @SerializedName("id_kostum")
    private String id_kostum;
    @SerializedName("id_tempat")
    private  String id_tempat;
    @SerializedName("id_user")
    private  String id_user;
    @SerializedName("id_kategori")
    private String id_kategori;
    @SerializedName("nama_kostum")
    private  String nama_kostum;
    @SerializedName("jumlah_kostum")
    private String jumlah_kostum;
    @SerializedName("harga_kostum")
    private String  harga_kostum;
    @SerializedName("deskripsi_kostum")
    private String deskripsi_kostum;
    @SerializedName("foto_kostum")
    private String foto_kostum;

    public String getId_kostum() {
        return id_kostum;
    }

    public void setId_kostum(String id_kostum) {
        this.id_kostum = id_kostum;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getNama_kostum() {
        return nama_kostum;
    }

    public void setNama_kostum(String nama_kostum) {
        this.nama_kostum = nama_kostum;
    }

    public String getJumlah_kostum() {
        return jumlah_kostum;
    }

    public void setJumlah_kostum(String jumlah_kostum) {
        this.jumlah_kostum = jumlah_kostum;
    }

    public String getHarga_kostum() {
        return harga_kostum;
    }

    public void setHarga_kostum(String harga_kostum) {
        this.harga_kostum = harga_kostum;
    }

    public String getDeskripsi_kostum() {
        return deskripsi_kostum;
    }

    public void setDeskripsi_kostum(String deskripsi_kostum) {
        this.deskripsi_kostum = deskripsi_kostum;
    }

    public String getFoto_kostum() {
        return foto_kostum;
    }

    public void setFoto_kostum(String foto_kostum) {
        this.foto_kostum = foto_kostum;
    }

    public String getId_tempat() {
        return id_tempat;
    }

    public void setId_tempat(String id_tempat) {
        this.id_tempat = id_tempat;
    }

    public Kostum(String id_kostum, String id_tempat, String id_user, String id_kategori,
                  String nama_kostum, String jumlah_kostum, String harga_kostum,
                  String deskripsi_kostum, String foto_kostum){
        this.id_kostum= id_kostum;
        this.id_user = id_user;
        this.id_kategori= id_kategori;
        this.nama_kostum = nama_kostum;
        this.jumlah_kostum= jumlah_kostum;
        this.harga_kostum= harga_kostum;
        this .deskripsi_kostum= deskripsi_kostum;
        this.foto_kostum= foto_kostum;
        this.id_tempat= id_tempat;
    }
}
