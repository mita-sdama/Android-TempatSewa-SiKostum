package com.example.sikostumtempat.MODEL;

import com.google.gson.annotations.SerializedName;

public class Komentar {
    @SerializedName("tgl_transaksi")
    private  String tgl_transaksi;
    @SerializedName("nama")
    private String nama;
    @SerializedName("nama_kostum")
    private  String nama_kostum;
    @SerializedName("komentar")
    private  String komentar;


    public String getTgl_transaksi() {
        return tgl_transaksi;
    }

    public void setTgl_transaksi(String tgl_transaksi) {
        this.tgl_transaksi = tgl_transaksi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama_kostum() {
        return nama_kostum;
    }

    public void setNama_kostum(String nama_kostum) {
        this.nama_kostum = nama_kostum;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }





    public Komentar(String tgl_transaksi, String nama, String nama_kostum,
                    String komentar){
        this.tgl_transaksi= tgl_transaksi;
        this.nama= nama;
        this.nama_kostum= nama_kostum;
        this.komentar= komentar;

    }
}
