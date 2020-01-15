package com.example.sikostumtempat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sikostumtempat.MODEL.GetKategori;
import com.example.sikostumtempat.MODEL.GetKostum;
import com.example.sikostumtempat.MODEL.GetTempat;
import com.example.sikostumtempat.MODEL.Kategori;
import com.example.sikostumtempat.MODEL.Region;
import com.example.sikostumtempat.REST.APIClient;
import com.example.sikostumtempat.REST.APIInterface;
import com.example.sikostumtempat.Utils.SaveSharedPreferences;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static okhttp3.MediaType.parse;

public class InsertKostumActivity extends AppCompatActivity {
    EditText namaKostum, jumlahKostum, hargaKostum, deskripsiKostum;
    TextView tvIdTem;
    ImageView fotoKostum;
    Spinner kategori;
    Button ambilFoto, simpanKostum;
    Context mContext;
    Kategori kategoriKostum;
    APIInterface mApiInterface;
    String imagePath="";
    String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_kostum);
        mContext= getApplicationContext();
        tvIdTem =(TextView) findViewById(R.id.tvIdTempat);
        fotoKostum =(ImageView) findViewById(R.id.fotoKostum);
        namaKostum = (EditText) findViewById(R.id.eNamaKostum);
        jumlahKostum = (EditText) findViewById(R.id.eJumlahKostum);
        hargaKostum = (EditText) findViewById(R.id.eHargaKostum);
        deskripsiKostum = (EditText) findViewById(R.id.eDeskripsiKostum);
        kategori = (Spinner) findViewById(R.id.spinnerKategori);
        simpanKostum = (Button) findViewById(R.id.buttonSimpanKostum);
        ambilFoto = (Button) findViewById(R.id.buttonKostum);
        id_user = SaveSharedPreferences.getId(getApplicationContext());



        getIdTempat();
        initSpinnerKategori();

        ambilFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mintaPermissions();
            }
        });
        simpanKostum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kategoriKostum = (Kategori) kategori.getSelectedItem();
                APIInterface mApiInterface = APIClient.getClient().create(APIInterface.class);
                MultipartBody.Part body =null;
                if(!imagePath.isEmpty()){
                    // Buat file dari image yang dipilih
                    File file = new File(imagePath);

                    // Buat RequestBody instance dari file
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);

                    // MultipartBody.Part digunakan untuk mendapatkan nama file
                    body = MultipartBody.Part.createFormData("foto_kostum", file.getName(),
                            requestFile);

                    RequestBody reqid_kategori =MultipartBody.create(MediaType.parse("multipart/form-data"),
                            (String.valueOf(kategoriKostum.getId())));
//                            (kategori.getSelectedItem().toString()));
                    RequestBody reqid_tempat = MultipartBody.create(MediaType.parse("multipart/form-data"),
                            tvIdTem.getText().toString());
                    RequestBody reqnama_kostum = MultipartBody.create(MediaType.parse("multipart/form-data"),
                            (namaKostum.getText().toString().isEmpty()) ? "" : namaKostum.getText().toString());
                    RequestBody reqjumlah_kostum = MultipartBody.create(MediaType.parse("multipart/form-data"),
                            (jumlahKostum.getText().toString().isEmpty()) ? "" : jumlahKostum.getText().toString());
                    RequestBody reqharga_kostum= MultipartBody.create(MediaType.parse("multipart/form-data"),
                            (hargaKostum.getText().toString().isEmpty()) ? "" : hargaKostum.getText().toString());
                    RequestBody reqdeskripsi_kostum= MultipartBody.create(MediaType.parse("multipart/form-data"),
                            (deskripsiKostum.getText().toString().isEmpty()) ? "" : deskripsiKostum.getText().toString());
                    Call<GetKostum> kostumCall =mApiInterface.postKostum(body,reqid_kategori,reqid_tempat,reqnama_kostum,reqjumlah_kostum,
                            reqharga_kostum,reqdeskripsi_kostum);
                    kostumCall.enqueue(new Callback<GetKostum>() {
                        @Override
                        public void onResponse(Call<GetKostum> call, Response<GetKostum> response) {
                            if (response.body().getStatus().equals("failed")){
                                Toast.makeText(InsertKostumActivity.this, "Gagal insert!", Toast.LENGTH_SHORT).show();
                            }else{

                                Toast.makeText(InsertKostumActivity.this, "Sukses insert kostum!", Toast.LENGTH_SHORT).show();
                                Intent mIntent = new Intent(getApplicationContext(), KostumActivity.class);
                                startActivity(mIntent);
                            }
                        }

                        @Override
                        public void onFailure(Call<GetKostum> call, Throwable t) {
                            Log.d("Insert Kostum", t.getMessage());
                        }
                    });
                }
            }
        });

    }
    public  void getIdTempat(){
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user =
                MultipartBody.create(parse("multipart/form-data"),
                        id_user);
        Call<GetTempat> mTempatCall = mApiInterface.getTempat(reqid_user);
        mTempatCall.enqueue(new Callback<GetTempat>() {
            @Override
            public void onResponse(Call<GetTempat> call, Response<GetTempat> response) {
                if (response.body().getStatus().equals("success")){
                    tvIdTem.setText(response.body().getResult().get(0).getId_tempat());
                }else{
                    Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetTempat> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void initSpinnerKategori(){
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        Call<GetKategori> mKategori=mApiInterface.getKategori();
        mKategori.enqueue(new Callback<GetKategori>() {
            @Override
            public void onResponse(Call<GetKategori> call, Response<GetKategori> response) {
                if(response.body().getStatus().equals("success")){
                    final List<Kategori> kategoriItem = response.body().getResult();
                    List<String> listSpinner= new ArrayList<String>();
                    for(int i=0; i<kategoriItem.size();i++){
                        listSpinner.add(kategoriItem.get(i).getNama_kategori());
                    }
                    final ArrayAdapter<Kategori> adapter = new ArrayAdapter<>(InsertKostumActivity.this,
                            android.R.layout.simple_spinner_item, kategoriItem);
                    kategori.setAdapter(adapter);

                    kategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (!kategori.getSelectedItem().toString().equals(getString(R.string.txt_please_slct))) {
                                String selectKategori = kategoriItem.get(position).getId();
                                Toast.makeText(mContext, "Kamu memilih kategori" + selectKategori, Toast.LENGTH_SHORT);

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }else{

                    Toast.makeText(mContext,"Gagal mengambil data alamat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetKategori> call, Throwable t) {
                Toast.makeText(mContext,"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void mintaPermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {

                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // Cek apakah semua permission yang diperlukan sudah diijinkan
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(),
                                    "Semua permissions diijinkan!", Toast.LENGTH_SHORT).show();
                            tampilkanFotoDialog();
                        }

                        // Cek apakah ada permission yang tidak diijinkan
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // Info user untuk mengubah setting permission
                            tampilkanSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(),
                                "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void tampilkanFotoDialog() {
        AlertDialog.Builder fotoDialog = new AlertDialog.Builder(this);
        fotoDialog.setTitle("Select Action");
        // Isi opsi dialog
        String[] fotoDialogItems = {
                "Pilih foto dari gallery",
                "Ambil dari kamera"};
        fotoDialog.setItems(fotoDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pilihan) {
                        switch (pilihan) {
                            case 0:
                                pilihDariGallery();
                                break;
                            case 1:
                                ambilDariCamera();
                                break;
                        }
                    }
                });
        fotoDialog.show();
    }

    public void pilihDariGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, 13);
    }

    private void ambilDariCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(cameraIntent, 16);
    }

    private void tampilkanSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(InsertKostumActivity.this);
        builder.setTitle("Butuh Permission");
        builder.setMessage("Aplikasi ini membutuhkan permission khusus, mohon ijin.");
        builder.setPositiveButton("BUKA SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                bukaSettings();
            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();


    }

    private void bukaSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        // Jika request berasal dari Gallery
        if (requestCode == 13) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    imagePath = simpanImage(bitmap);
                    Toast.makeText(mContext, "Foto berhasil di-load!", Toast.LENGTH_SHORT).show();

                    Glide.with(mContext).load(new File(imagePath)).into(fotoKostum);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Foto gagal di-load!", Toast.LENGTH_SHORT).show();
                }
            }

            // Jika request dari Camera
        } else if (requestCode == 16) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imagePath = simpanImage(thumbnail);
            Toast.makeText(mContext, "Foto berhasil di-load dari Camera!", Toast.LENGTH_SHORT)
                    .show();

            Glide.with(mContext).load(new File(imagePath)).into(fotoKostum);
        }

    }
    public String simpanImage(Bitmap myBitmap){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        // Kualitas gambar yang disimpan
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        // Buat object direktori file
        File lokasiImage = new File(
                Environment.getExternalStorageDirectory() + "/kostum");

        // Buat direktori untuk penyimpanan
        if (!lokasiImage.exists()) {
            lokasiImage.mkdirs();
        }

        try {
            // Untuk penamaan file
            File f = new File(lokasiImage, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();

            // Operasi file
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();

            Log.d("kostum", "File tersimpan di --->" + f.getAbsolutePath());

            // Return file
            return f.getAbsolutePath();

        } catch (IOException e1) {
            Log.d("kostum", "error");
            e1.printStackTrace();
        }
        return "";
    }
}
