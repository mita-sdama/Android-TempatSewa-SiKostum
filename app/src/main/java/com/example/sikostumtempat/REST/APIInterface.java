package com.example.sikostumtempat.REST;


import com.example.sikostumtempat.MODEL.GetAlamat;
import com.example.sikostumtempat.MODEL.GetDeleteAlamat;
import com.example.sikostumtempat.MODEL.GetDenda;
import com.example.sikostumtempat.MODEL.GetEditProfil;
import com.example.sikostumtempat.MODEL.GetIdentitas;
import com.example.sikostumtempat.MODEL.GetKategori;
import com.example.sikostumtempat.MODEL.GetKomentar;
import com.example.sikostumtempat.MODEL.GetKostum;
import com.example.sikostumtempat.MODEL.GetLogin;
import com.example.sikostumtempat.MODEL.GetPemesanan;
import com.example.sikostumtempat.MODEL.GetPendaftaran;
import com.example.sikostumtempat.MODEL.GetProfilId;
import com.example.sikostumtempat.MODEL.GetTempat;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {
    @FormUrlEncoded
    @POST("TempatSewa/Login/login")
    Call<GetLogin> loginToko(
            @Field("username") String username,
            @Field("password") String password);

    //insert kategori
    @Multipart
    @POST("TempatSewa/pendaftaran/all")
    Call<GetPendaftaran> postPendaftaran(
            @Part MultipartBody.Part file,
            @Part("nama") RequestBody nama,
            @Part("jenis_kelamin") RequestBody jenis_kelamin,
            @Part("email") RequestBody email,
            @Part("no_hp") RequestBody noHp,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("action") RequestBody action
    );

    //TAMPIL Profil
    @Multipart
    @POST("TempatSewa/profil/myProfil")
    Call<GetProfilId> getMyProfile(
            @Part("id_user") RequestBody id_user
    );
    @Multipart
    @POST("TempatSewa/profil/myedit")
    Call<GetEditProfil>postEditProfil(
            @Part MultipartBody.Part file,
            @Part("id_user") RequestBody id_user,
            @Part("nama") RequestBody nama,
            @Part("jenis_kelamin") RequestBody jenis_kelamin,
            @Part("no_hp") RequestBody no_hp,
            @Part("email") RequestBody email,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password
    );

    //Identitas
    @Multipart
    @POST("TempatSewa/Identitasku/myidentitas")
    Call<GetIdentitas> getIdentitas(
            @Part("id_user") RequestBody id_user
    );
    @Multipart
    @POST("TempatSewa/Identitasku/all")
    Call<GetIdentitas>postIdentitas(
            @Part MultipartBody.Part file,
            @Part("id_user") RequestBody id_user,
            @Part("action") RequestBody action
    );
    @Multipart
    @POST("TempatSewa/Identitasku/editidentitas")
    Call<GetIdentitas>putIdentitas(
            @Part MultipartBody.Part file,
            @Part("id_identitas") RequestBody id_identitas
    );

    //Alamat
    @Multipart
    @POST("TempatSewa/Alamat/myAlamat")
    Call<GetAlamat> getAlamat(
            @Part("id_user") RequestBody id_user

    );

    @Multipart
    @POST("TempatSewa/Alamat/all")
    Call<GetAlamat>postAlamat(
            @Part("id_user") RequestBody id_user,
            @Part("label_alamat") RequestBody label_alamat,
            @Part("alamat") RequestBody alamat,
            @Part("provinsi") RequestBody provinsi,
            @Part("kota") RequestBody kota,
            @Part("kecamatan") RequestBody kecamatan,
            @Part("desa") RequestBody desa,
            @Part("action") RequestBody action
    );

    @Multipart
    @POST("TempatSewa/Alamat/editalamat")
    Call<GetAlamat>putAlamat(
            @Part("id_alamat") RequestBody id_alamat,
            @Part("label_alamat") RequestBody label_alamat,
            @Part("alamat") RequestBody alamat,
            @Part("provinsi") RequestBody provinsi,
            @Part("kota") RequestBody kota,
            @Part("kecamatan") RequestBody kecamatan,
            @Part("desa") RequestBody desa
    );
    @Multipart
    @POST("TempatSewa/Alamat/deletemy")
    Call<GetDeleteAlamat>deleteAlamat(
            @Part("id_alamat") RequestBody id_alamat
    );


    @Multipart
    @POST("TempatSewa/TempatSewa/insertTempat")
    Call<GetTempat>posTempat(
            @Part MultipartBody.Part file,
            @Part("id_user") RequestBody id_user,
            @Part("id_alamat") RequestBody id_alamat,
            @Part("nama_tempat") RequestBody nama_tempat,
            @Part("no_rekening") RequestBody no_rekening,
            @Part("slogan_tempat") RequestBody slogan_tempat,
            @Part("deskripsi_tempat") RequestBody deskripsi_tempat,
            @Part("status_tempat") RequestBody status_tempat
    );
@Multipart
    @POST("TempatSewa/TempatSewa/alamat")
    Call<GetAlamat>getAlm(
        @Part("id_user") RequestBody id_user


);
@Multipart
    @POST("TempatSewa/TempatSewa/tampilTempat")
    Call<GetTempat>getTempat(
        @Part("id_user") RequestBody id_user
);
@Multipart
    @POST("TempatSewa/TempatSewa/updateTempat")
    Call<GetTempat>putTempat(
        @Part MultipartBody.Part file,
        @Part("id_tempat") RequestBody id_tempat,
        @Part("id_alamat") RequestBody id_alamat,
        @Part("nama_tempat") RequestBody nama_tempat,
        @Part("no_rekening") RequestBody no_rekening,
        @Part("slogan_tempat") RequestBody slogan_tempat,
        @Part("deskripsi_tempat") RequestBody deskrisi_tempat,
        @Part("status") RequestBody status
);
//KOSTUM

    @GET("TempatSewa/Kostum/getKategori")
    Call<GetKategori>getKategori();
    @Multipart
    @POST("TempatSewa/Kostum/insertKostum")
    Call<GetKostum>postKostum(
            @Part MultipartBody.Part file,

            @Part("id_kategori") RequestBody id_kategori,
            @Part("id_tempat") RequestBody id_tempat,
            @Part("nama_kostum") RequestBody nama_kostum,
            @Part("jumlah_kostum") RequestBody jumlah_kostum,
            @Part("harga_kostum") RequestBody harga_kostum,
            @Part("deskripsi_kostum") RequestBody deskripsi_kostum
    );
    @Multipart
    @POST("TempatSewa/Kostum/tampilKostum")
    Call<GetKostum>tampilKostum(
            @Part("id_user") RequestBody id_user

    );
    @Multipart
    @POST("TempatSewa/TempatSewa/statusIdentitas")
    Call<GetIdentitas>tampilIdentitas(
            @Part("id_user") RequestBody id_user
    );
    @Multipart
    @POST("TempatSewa/Kostum/updateKostum")
    Call<GetKostum>putKostum(
            @Part MultipartBody.Part file,
            @Part("id_kostum") RequestBody id_kostum,
            @Part("id_kategori") RequestBody id_kategori,
            @Part("nama_kostum") RequestBody nama_kostum,
            @Part("jumlah_kostum") RequestBody jumlah_kostum,
            @Part("harga_kostum") RequestBody harga_kostum,
            @Part("deskripsi_kostum") RequestBody deskripsi_kostum
    );
    @Multipart
    @POST("TempatSewa/Kostum/hapusKostum")
    Call<GetKostum> deleteKostum(
            @Part("id_kostum") RequestBody id_kostum
    );
    //Pemesanan
    @Multipart
    @POST("TempatSewa/Pemesanan/tampilPemesanan")
    Call<GetPemesanan>getPemesanan(
            @Part("id_user") RequestBody id_user
    );
    @Multipart
    @POST("TempatSewa/Pemesanan/getSewa")
    Call<GetPemesanan>getSewa(
            @Part("id_user") RequestBody id_user
    );
    @Multipart
    @POST("TempatSewa/Pemesanan/updateSewaSelesai")
    Call<GetPemesanan>updateSewaSelesai(
            @Part("id_log") RequestBody id_log
    );
    @Multipart
    @POST("TempatSewa/Pemesanan/getRiwayat")
    Call<GetPemesanan>getRiwayat(
            @Part("id_user") RequestBody id_user
    );
    @Multipart
    @POST("TempatSewa/Pemesanan/tampilDetail")
    Call<GetPemesanan>getDetail(
            @Part("id_detail") RequestBody id_detail
    );
    @Multipart
    @POST("TempatSewa/Pemesanan/getKomentar")
    Call<GetKomentar>getKomentar(
            @Part("id_user") RequestBody id_user
    );

    //input denda
    @Multipart
    @POST("TempatSewa/denda/tambahDenda")
    Call<GetDenda>postDenda(
            @Part("id_detail") RequestBody id_detail,
            @Part("jumlah_denda") RequestBody jumlah_denda,
            @Part("keterangan") RequestBody keterangan
    );
    @Multipart
    @POST("TempatSewa/TempatSewa/tampilTempat")
    Call<GetTempat>cekTempat(
            @Part("id_user") RequestBody id_user
    );

    //notif pesan
    @Multipart
    @POST("TempatSewa/Pemesanan/sewaPesan")
    Call<GetPemesanan>hitungPesan(
        @Part("id_user") RequestBody id_user
    );

    @Multipart
    @POST("TempatSewa/Pemesanan/sewaSelesai")
    Call<GetPemesanan>hitungSelesai(
            @Part("id_user") RequestBody id_user
    );
    //login with email
    @FormUrlEncoded
    @POST("TempatSewa/login/loginEmail")
    Call<GetLogin>postEmail(
            @Field("email") String email
    );
}
