package com.example.sikostumtempat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sikostumtempat.Adapter.KostumAdapter;
import com.example.sikostumtempat.MODEL.GetKostum;
import com.example.sikostumtempat.MODEL.GetTempat;
import com.example.sikostumtempat.MODEL.Kostum;
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

public class KostumActivity extends AppCompatActivity {
    APIInterface mApiInterface;
    private FloatingActionButton fab;
    String id_user;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kostum);
        fab=(FloatingActionButton) findViewById(R.id.tambahKostum);
        mContext = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        id_user = SaveSharedPreferences.getId(getApplicationContext());
//        tampilKostum();
            cekTempat();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(),InsertKostumActivity.class);
                startActivity(mIntent);
            }
        });
    }
    public void cekTempat(){
        mApiInterface =APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user = MultipartBody.create(MediaType.parse("multipart/form-data"),
                SaveSharedPreferences.getId(getApplicationContext()));
        Call<GetTempat> mTempat = mApiInterface.cekTempat(reqid_user);
        mTempat.enqueue(new Callback<GetTempat>() {

            @Override
            public void onResponse(Call<GetTempat> call, Response<GetTempat> response) {

                if (response.body().getStatus().equals("success")) {
                tampilKostum();
                }
                else{

                    fab.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Anda Tidak Memiliki Izin untuk menambahkan Kostum",Toast.LENGTH_SHORT).show();

                }
                }

            @Override
            public void onFailure(Call<GetTempat> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void tampilKostum(){
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user = MultipartBody.create(MediaType.parse("multipart/form-data"),
                SaveSharedPreferences.getId(getApplicationContext()));
        Call<GetKostum> mKostumCall = mApiInterface.tampilKostum(reqid_user);
        mKostumCall.enqueue(new Callback<GetKostum>() {
            @Override
            public void onResponse(Call<GetKostum> call, Response<GetKostum> response) {
                Log.d("Get Kostum", response.body().getStatus());
                List<Kostum> daftarKostum = response.body().getResult();
                mAdapter = new KostumAdapter(daftarKostum);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetKostum> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
