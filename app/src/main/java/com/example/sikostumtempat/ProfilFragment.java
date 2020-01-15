package com.example.sikostumtempat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sikostumtempat.MODEL.GetProfilId;
import com.example.sikostumtempat.REST.APIClient;
import com.example.sikostumtempat.REST.APIInterface;
import com.example.sikostumtempat.Utils.SaveSharedPreferences;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilFragment extends Fragment {
    TextView nama, jk ;
    EditText email, no_hp, username, password;

    RadioGroup rg;
    Button edit, keluar, identitas, alamat;
    int id_user;
    ImageView foto, foto_user2;
    String url_photo, photoName;
    APIInterface mAPIInterface;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_profil, container, false);
        View rootView2 = inflater.inflate(R.layout.activity_edit_profil, container, false);

        jk = rootView.findViewById(R.id.textJk);
        nama = rootView.findViewById(R.id.tx_nama);
        foto = rootView.findViewById(R.id.profile_image);
        edit = rootView.findViewById(R.id.btn_edit);
        keluar = rootView.findViewById(R.id.btn_logout);
        no_hp = rootView.findViewById(R.id.no_hp);
        email =rootView.findViewById(R.id.email);
        username = rootView.findViewById(R.id.username);
        password = rootView.findViewById(R.id.password);
        identitas = rootView.findViewById(R.id.btn_identitas);
        alamat = rootView.findViewById(R.id.btn_alamat);

        email = rootView2.findViewById(R.id.email);
        no_hp = rootView2.findViewById(R.id.no_hp);
        foto_user2 = rootView2.findViewById(R.id.foto_user);
        username = rootView2.findViewById(R.id.username);
        password = rootView2.findViewById(R.id.password);
        id_user =Integer.parseInt(SaveSharedPreferences.getId(getContext()));

        getData();
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        identitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), IdentitasActivity.class);
                startActivity(intent);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getContext(), EditProfilActivity.class);
                mIntent.putExtra("id_user", id_user);
                mIntent.putExtra("nama", nama.getText().toString());
                mIntent.putExtra("jenis_kelamin", jk.getText().toString());
                mIntent.putExtra("no_hp", no_hp.getText().toString());
//                mIntent.putExtra("foto_user",foto_user );
                mIntent.putExtra("email", email.getText().toString());
                mIntent.putExtra("username", username.getText().toString());
                mIntent.putExtra("password", password.getText().toString());
                mIntent.putExtra("foto_user", photoName);
                mIntent.putExtra("foto_user_url", url_photo);
                startActivity(mIntent);
            }
        });
        alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(), AlamatActivity.class);
                startActivity(intent);
            }
        });
        return rootView;

    }
    public void logout(){
        SaveSharedPreferences.setLoggedInTS(getContext(), false);
        SaveSharedPreferences.setId(getContext(),"");
    }
    public void getData(){
        mAPIInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody reqid_user = MultipartBody.create(MediaType.parse("multipart/form-data"),SaveSharedPreferences.getId(getContext()));
        Call<GetProfilId> mMyProfileCall = mAPIInterface.getMyProfile(reqid_user);
        mMyProfileCall.enqueue(new Callback<GetProfilId>(){

            @Override
            public void onResponse(Call<GetProfilId> call, Response<GetProfilId> response) {
                Log.d("GetProfilId", response.body().getStatus());
                if (response.body().getStatus().equals("success")){
                    nama.setText(response.body().getResult().get(0).getNama());
                    jk.setText(response.body().getResult().get(0).getJenis_kelamin());
                    no_hp.setText(response.body().getResult().get(0).getNo_hp());
                    url_photo = APIClient.BASE_URL+"uploads/"+response.body().getResult().get(0).getFoto_user();
                    photoName = response.body().getResult().get(0).getFoto_user();
                    if (photoName != null){
                        Glide.with(getContext()).load(url_photo).into(foto);
                    } else {
                        Glide.with(getContext()).load(R.drawable.kostum_icon).into(foto);
                    }


                    email.setText(response.body().getResult().get(0).getEmail());
                    username.setText(response.body().getResult().get(0).getUsername());
                    password.setText(response.body().getResult().get(0).getPassword());
                }else{
                    Toast.makeText(getActivity(),"Gagal",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetProfilId> call, Throwable t) {
                Toast.makeText(getContext(),"Koneksi Internet bermasalah",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
