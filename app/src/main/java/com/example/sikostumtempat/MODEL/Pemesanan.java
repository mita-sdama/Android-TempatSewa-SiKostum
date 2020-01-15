package com.example.sikostumtempat.MODEL;

import com.google.gson.annotations.SerializedName;

public class Pemesanan {
    @SerializedName("id_log")
    private String id_log;
    @SerializedName("id_detail")
    private  String id_detail;
    @SerializedName("id_tempat")
    private String id_tempat;
    @SerializedName("nama")
    private  String nama_user;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("tgl_transaksi")
    private String tgl_transaksi;
    @SerializedName("nama_kostum")
    private String nama_kostum;
    @SerializedName("jumlah")
    private String jumlah;
    @SerializedName("harga_kostum")
    private String harga_kostum;
    @SerializedName("status_log")
    private String status_log;
    @SerializedName("jumlah_denda")
    private String jumlah_denda;
    @SerializedName("keterangan")
    private String keterangan;
    @SerializedName("foto_kostum")
    private String foto_kostum;
    @SerializedName("tgl_sewa")
    private String tgl_sewa;
    @SerializedName("tgl_kembali")
    private String tgl_kembali;
    @SerializedName("no_hp")
    private String no_hp;
    @SerializedName("email")
    private String email;
    @SerializedName("provinsi")
    private String provinsi;
    @SerializedName("kota")
    private String kota;
    @SerializedName("kecamatan")
    private String kecamatan;
    @SerializedName("desa")
    private String desa;
    @SerializedName("jumlahPesan")
    private String jumlahValid;
    @SerializedName("jumlahTransfer")
    private String jumlahSewa;

    public String getId_tempat() {
        return id_tempat;
    }

    public void setId_tempat(String id_tempat) {
        this.id_tempat = id_tempat;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
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
        this.provinsi= provinsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota= kota;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan= kecamatan;
    }

    public String getDesa() {
        return desa;
    }

    public void setDesa(String desa) {
        this.desa= desa;
    }

    public String getTgl_transaksi() {
        return tgl_transaksi;
    }

    public void setTgl_transaksi(String tgl_transaksi) {
        this.tgl_transaksi = tgl_transaksi;
    }

    public String getNama_kostum() {
        return nama_kostum;
    }

    public void setNama_kostum(String nama_kostum) {
        this.nama_kostum = nama_kostum;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getHarga_kostum() {
        return harga_kostum;
    }

    public void setHarga_kostum(String harga_kostum) {
        this.harga_kostum = harga_kostum;
    }

    public String getStatus_log() {
        return status_log;
    }

    public void setStatus_log(String status_log) {
        this.status_log = status_log;
    }

    public String getId_log() {
        return id_log;
    }

    public void setId_log(String id_log) {
        this.id_log = id_log;
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
        this.keterangan= keterangan;
    }

    public String getFoto_kostum() {
        return foto_kostum;
    }

    public void setFoto_kostum(String foto_kostum) {
        this.foto_kostum = foto_kostum;
    }

    public String getTgl_sewa() {
        return tgl_sewa;
    }

    public void setTgl_sewa(String tgl_sewa) {
        this.tgl_sewa = tgl_sewa;
    }

    public String getTgl_kembali() {
        return tgl_kembali;
    }

    public void setTgl_kembali(String tgl_kembali) {
        this.tgl_kembali = tgl_kembali;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp= no_hp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email= email;
    }

    public String getJumlahValid() {
        return jumlahValid;
    }

    public void setJumlahValid(String jumlahValid) {
        this.jumlahValid = jumlahValid;
    }

    public String getJumlahSewa() {
        return jumlahSewa;
    }

    public void setJumlahSewa(String jumlahSewa) {
        this.jumlahSewa = jumlahSewa;
    }

    public Pemesanan(String id_detail, String jumlah_denda,String keterangan, String id_log,
                     String id_tempat, String nama_user, String no_hp, String email, String tgl_transaksi, String nama_kostum, String jumlah,
                     String status_log, String alamat, String provinsi, String kota, String kecamatan, String desa, String foto_kostum, String tgl_sewa, String tgl_kembali,
    String jumlahValid, String jumlahSewa){
        this.id_tempat = id_tempat;
        this.jumlah_denda=jumlah_denda;
        this.keterangan = keterangan;
        this.id_log=id_log;
        this.nama_user = nama_user;
        this.no_hp = no_hp;
        this.email = email;
        this.tgl_transaksi = tgl_transaksi;
        this.nama_kostum = nama_kostum;
        this.jumlah = jumlah;
        this.status_log = status_log;
        this.alamat = alamat;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kecamatan = kecamatan;
        this.desa = desa;
        this.id_detail= id_detail;
        this.foto_kostum=foto_kostum;
        this.tgl_sewa = tgl_sewa;
        this.tgl_kembali = tgl_kembali;
        this.jumlahValid= jumlahValid;
        this.jumlahSewa= jumlahSewa;

    }
}
