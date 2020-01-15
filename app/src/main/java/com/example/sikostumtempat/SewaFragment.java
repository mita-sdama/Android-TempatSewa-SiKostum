package com.example.sikostumtempat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sikostumtempat.MODEL.GetPemesanan;
import com.example.sikostumtempat.REST.APIClient;
import com.example.sikostumtempat.REST.APIInterface;
import com.example.sikostumtempat.Utils.SaveSharedPreferences;
import com.nex3z.notificationbadge.NotificationBadge;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SewaFragment extends Fragment {
    RelativeLayout pemesanan, sewa;
    NotificationBadge jumlahPesanMasuk, jumlahSewaMasuk;
    APIInterface mApiInterface;
    String id_user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sewa, container, false);
        pemesanan = rootView.findViewById(R.id.pemesanan_menu);
        sewa = rootView.findViewById(R.id.sewa_menu);
        jumlahPesanMasuk = rootView.findViewById(R.id.jumlahPesanMasuk);
        jumlahSewaMasuk = rootView.findViewById(R.id.jumlahSewaMasuk);
        id_user = SaveSharedPreferences.getId(getContext());
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user= MultipartBody.create(MediaType.parse("multipart/form-data"),
                id_user);
        Call<GetPemesanan> mJumlahMasuk= mApiInterface.hitungPesan(reqid_user);
        mJumlahMasuk.enqueue(new Callback<GetPemesanan>() {
            @Override
            public void onResponse(Call<GetPemesanan> call, Response<GetPemesanan> response) {
                if (response.body().getStatus().equals("success")){
                    jumlahPesanMasuk.setText(response.body().getResult().get(0).getJumlahValid());
                }
                else{
                    Toast.makeText(getActivity(),"Gagal Hitung",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GetPemesanan> call, Throwable t) {
                Toast.makeText(getContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();

            }
        });

        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid= MultipartBody.create(MediaType.parse("multipart/form-data"),
                id_user);
        Call<GetPemesanan> mJumlahSewa= mApiInterface.hitungSelesai(reqid);
        mJumlahSewa.enqueue(new Callback<GetPemesanan>() {
            @Override
            public void onResponse(Call<GetPemesanan> call, Response<GetPemesanan> response) {
                if (response.body().getStatus().equals("success")){
                    jumlahSewaMasuk.setText(response.body().getResult().get(0).getJumlahSewa());
                }
                else{
                    Toast.makeText(getActivity(),"Gagal Hitung",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GetPemesanan> call, Throwable t) {
                Toast.makeText(getContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();

            }
        });

        pemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent= new Intent(getContext(),PemesananActivity.class);
                startActivity(mIntent);
            }
        });

        sewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent= new Intent(getContext(),SewaActivity.class);
                startActivity(mIntent);
            }
        });
        return rootView;


    }

}
