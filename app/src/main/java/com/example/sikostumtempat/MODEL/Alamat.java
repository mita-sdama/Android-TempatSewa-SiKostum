package com.example.sikostumtempat.MODEL;

import com.google.gson.annotations.SerializedName;

public class Alamat {
    @SerializedName("id_alamat")
    public  String id_alamat;
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("label_alamat")
    private String label_alamat;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("provinsi")
    private String provinsi;
    @SerializedName("kota")
    private String kota;
    @SerializedName("kecamatan")
    private String kecamatan;
    @SerializedName("desa")
    private String desa;

    private String action;

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getLabel_alamat() {
        return label_alamat;
    }

    public void setLabel_alamat(String label_alamat) {
        this.label_alamat = label_alamat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getDesa() {
        return desa;
    }

    public void setDesa(String desa) {
        this.desa = desa;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getId_alamat() {
        return id_alamat;
    }
    public String getAlamatIn(){
        return id_alamat;
    }
    public void setAlamatIn(String alamatIn){
        this.id_alamat = alamat;
    }

    public void setId_alamat(String id_alamat) {
        this.id_alamat = id_alamat;
    }

    public String getId() {
        return id_alamat;
    }

    public Alamat(String id_alamat, String id_user, String label_alamat, String alamat, String provinsi, String kota, String kecamatan, String desa, String action){
        this.id_user = id_user;
        this.label_alamat = label_alamat;
        this.alamat = alamat;
        this.id_alamat =alamat;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kecamatan = kecamatan;
        this.desa = desa;
        this.action = action;
        this.id_alamat = id_alamat;
        this.setId_alamat(id_alamat);
        this.setAlamat(alamat);

    }
    public String getIdAlamat(){
        return id_alamat;
    }
    public  void setIdAlamat(String id_alamat ){
        this.id_alamat=id_alamat;
    }
    @Override
    public String toString(){
        return alamat;
    }
    public void setName(String alamat){this.alamat=alamat;}


}
