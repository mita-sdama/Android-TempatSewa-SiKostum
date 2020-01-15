package com.example.sikostumtempat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sikostumtempat.MODEL.Data;
import com.example.sikostumtempat.MODEL.GetAlamat;
import com.example.sikostumtempat.MODEL.GetDeleteAlamat;
import com.example.sikostumtempat.MODEL.Region;
import com.example.sikostumtempat.MODEL.UniqueCode;
import com.example.sikostumtempat.REST.APIClient;
import com.example.sikostumtempat.REST.APIInterface;
import com.example.sikostumtempat.REST.APIWilayah;
import com.example.sikostumtempat.REST.WilayahInterface;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAlamatActivity extends AppCompatActivity {
    Context mContext;
    EditText edLabel, edAlamat, edKota;
    TextView tvMsg;
    Button btUpdateAl, btDeleteAl;
    APIInterface mAPIInterface;
    private Spinner spinnerProv;
    private Spinner spinnerKab;
    private Spinner spinnerKec;
    private Spinner spinnerKel;
    public Intent mIntent;
    Region provinsiDipilih;
    Region kotaDipilih;
    Region kecamatanDipilih;
    Region kelurahanDipilih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alamat);
        mContext = getApplicationContext();

        edLabel = (EditText) findViewById(R.id.edLabel);
        edAlamat = (EditText) findViewById(R.id.edAlamat);
        spinnerProv = findViewById(R.id.spinner_prov);
        spinnerKab = findViewById(R.id.spinner_kab);
        spinnerKec = findViewById(R.id.spinner_kec);
        spinnerKel = findViewById(R.id.spinner_kel);
        btUpdateAl = (Button) findViewById(R.id.bt_updateAl);
        btDeleteAl = (Button) findViewById(R.id.bt_hapusAl);
        tvMsg = (TextView) findViewById(R.id.tvMssgAl);


        mIntent = getIntent();
        edLabel.setText(mIntent.getStringExtra("label_alamat"));
        edAlamat.setText(mIntent.getStringExtra("alamat"));


        loadUniqueCode();

        final APIInterface mAPIInterface = APIClient.getClient().create(APIInterface.class);

        btUpdateAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                provinsiDipilih = (Region) spinnerProv.getSelectedItem();
                kotaDipilih = (Region) spinnerKab.getSelectedItem();
                kecamatanDipilih = (Region) spinnerKec.getSelectedItem();
                kelurahanDipilih = (Region) spinnerKel.getSelectedItem();

                RequestBody reqid_alamat = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (mIntent.getStringExtra("id_alamat")));
                RequestBody reqlabel_alamat =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edLabel.getText().toString().isEmpty()) ?
                                        "" : edLabel.getText().toString());
                RequestBody reqalamat =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edAlamat.getText().toString().isEmpty()) ?
                                        "" : edAlamat.getText().toString());
                RequestBody reqprovinsi=
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (String.valueOf(provinsiDipilih.getId())));
                RequestBody reqkota =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (String.valueOf(kotaDipilih.getId())));
                RequestBody reqkecamatan=

                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (String.valueOf(kecamatanDipilih.getId())));
                RequestBody reqdesa=

                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (String.valueOf(kelurahanDipilih.getId())));

                Call<GetAlamat> callUpdate = mAPIInterface.putAlamat(reqid_alamat, reqlabel_alamat, reqalamat,
                        reqprovinsi, reqkota, reqkecamatan, reqdesa
                );
                callUpdate.enqueue(new Callback<GetAlamat>() {
                    @Override
                    public void onResponse(Call<GetAlamat> call, Response<GetAlamat> response) {
                        Log.d("EditTempat", response.body().getStatus());
                        if (response.body().getStatus().equals("failed")){
                            Toast.makeText(EditAlamatActivity.this, "Gagal Edit", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(EditAlamatActivity.this, "Berhasil Update", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetAlamat> call, Throwable t) {
                        tvMsg.setText("Retrofit Update \n Status = " + t.getMessage());
                    }
                });
            }
        });
        btDeleteAl.setOnClickListener(new View.OnClickListener() {
            final APIInterface mAPIInterface = APIClient.getClient().create(APIInterface.class);


            @Override
            public void onClick(View view) {
                RequestBody reqid_alamat = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (mIntent.getStringExtra("id_alamat")));
                Call<GetDeleteAlamat> mDeleteAlamatCall = mAPIInterface.deleteAlamat(reqid_alamat);
                mDeleteAlamatCall.enqueue(new Callback<GetDeleteAlamat>() {
                    @Override
                    public void onResponse(Call<GetDeleteAlamat> call, Response<GetDeleteAlamat> response) {
                        if (response.body().getStatus().equals("failed")) {
                            Toast.makeText(EditAlamatActivity
                                    .this, "Gagal Hapus", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditAlamatActivity.this, "Berhasil Hapus", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetDeleteAlamat> call, Throwable t) {
                        Toast.makeText(EditAlamatActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(getApplicationContext(), AlamatActivity.class);
                startActivity(intent);
            }
        });


    }

    private void loadUniqueCode() {

        WilayahInterface apiService = APIWilayah.getClient().create(WilayahInterface.class);
        Call<UniqueCode> call = apiService.getUniqueCode();

        call.enqueue(new Callback<UniqueCode>() {
            @Override
            public void onResponse(Call<UniqueCode> call, Response<UniqueCode> response) {
                String code = "MeP7c5ne" + response.body().getUniqueCode();
                loadProvinceList(code);
            }

            @Override
            public void onFailure(Call<UniqueCode> call, Throwable t) {

            }
        });
    }

    public void loadProvinceList(final String code) {
        WilayahInterface apiService = APIWilayah.getClient().create(WilayahInterface.class);

        Call<Data> call = apiService.getProvinceList(code);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                final List<Region> daftarProvinsi = response.body().getData();
                int indexPilih = 0;
                // masukkan daftar provinsi ke list string
                List<String> provs = new ArrayList<>();
                // isi data pertama dengan string 'Silakan Pilih!'
                provs.add(0, getString(R.string.txt_please_slct));
                for (int i = 0; i < daftarProvinsi.size(); i++) {
                    if (daftarProvinsi.get(i).id == Long.valueOf(mIntent.getStringExtra("provinsi"))){
                        indexPilih = i;
                    }
                }

                final ArrayAdapter<Region> adapter = new ArrayAdapter<>(EditAlamatActivity.this,
                        android.R.layout.simple_spinner_item, daftarProvinsi);
                spinnerProv.setAdapter(adapter);

                spinnerProv.setSelection(indexPilih);

                spinnerProv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (!spinnerProv.getSelectedItem().toString().equals(getString(R.string.txt_please_slct))) {
                            long idProv = daftarProvinsi.get(position).getId();
                            loadKabupatenList(code, idProv);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }

    public void loadKabupatenList(final String code, final long idProv) {
        spinnerKec.setAdapter(null);
        spinnerKel.setAdapter(null);
        WilayahInterface apiService = APIWilayah.getClient().create(WilayahInterface.class);
//        Call<Data> call = apiService.getKabupatenList(code, Long.valueOf(mIntent.getStringExtra("provinsi")));
        Call<Data> call = apiService.getKabupatenList(code, idProv);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                final List<Region> daftarKabupaten = response.body().getData();
                int indexPilih = 0;
                // masukkan daftar kabupaten ke list string
                List<String> kabs = new ArrayList<>();
                // isi data pertama dengan string 'Silakan Pilih!'
                kabs.add(0, getString(R.string.txt_please_slct));
                for (int i = 0; i < daftarKabupaten.size(); i++) {
                    if (daftarKabupaten.get(i).id == Long.valueOf(mIntent.getStringExtra("kota"))){
                        indexPilih = i;
                    }
                }

                // masukkan daftar kabupaten ke spinner
                final ArrayAdapter<Region> adapter = new ArrayAdapter<>(EditAlamatActivity.this,
                        android.R.layout.simple_spinner_item, daftarKabupaten);
                spinnerKab.setAdapter(adapter);

                spinnerKab.setSelection(indexPilih);

                spinnerKab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (!spinnerKab.getSelectedItem().toString().equals(getString(R.string.txt_please_slct))) {
                            long idKab = daftarKabupaten.get(position).getId();
                            loadKecamatanList(code, idKab);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }

    public void loadKecamatanList(final String code, long idKab) {
        spinnerKel.setAdapter(null);
        WilayahInterface apiService = APIWilayah.getClient().create(WilayahInterface.class);
        Call<Data> call = apiService.getKecamatanList(code, idKab);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                final List<Region> daftarKecamatan = response.body().getData();
                int indexPilih = 0;
                // masukkan daftar kecamatan ke list string
                List<String> kecs = new ArrayList<>();
                // isi data pertama dengan string 'Silakan Pilih!'
                kecs.add(0, getString(R.string.txt_please_slct));
                for (int i = 0; i < daftarKecamatan.size(); i++) {
                    if (daftarKecamatan.get(i).id == Long.valueOf(mIntent.getStringExtra("kecamatan"))){
                        indexPilih = i;
                    }
                }

                // masukkan daftar kecamatan ke spinner
                final ArrayAdapter<Region> adapter = new ArrayAdapter<>(EditAlamatActivity.this,
                        android.R.layout.simple_spinner_item, daftarKecamatan);
                spinnerKec.setAdapter(adapter);

                spinnerKec.setSelection(indexPilih);

                spinnerKec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (!spinnerKec.getSelectedItem().toString().equals(getString(R.string.txt_please_slct))) {
                            long idKec = daftarKecamatan.get(position).getId();
                            loadKelurahanList(code, idKec);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }

    public void loadKelurahanList(final String code, final long idKec) {
        WilayahInterface apiService = APIWilayah.getClient().create(WilayahInterface.class);
        Call<Data> call = apiService.getKelurahanList(code, idKec);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                final List<Region> daftarKelurahan = response.body().getData();
                int indexPilih = 0;
                // masukkan daftar kelurahan ke list string
                List<String> kels = new ArrayList<>();
                // isi data pertama dengan string 'Silakan Pilih!'
                kels.add(0, getString(R.string.txt_please_slct));
                for (int i = 0; i < daftarKelurahan.size(); i++) {
                    if (daftarKelurahan.get(i).id == Long.valueOf(mIntent.getStringExtra("desa"))){
                        indexPilih = i;
                    }
                }

                // masukkan daftar kelurahan ke spinner
                final ArrayAdapter<Region> adapter = new ArrayAdapter<>(EditAlamatActivity.this,
                        android.R.layout.simple_spinner_item, daftarKelurahan);
                spinnerKel.setAdapter(adapter);

                spinnerKel.setSelection(indexPilih);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });


    }
}