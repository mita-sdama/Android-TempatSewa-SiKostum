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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sikostumtempat.MODEL.GetIdentitas;
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
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static okhttp3.MediaType.parse;

public class InsertIdentitasActivity extends AppCompatActivity {
    ImageView mImageView;
    Context mContext;
    Button bt_camera, bt_simpan;
    TextView tv_message;
    String imagePath="";
    String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_identitas);

        mContext = getApplicationContext();
        mImageView = (ImageView) findViewById(R.id.foto_ktp);
        bt_camera = (Button) findViewById(R.id.btn_camera);

        bt_simpan = (Button) findViewById(R.id.bt_simpan);
        tv_message = (TextView) findViewById(R.id.tv_message);
        id_user = SaveSharedPreferences.getId(getApplicationContext());

        bt_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIInterface mAPIInterface = APIClient.getClient().create(APIInterface.class);

                MultipartBody.Part body =null;
                if(!imagePath.isEmpty()){
                    // Buat file dari image yang dipilih
                    File file = new File(imagePath);

                    // Buat RequestBody instance dari file
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);

                    // MultipartBody.Part digunakan untuk mendapatkan nama file
                    body = MultipartBody.Part.createFormData("foto_ktp", file.getName(),
                            requestFile);

                }
                RequestBody reqid_user =
                        MultipartBody.create(parse("multipart/form-data"),
                                id_user);
                RequestBody reqaction =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                "insert");

                Call<GetIdentitas> identitasCall = mAPIInterface.postIdentitas(body, reqid_user, reqaction);
                identitasCall.enqueue(new Callback<GetIdentitas>() {
                    @Override
                    public void onResponse(Call<GetIdentitas> call, Response<GetIdentitas> response) {
                        if (response.body().getStatus().equals("failed")) {
                            tv_message.setText("Retrofit Insert \n Status = " + response.body()
                                    .getStatus() + "\n" +
                                    "Message = " + response.body().getMessage() + "\n");

                        } else {
                            String detail = "\n" +
                                    "id_user = " + response.body().getResult().get(0).getId_user() + "\n" +

                                    "foto_ktp= " + response.body().getResult().get(0).getFoto_ktp()
                                    + "\n";
                            tv_message.setText("Retrofit Insert \n Status = " + response.body().getStatus() + "\n" +
                                    "Message = " + response.body().getMessage() + detail);
                            Toast.makeText(InsertIdentitasActivity.this, "Berhasil disimpan", Toast.LENGTH_SHORT).show();
                            openIdentitas();
                        }
                    }



                    @Override
                    public void onFailure(Call<GetIdentitas> call, Throwable t) {
                        tv_message.setText("Retrofit Insert Failure \n Status = " + t.getMessage
                                ());
                    }
                });
            }
        });

        bt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mintaPermissions();

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
                            ambilDariCamera();
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
    private void ambilDariCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(cameraIntent, 16);
    }
    private void tampilkanSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(InsertIdentitasActivity.this);
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

                    Glide.with(mContext).load(new File(imagePath)).into(mImageView);

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

            Glide.with(mContext).load(new File(imagePath)).into(mImageView);
        }

    }
    public String simpanImage(Bitmap myBitmap){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        // Kualitas gambar yang disimpan
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        // Buat object direktori file
        File lokasiImage = new File(
                Environment.getExternalStorageDirectory() + "/identitas");

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

            Log.d("Identitas", "File tersimpan di --->" + f.getAbsolutePath());

            // Return file
            return f.getAbsolutePath();

        } catch (IOException e1) {
            Log.d("Identitas", "error");
            e1.printStackTrace();
        }
        return "";
    }

    private void openIdentitas() {
        Intent intent = new Intent(this.getApplicationContext(), IdentitasActivity.class);
        this.startActivity(intent);
    }
}
