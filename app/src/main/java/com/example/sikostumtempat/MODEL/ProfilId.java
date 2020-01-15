package com.example.sikostumtempat.MODEL;

import com.google.gson.annotations.SerializedName;

public class ProfilId {
    @SerializedName("id_user")
    private int id_user;
    @SerializedName("nama")
    private String nama;
    @SerializedName("jenis_kelamin")
    private String jenis_kelamin;
    @SerializedName("no_hp")
    private String no_hp;
    @SerializedName("foto_user")
    private String foto_user;
    @SerializedName("email")
    private String email;
    @SerializedName("level")
    private String level;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;


    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public String getFoto_user() {
        return foto_user;
    }

    public void setFoto_user(String foto_user) {
        this.foto_user = foto_user;
    }

    public ProfilId(int id_user, String nama, String no_hp, String email,
                    String level, String username, String password,String foto_user){
        this.id_user= id_user;
        this.nama= nama;
        this.no_hp= no_hp;
        this.level=level;
        this.username= username;
        this.password=password;
        this.foto_user=foto_user;
        this.email=email;
    }
}
