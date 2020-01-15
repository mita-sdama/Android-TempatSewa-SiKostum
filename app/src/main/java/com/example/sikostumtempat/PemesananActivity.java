package com.example.sikostumtempat;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.sikostumtempat.Adapter.PemesananAdapter;
import com.example.sikostumtempat.MODEL.GetPemesanan;
import com.example.sikostumtempat.MODEL.Pemesanan;
import com.example.sikostumtempat.REST.APIClient;
import com.example.sikostumtempat.REST.APIInterface;
import com.example.sikostumtempat.Utils.SaveSharedPreferences;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PemesananActivity extends AppCompatActivity {
    String id_user;
    APIInterface mApiInterface;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan);
        mContext = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        id_user = SaveSharedPreferences.getId(getApplicationContext());
        tampilPemesanan();
    }

    private void tampilPemesanan(){
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user = MultipartBody.create(MediaType.parse("multipart/form-data"),
                SaveSharedPreferences.getId(getApplicationContext()));
        Call<GetPemesanan> mPemesananCall = mApiInterface.getPemesanan(reqid_user);
        mPemesananCall.enqueue(new Callback<GetPemesanan>() {
            @Override
            public void onResponse(Call<GetPemesanan> call, Response<GetPemesanan> response) {
                Log.d("Get Pemesanan", response.body().getStatus());
                List<Pemesanan> daftarPemesanan = response.body().getResult();
                mAdapter = new PemesananAdapter(daftarPemesanan);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetPemesanan> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
