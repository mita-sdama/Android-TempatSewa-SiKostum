package com.example.sikostumtempat.MODEL;

import com.google.gson.annotations.SerializedName;

public class PostIdentitas {
    @SerializedName("id_identitas")
    private String id_identitas;

    @SerializedName("id_user")
    private  String id_user;
    @SerializedName("foto_ktp")
    private String foto_ktp;
    @SerializedName("status")
    protected  String status;
    private  String action ;


    public String getId_identitas() {
        return id_identitas;
    }


    public void setId_identitas(String id_identitas) {
        this.id_identitas = id_identitas;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getFoto_ktp() {
        return foto_ktp;
    }

    public void setFoto_ktp(String foto_ktp) {
        this.foto_ktp = foto_ktp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PostIdentitas(String id_identitas, String id_user, String foto_ktp, String status, String action){
        this.id_identitas= id_identitas;
        this.id_user= id_user;
        this.foto_ktp= foto_ktp;
        this.action= action;
        this.status= status;
    }

}
