package com.example.sikostumtempat;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sikostumtempat.MODEL.GetIdentitas;
import com.example.sikostumtempat.MODEL.GetTempat;
import com.example.sikostumtempat.REST.APIClient;
import com.example.sikostumtempat.REST.APIInterface;
import com.example.sikostumtempat.Utils.SaveSharedPreferences;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class TempatSewaActivity extends AppCompatActivity {
    private Button editTempat;
    Button insertTempat;
    APIInterface mAPIInterface;
    TextView idTempat, idAlamat, namaTempat, noRekening, alamatTempat,sloganTempat,deskripsiTempat,statusTempat, izinTempat;
    String id_user;
    String url_photo, photoName;
    ImageView fotoTempat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempat_sewa);

    //inisial tombol
    editTempat =(Button)findViewById(R.id.buttonEditTempat);
    insertTempat = (Button) findViewById(R.id.buttonInsertTempat);
    id_user = SaveSharedPreferences.getId(getApplicationContext());
    idTempat =(TextView)findViewById(R.id.idTempat);
    noRekening = (TextView) findViewById(R.id.noRekening);
    fotoTempat = (ImageView) findViewById(R.id.fotoTempat);
    namaTempat = (TextView) findViewById(R.id.namaTempat);
    alamatTempat = (TextView) findViewById(R.id.alamatTempat);
    sloganTempat = (TextView) findViewById(R.id.sloganTempat);
    deskripsiTempat = (TextView) findViewById(R.id.deskripsiTempat);
    statusTempat = (TextView) findViewById(R.id.statusTempat);
    izinTempat = (TextView) findViewById(R.id.izinTempat);
    idAlamat = (TextView) findViewById(R.id.idAlamatku);
    getIdentitas();

    //function button edit
        editTempat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(), EditTempatActivity.class);
                mIntent.putExtra("id_tempat", idTempat.getText().toString());
                mIntent.putExtra("nama_tempat", namaTempat.getText().toString());
                mIntent.putExtra("no_rekening", noRekening.getText().toString());
                mIntent.putExtra("id_alamat", idAlamat.getText().toString());
                mIntent.putExtra("alamat_tempat", alamatTempat.getText().toString());
                mIntent.putExtra("slogan_tempat", sloganTempat.getText().toString());
                mIntent.putExtra("deskripsi_tempat", deskripsiTempat.getText().toString());
                mIntent.putExtra("status", statusTempat.getText().toString());
                mIntent.putExtra("foto_tempat", photoName);
                mIntent.putExtra("foto_tempat_url", url_photo);
                startActivity(mIntent);
            }
        });

        //function button insert
        insertTempat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent mIntent = new Intent(getApplicationContext(), InsertTempatActivity.class);
            startActivity(mIntent);
            }
        });

        //scrollview
        ScrollView sView =(ScrollView) findViewById(R.id.kelola_tempat);
        sView.setVerticalScrollBarEnabled(false);
        sView.setHorizontalScrollBarEnabled(false);

        ActionBar menu =getSupportActionBar();
        menu.setDisplayHomeAsUpEnabled(true);
        menu.setDisplayShowHomeEnabled(true);
    }

    //mendapatkan data identitas
    public void getIdentitas(){
    mAPIInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody req_id_user = MultipartBody.create(MediaType.parse("multipart/form-data"),(id_user));
    retrofit2.Call<GetIdentitas> mIdentitasCall = mAPIInterface.tampilIdentitas(req_id_user);
    mIdentitasCall.enqueue(new retrofit2.Callback<GetIdentitas>(){

        @Override
        public void onResponse(Call<GetIdentitas> call, Response<GetIdentitas> response) {
            if (response.body().getStatus().equals("success")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TempatSewaActivity.this);
            //set tittle dialog
                alertDialogBuilder.setTitle("Cek Identitas Anda terlebih dahulu untuk dapat kelola toko");
                //set pesan dialog
            alertDialogBuilder.setCancelable(false).setNeutralButton("Ok", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int i) {
                    TempatSewaActivity.this.finish();
                }
            });

            //membuat alert dialog dari builder
            AlertDialog alertDialog = alertDialogBuilder.create();
            //menampilkan alert dialog
            alertDialog.show();
            }else{
                //menampilkan data tempat sewa
            getData();
            }
        }

        @Override
        public void onFailure(Call<GetIdentitas> call, Throwable t) {

        }
    });
    }

        public void getData(){
        mAPIInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user =MultipartBody.create(MediaType.parse("multipart/form-data"),(id_user));
        retrofit2.Call<GetTempat> mTempatCall = mAPIInterface.getTempat(reqid_user);
        mTempatCall.enqueue(new retrofit2.Callback<GetTempat>(){

            @Override
            public void onResponse(Call<GetTempat> call, Response<GetTempat> response) {
            Log.d("My Tempat Sewa", response.body().getStatus());
            if (response.body().getStatus().equals("success")){
            insertTempat.setVisibility(View.GONE);
            editTempat.setVisibility(View.VISIBLE);
            idTempat.setText(response.body().getResult().get(0).getId_tempat());
            namaTempat.setText(response.body().getResult().get(0).getNama_tempat());
            url_photo = APIClient.BASE_URL+"uploads/"+response.body().getResult().get(0).getFoto_tempat();
            photoName = response.body().getResult().get(0).getFoto_tempat();

            if (photoName != null){
                Glide.with(getApplicationContext()).load(url_photo).into(fotoTempat);
            }else{
                Glide.with(getApplicationContext()).load(R.drawable.splash_toko).into(fotoTempat);
            }
            noRekening.setText(response.body().getResult().get(0).getNo_rekening());
            idAlamat.setText(response.body().getResult().get(0).getId_alamat());
            alamatTempat.setText(response.body().getResult().get(0).getAlamat());
            sloganTempat.setText(response.body().getResult().get(0).getSlogan_tempat());
            deskripsiTempat.setText(response.body().getResult().get(0).getDeskripsi_tempat());
            statusTempat.setText(response.body().getResult().get(0).getStatus_tempat());
            izinTempat.setText(response.body().getResult().get(0).getIzin());
            if(response.body().getResult().get(0).getIzin().equals("ya")){
                izinTempat.setText("disetujui");
            }else{
                izinTempat.setText("tidak disetujui");
            }
            }else if(response.body().getStatus().equals("kosong")){
                insertTempat.setVisibility(View.VISIBLE);
                editTempat.setVisibility(View.GONE);
                idTempat.setText(" ");
                namaTempat.setText("Nama Tempat Sewa ");
                Glide.with(getApplicationContext()).load(R.drawable.splash_toko).into(fotoTempat);


            }else{
                Toast.makeText(TempatSewaActivity.this, "Gagal medapatkan data tempat sewa", Toast.LENGTH_SHORT).show();
            }
            }

            @Override
            public void onFailure(Call<GetTempat> call, Throwable t) {

            }
        });
        }
}
