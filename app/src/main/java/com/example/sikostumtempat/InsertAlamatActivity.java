package com.example.sikostumtempat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sikostumtempat.MODEL.Data;
import com.example.sikostumtempat.MODEL.GetAlamat;
import com.example.sikostumtempat.MODEL.Region;
import com.example.sikostumtempat.MODEL.UniqueCode;
import com.example.sikostumtempat.REST.APIClient;
import com.example.sikostumtempat.REST.APIInterface;
import com.example.sikostumtempat.REST.APIWilayah;
import com.example.sikostumtempat.REST.WilayahInterface;
import com.example.sikostumtempat.Utils.SaveSharedPreferences;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static okhttp3.MediaType.parse;

public class InsertAlamatActivity extends AppCompatActivity {

    EditText label_alamat, alamat, kota;
    Button simpan;
    Context mContext;
    TextView tvMessage;
    String id_user;

    Region provinsiDipilih;
    Region kotaDipilih;
    Region kecamatanDipilih;
    Region kelurahanDipilih;

    private Spinner spinnerProv;
    private Spinner spinnerKab;
    private Spinner spinnerKec;
    private Spinner spinnerKel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_alamat);

        mContext = getApplicationContext();

        label_alamat= (EditText) findViewById(R.id.editTextLabel);
        alamat = (EditText) findViewById(R.id.editTextAlamat);
        simpan = (Button) findViewById(R.id.buttonSimpan);
        tvMessage =(TextView) findViewById(R.id.tvMessage);
        id_user = SaveSharedPreferences.getId(getApplicationContext());

        spinnerProv = findViewById(R.id.spinner_prov);
        spinnerKab = findViewById(R.id.spinner_kab);
        spinnerKec = findViewById(R.id.spinner_kec);
        spinnerKel = findViewById(R.id.spinner_kel);

        loadUniqueCode();

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                provinsiDipilih = (Region) spinnerProv.getSelectedItem();
                kotaDipilih = (Region) spinnerKab.getSelectedItem();
                kecamatanDipilih = (Region) spinnerKec.getSelectedItem();
                kelurahanDipilih = (Region) spinnerKel.getSelectedItem();
                APIInterface mAPIInterface = APIClient.getClient().create(APIInterface.class);

                RequestBody reqid_user =
                        MultipartBody.create(parse("multipart/form-data"),
                                id_user);
                RequestBody reqlabel_alamat = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (label_alamat.getText().toString().isEmpty()) ? "" : label_alamat.getText().toString());
                RequestBody reqalamat = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (alamat.getText().toString().isEmpty()) ? "" : alamat.getText().toString());
                RequestBody reqprovinsi = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (String.valueOf(provinsiDipilih.getId())));
                RequestBody reqkota= MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (String.valueOf(kotaDipilih.getId())));
                RequestBody reqkecamatan = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (String.valueOf(kecamatanDipilih.getId())));
                RequestBody reqdesa = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (String.valueOf(kelurahanDipilih.getId())));
                RequestBody reqAction = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        "insert");
                Call<GetAlamat> malamatCall = mAPIInterface.postAlamat(reqid_user, reqlabel_alamat,reqalamat, reqprovinsi,
                        reqkota, reqkecamatan, reqdesa,reqAction);
                malamatCall.enqueue(new Callback<GetAlamat>() {
                    @Override
                    public void onResponse(Call<GetAlamat> call, Response<GetAlamat> response) {
                        if (response.body().getStatus().equals("failed")) {
                            tvMessage.setText("Retrofit Insert \n Status = " + response.body()
                                    .getStatus() + "\n" +
                                    "Message = " + response.body().getMessage() + "\n");

                        } else {
                            String detail = "\n" +
                                    "id_user = " + response.body().getResult().get(0).getId_user() + "\n" +
                                    "label_alamat = " + response.body().getResult().get(0).getLabel_alamat() + "\n" +
                                    "alamat = "+response.body().getResult().get(0).getAlamat() + "\n"+
                                    "kota = " + response.body().getResult().get(0).getKota()
                                    + "\n";
                            tvMessage.setText("Retrofit Insert \n Status = " + response.body().getStatus() + "\n" +
                                    "Message = " + response.body().getMessage() + detail);
                            Intent intent = new Intent(getApplicationContext(), AlamatActivity.class);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onFailure(Call<GetAlamat> call, Throwable t) {
                        tvMessage.setText("Retrofit Insert Failure \n Status = " + t.getMessage
                                ());
                    }
                });
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

                // masukkan daftar provinsi ke list string
                List<String> provs = new ArrayList<>();
                // isi data pertama dengan string 'Silakan Pilih!'
                provs.add(0, getString(R.string.txt_please_slct));
                for (int i = 0; i < daftarProvinsi.size(); i++) {
                    provs.add(daftarProvinsi.get(i).getName());
                }

                final ArrayAdapter<Region> adapter = new ArrayAdapter<>(InsertAlamatActivity.this,
                        android.R.layout.simple_spinner_item, daftarProvinsi);
                spinnerProv.setAdapter(adapter);

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
        Call<Data> call = apiService.getKabupatenList(code, idProv);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                final List<Region> daftarKabupaten = response.body().getData();

                // masukkan daftar kabupaten ke list string
                List<String> kabs = new ArrayList<>();
                // isi data pertama dengan string 'Silakan Pilih!'
                kabs.add(0, getString(R.string.txt_please_slct));
                for (int i = 0; i < daftarKabupaten.size(); i++) {
                    kabs.add(daftarKabupaten.get(i).getName());
                }

                // masukkan daftar kabupaten ke spinner
                final ArrayAdapter<Region> adapter = new ArrayAdapter<>(InsertAlamatActivity.this,
                        android.R.layout.simple_spinner_item, daftarKabupaten);
                spinnerKab.setAdapter(adapter);

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

                // masukkan daftar kecamatan ke list string
                List<String> kecs = new ArrayList<>();
                // isi data pertama dengan string 'Silakan Pilih!'
                kecs.add(0, getString(R.string.txt_please_slct));
                for (int i = 0; i < daftarKecamatan.size(); i++) {
                    kecs.add(daftarKecamatan.get(i).getName());
                }

                // masukkan daftar kecamatan ke spinner
                final ArrayAdapter<Region> adapter = new ArrayAdapter<>(InsertAlamatActivity.this,
                        android.R.layout.simple_spinner_item, daftarKecamatan);
                spinnerKec.setAdapter(adapter);

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

                // masukkan daftar kelurahan ke list string
                List<String> kels = new ArrayList<>();
                // isi data pertama dengan string 'Silakan Pilih!'
                kels.add(0, getString(R.string.txt_please_slct));
                for (int i = 0; i < daftarKelurahan.size(); i++) {
                    kels.add(daftarKelurahan.get(i).getName());
                }

                // masukkan daftar kelurahan ke spinner
                final ArrayAdapter<Region> adapter = new ArrayAdapter<>(InsertAlamatActivity.this,
                        android.R.layout.simple_spinner_item, daftarKelurahan);
                spinnerKel.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }
}