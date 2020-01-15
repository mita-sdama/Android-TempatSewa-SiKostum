package com.example.sikostumtempat;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.sikostumtempat.Adapter.AlamatAdapter;
import com.example.sikostumtempat.MODEL.Alamat;
import com.example.sikostumtempat.MODEL.GetAlamat;
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

public class AlamatActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;
    APIInterface mApiInterface;
    String id_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alamat);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        mContext = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        id_user = SaveSharedPreferences.getId(getApplicationContext());
        tampilAlamat();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InsertAlamatActivity.class);
                startActivity(intent);
            }
        });
        }

    public void tampilAlamat(){
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user = MultipartBody.create(MediaType.parse("multipart/form-data"), SaveSharedPreferences.getId(getApplicationContext()));
        Call<GetAlamat> mAlamatCall = mApiInterface.getAlamat(reqid_user);
        mAlamatCall.enqueue(new Callback<GetAlamat>() {
            @Override
            public void onResponse(Call<GetAlamat> call, Response<GetAlamat> response) {
                Log.d("Get Alamat", response.body().getStatus());
                List<Alamat> daftarAlamat = response.body().getResult();
                mAdapter = new AlamatAdapter(daftarAlamat);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetAlamat> call, Throwable t) {
                Log.d("Get Alamat", t.getMessage());
            }
        });

    }
}
