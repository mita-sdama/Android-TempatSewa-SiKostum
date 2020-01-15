package com.example.sikostumtempat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditIdentitasActivity extends AppCompatActivity {
    TextView tvStatusId,tvId;
    ImageView ivPhoto;
    Button btCameraId, btSimpanUp;
    String fileNamePhoto;
    String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_identitas);

        tvId = (TextView) findViewById(R.id.tvId);
        tvStatusId = (TextView) findViewById(R.id.tvStatusId);
        btCameraId = (Button)findViewById(R.id.btCameraId);
        btSimpanUp =(Button) findViewById(R.id.btSimpanUp);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);

        final Intent mIntent = getIntent();
        tvId.setText(mIntent.getStringExtra("id_identitas"));

        tvStatusId.setText(mIntent.getStringExtra("status"));
        fileNamePhoto = mIntent.getStringExtra("foto_ktp");
        imagePath = mIntent.getStringExtra("foto_ktp_url");
        if (fileNamePhoto != null){
            Glide.with(getApplicationContext()).load(imagePath).into(ivPhoto);
        } else {
            Glide.with(getApplicationContext()).load(R.drawable.ic_person_black_24dp).into(ivPhoto);
        }
        imagePath = mIntent.getStringExtra("foto_ktp_url");

        final APIInterface mApiInterface = APIClient.getClient().create(APIInterface.class);

        btSimpanUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MultipartBody.Part body = null;
                //dicek apakah image sama dengan yang ada di server atau berubah
                //jika sama dikirim lagi jika berbeda akan dikirim ke server
                if ((!imagePath.contains("uploads/" + fileNamePhoto)) &&
                        (imagePath.length()>0)){
                    //File creating from selected URL
                    File file = new File(imagePath);

                    // create RequestBody instance from file
                    RequestBody requestFile = RequestBody.create(
                            MediaType.parse("multipart/form-data"), file);

                    // MultipartBody.Part is used to send also the actual file name
                    body = MultipartBody.Part.createFormData("foto_ktp", file.getName(),
                            requestFile);
                }
                RequestBody reqid_identitas =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (tvId
                                        .getText().toString().isEmpty()) ?
                                        "" : tvId.getText().toString());

                Call<GetIdentitas> callUpdate = mApiInterface.putIdentitas(body, reqid_identitas);
                callUpdate.enqueue(new Callback<GetIdentitas>() {
                    @Override
                    public void onResponse(Call<GetIdentitas> call, Response<GetIdentitas> response) {
                        if (response.body().getStatus().equals("failed")){
                            Toast.makeText(EditIdentitasActivity.this, "Gagal Edit Identitas", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(EditIdentitasActivity.this, "Berhasil diedit", Toast.LENGTH_SHORT).show();
                            Intent myIdentitasIntent = new Intent(getApplicationContext(),IdentitasActivity.class);
                            startActivity(myIdentitasIntent);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetIdentitas> call, Throwable t) {
                        Toast.makeText(EditIdentitasActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
        btCameraId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isDeviceSupportCamera()) {
                    Toast.makeText(getApplicationContext(),"Camera di device anda tidak tersedia",
                            Toast.LENGTH_LONG).show();
                    Log.d("Camera", "gak ada kamera!!");
                    finish();
                } else {
                    Log.d("Camera", "ada kamera");
                    captureImage();

                }
            }
        });


    }
    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            // requestCode 100 untuk membedakan
            startActivityForResult(takePictureIntent, 100);
        }
    }

    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // Ok, device punya camera
            return true;
        } else {
            // Device masih mendol
            return false;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode ==10) {
            if (data == null) {
                Toast.makeText(getApplicationContext(), "Foto gagal di-load", Toast.LENGTH_LONG).show();
                return;
            } else {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.
                            Media.getBitmap(this.getContentResolver(), contentURI);
                    imagePath = simpanImage(bitmap);
                    Toast.makeText(getApplicationContext(), "Foto berhasil di-load!",
                            Toast.LENGTH_SHORT).show();

                    ivPhoto.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Foto gagal di-load!", Toast.LENGTH_SHORT).show();
                }

            }
        } else if (resultCode == RESULT_OK && requestCode == 100) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ivPhoto.setImageBitmap(thumbnail);

            imagePath = simpanImage(thumbnail);
            Toast.makeText(getApplicationContext(), "Foto berhasil di-load dari Camera!",
                    Toast.LENGTH_SHORT).show();


        }
    }

    public String simpanImage(Bitmap myBitmap) {
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

            Log.d("Foto", "File tersimpan di --->" + f.getAbsolutePath());

            // Return file
            return f.getAbsolutePath();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
}
