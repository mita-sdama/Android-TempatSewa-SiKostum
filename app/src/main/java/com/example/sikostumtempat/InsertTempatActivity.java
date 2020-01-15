package com.example.sikostumtempat;

import android.Manifest;
import android.app.ProgressDialog;
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
import com.example.sikostumtempat.MODEL.Alamat;
import com.example.sikostumtempat.MODEL.GetAlamat;
import com.example.sikostumtempat.MODEL.GetTempat;
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

public class InsertTempatActivity extends AppCompatActivity {
    EditText t_nama, t_noRek, t_slogan, t_deskripsi;

    ImageView fotoTempat;
    Button btFotoTempat,btSimpan;
    Spinner spinnerAlamat,spinnerStatus;
    Context mContext;
    APIInterface mApiInterface;
    ProgressDialog loading;
    Alamat alamatTempat;
    String id_user;
    String imagePath="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_tempat);
        mContext= getApplicationContext();

        t_nama = (EditText) findViewById(R.id.namaTempat);
        t_noRek = (EditText) findViewById(R.id.noRekening);
        t_slogan = (EditText) findViewById(R.id.sloganTempat);
        t_deskripsi = (EditText) findViewById(R.id.deskripsiTempat);
        fotoTempat = (ImageView) findViewById(R.id.fotoTempat);
        spinnerAlamat = (Spinner) findViewById(R.id.spinnerAlamat);
        spinnerStatus = (Spinner) findViewById(R.id.spinnerStatus);
        btFotoTempat = (Button) findViewById(R.id.buttonFotoTempat);
        btSimpan = (Button) findViewById(R.id.buttonSimpanTempat);
        id_user = SaveSharedPreferences.getId(getApplicationContext());

        initSpinnerAlamat();

        btFotoTempat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mintaPermissions();
            }
        });

        btSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alamatTempat = (Alamat) spinnerAlamat.getSelectedItem();
                APIInterface mApiInterface= APIClient.getClient().create(APIInterface.class);
                MultipartBody.Part body =null;
                if(!imagePath.isEmpty()) {
                    // Buat file dari image yang dipilih
                    File file = new File(imagePath);

                    // Buat RequestBody instance dari file
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);

                    // MultipartBody.Part digunakan untuk mendapatkan nama file
                    body = MultipartBody.Part.createFormData("foto_tempat", file.getName(),
                            requestFile);


                    RequestBody reqid_user = MultipartBody.create(parse("multipart/form-data"),
                            id_user);
                    RequestBody reqid_alamat = MultipartBody.create(MediaType.parse("multipart/form-data"),
                            (String.valueOf(alamatTempat.getId())));
                    RequestBody reqnama_tempat = MultipartBody.create(MediaType.parse("multipart/form-data"),
                            (t_nama.getText().toString().isEmpty()) ? "" : t_nama.getText().toString());
                    RequestBody reqno_rekeneing = MultipartBody.create(MediaType.parse("multipart/form-data"),
                            (t_noRek.getText().toString().isEmpty()) ? "" : t_noRek.getText().toString());
                    RequestBody reqslogan_tempat = MultipartBody.create(MediaType.parse("multipart/form-data"),
                            (t_slogan.getText().toString().isEmpty()) ? "" : t_slogan.getText().toString());
                    RequestBody reqdeskripsi_tempat = MultipartBody.create(MediaType.parse("multipart/form-data"),
                            (t_deskripsi.getText().toString().isEmpty()) ? "" : t_deskripsi.getText().toString());
                    RequestBody reqstatus_tempat = MultipartBody.create(MediaType.parse("multipart/form-data"),
                            (spinnerStatus.getSelectedItem().toString()));
                    Call<GetTempat> alamatCall = mApiInterface.posTempat(body, reqid_user, reqid_alamat, reqnama_tempat,
                            reqno_rekeneing, reqslogan_tempat, reqdeskripsi_tempat, reqstatus_tempat);
                    alamatCall.enqueue(new Callback<GetTempat>() {
                        @Override
                        public void onResponse(Call<GetTempat> call, Response<GetTempat> response) {
                            if (response.body().getStatus().equals("fail")) {
                                Toast.makeText(InsertTempatActivity.this, "Gagal insert!", Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(InsertTempatActivity.this, "Sukses insert tempat!", Toast.LENGTH_SHORT).show();
                                Intent mIntent = new Intent(getApplicationContext(), TempatSewaActivity.class);
                                startActivity(mIntent);
                            }
                        }

                        @Override
                        public void onFailure(Call<GetTempat> call, Throwable t) {
                            Log.d("Insert Tempat", t.getMessage());
                        }
                    });
                }
            }
        });
    }

    private void initSpinnerAlamat(){

        mApiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user= MultipartBody.create(MediaType.parse("multipart/form-data"),
                (id_user));
        Call<GetAlamat>mAlamat=mApiInterface.getAlm(reqid_user);
        mAlamat.enqueue(new Callback<GetAlamat>() {
            @Override
            public void onResponse(Call<GetAlamat> call, Response<GetAlamat> response) {
                if(response.body().getStatus().equals("success")){
                    final List<Alamat> alamatItem = response.body().getResult();
                    List<String> listSpinner= new ArrayList<String>();
                    for(int i=0; i<alamatItem.size();i++){
                        listSpinner.add(alamatItem.get(i).getAlamat());
                    }
                    final ArrayAdapter<Alamat> adapter = new ArrayAdapter<>(InsertTempatActivity.this,
                            android.R.layout.simple_spinner_item, alamatItem);
                    spinnerAlamat.setAdapter(adapter);

                    spinnerAlamat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (!spinnerAlamat.getSelectedItem().toString().equals(getString(R.string.txt_please_slct))) {
                                String selectAlamat = alamatItem.get(position).getId();
                                Toast.makeText(mContext, "Kamu memilih alamat" + selectAlamat, Toast.LENGTH_SHORT);

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
            public void onFailure(Call<GetAlamat> call, Throwable t) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(InsertTempatActivity.this);
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

                    Glide.with(mContext).load(new File(imagePath)).into(fotoTempat);

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

            Glide.with(mContext).load(new File(imagePath)).into(fotoTempat);
        }

    }
    public String simpanImage(Bitmap myBitmap){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        // Kualitas gambar yang disimpan
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        // Buat object direktori file
        File lokasiImage = new File(
                Environment.getExternalStorageDirectory() + "/tempat_sewa");

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

            Log.d("tempat_sewa", "File tersimpan di --->" + f.getAbsolutePath());

            // Return file
            return f.getAbsolutePath();

        } catch (IOException e1) {
            Log.d("tempat_sewa", "error");
            e1.printStackTrace();
        }
        return "";
    }

}
