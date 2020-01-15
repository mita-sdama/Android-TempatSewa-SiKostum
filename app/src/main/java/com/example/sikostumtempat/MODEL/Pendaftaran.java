package com.example.sikostumtempat.MODEL;

import com.google.gson.annotations.SerializedName;

public class Pendaftaran {
    @SerializedName("id_user")
    private String idUser;
    @SerializedName("nama")
    private String nama;
    @SerializedName("jenis_kelamin")
    private String jenisKelamin;
    @SerializedName("email")
    private String email;
    @SerializedName("no_hp")
    private String noHp;
    @SerializedName("foto_user")
    private String fotoUser;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    private String action;

    public Pendaftaran(String idUser, String nama, String jenisKelamin, String email, String noHp,
                       String fotoUser, String username, String password, String action){
        this.idUser = idUser;
        this.nama = nama;
        this.jenisKelamin= jenisKelamin;
        this.email= email;
        this.noHp= noHp;
        this.fotoUser= fotoUser;
        this.username=username;
        this.password= password;
        this.action= action;

    }
    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getFotoUser() {
        return fotoUser;
    }

    public void setFotoUser(String fotoUser) {
        this.fotoUser = fotoUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
