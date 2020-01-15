package com.example.sikostumtempat.MODEL;

import com.google.gson.annotations.SerializedName;

public class Denda {

    @SerializedName("id_denda")
    private  String id_denda;
    @SerializedName("id_detail")
    private String id_detail;
    @SerializedName("jumlah_denda")
    private String jumlah_denda;
    @SerializedName("keterangan")
    private String keterangan;


    public String getId_denda() {
        return id_denda;
    }

    public void setId_denda(String id_denda) {
        this.id_denda = id_denda;
    }

    public String getId_detail() {
        return id_detail;
    }

    public void setId_detail(String id_detail) {
        this.id_detail = id_detail;
    }

    public String getJumlah_denda() {
        return jumlah_denda;
    }

    public void setJumlah_denda(String jumlah_denda) {
        this.jumlah_denda = jumlah_denda;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Denda(String id_denda, String id_detail, String jumlah_denda, String keterangan){
        this.id_denda= id_denda;
        this.id_detail= id_detail;
        this.jumlah_denda= jumlah_denda;
        this.keterangan= keterangan;

    }


}
