package com.example.sikostumtempat;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sikostumtempat.MODEL.GetLogin;
import com.example.sikostumtempat.REST.APIClient;
import com.example.sikostumtempat.REST.APIInterface;
import com.example.sikostumtempat.Utils.SaveSharedPreferences;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.text.Html.fromHtml;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    EditText editTextUsername, editTextPassword;
    Button buttonLogin;
    APIInterface apiInterface;
    private  GoogleApiClient googleApiClient;
    private  static final int REQ_CODE=9001;
    private GoogleSignInButton SignIn;
    TextView Email;
    String judul;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //text daftar
        initCreateAccountTextView();

        ScrollView sView = (ScrollView) findViewById(R.id.login_form);
        sView.setVerticalScrollBarEnabled(false);
        sView.setHorizontalScrollBarEnabled(false);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        Email=(TextView) findViewById(R.id.email);
        SignIn =(GoogleSignInButton) findViewById(R.id.btPilihEmail);
       SignIn.setOnClickListener(this);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();
        // Check if UserResponse is Already Logged In
        if(SaveSharedPreferences.getLoggedStatusTS(getApplicationContext())) {
            Intent intent = new Intent(getApplicationContext(), BerandaActivity.class);
            startActivity(intent);
            finish();
        }

        //button login
        buttonLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Cek form login
                if (!username.isEmpty() && !password.isEmpty()) {
                    // Lakukan login
                    doLogin(username, password);
                } else {
                    // Notif user
                    Toast.makeText(getApplicationContext(),
                            "Isikan username dan password!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });
    }

    //cek prefernces login
    private boolean isLoggedIn()
    {
        // Cek apakah ada shared pref login
        SharedPreferences pref = getSharedPreferences("SiKostumLoginData", MODE_PRIVATE);
        return pref.getBoolean("isloggedin", false);

    }

    //membuka beranda
    private void openHome()
    {
        Intent intent = new Intent(this.getApplicationContext(), BerandaActivity.class);
        this.startActivity(intent);
    }

    //menyimpan data ke shareprefernces
    private void saveLogin(String username, String id_user)
    {
        // Simpan data login ke shared pref
        SharedPreferences sharedpref = getSharedPreferences("SiKostumLoginData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();

        // Simpan isloggedin true berupa boolean
        editor.putBoolean("isloggedin", true);
        // Simpan data lainnya berupa string
        editor.putString("username", username);
        editor.putString("id_user", id_user);
        editor.apply();
    }

    //proses login email
    private void loginEmail(final String email){
        apiInterface = APIClient.getClient().create(APIInterface.class);
       Call loginCall = apiInterface.postEmail(email);
        final ProgressBar simpleProgressBar= (ProgressBar) findViewById(R.id.simpleProgressBarr);
        final LinearLayout progressLayout = (LinearLayout) findViewById(R.id.progressLayout);
        loginCall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                // Jika request sukses
                if(response.isSuccessful()){

                    // Buat object model GetLogin dari response
                    GetLogin loginobject = (GetLogin) response.body();
                    String id_user = loginobject.getResult();

                    // Jika status = success (sesuai respon dari REST server)
                    if(loginobject.getStatus().equals("Tempat Sewa")){
                        simpleProgressBar.setVisibility(View.VISIBLE);
                        progressLayout.setVisibility(View.VISIBLE);
                        // Simpan data email user ke sharedpref
                        SaveSharedPreferences.setLoggedInTS(getApplicationContext(), true);
                        SaveSharedPreferences.setId(getApplicationContext(),id_user);
                        // Buka layar home
                        openHome();

                    }
                    else {
                        Toast.makeText(LoginActivity.this,
                                "Email belum terdaftar",
                                Toast.LENGTH_SHORT).show();
                        simpleProgressBar.setVisibility(View.GONE);
                        progressLayout.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Error! Coba lagi!",
                            Toast.LENGTH_SHORT).show();
                    simpleProgressBar.setVisibility(View.GONE);
                    progressLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                // Jika gagal, beri notif
                Toast.makeText(LoginActivity.this, "Email tidak terdaftar" , Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
    //proses login menggunkan rest
    private void doLogin(final String username,final String password){
        // Panggil request Api
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call loginCall = apiInterface.loginToko(username, password);

        final ProgressBar simpleProgressBar= (ProgressBar) findViewById(R.id.simpleProgressBarr);
        final LinearLayout progressLayout = (LinearLayout) findViewById(R.id.progressLayout);

        loginCall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                // Jika request sukses
                if(response.isSuccessful()){

                    // Buat object model GetLogin dari response
                    GetLogin loginobject = (GetLogin) response.body();
                    String id_user = loginobject.getResult();

                    // Jika status = success (sesuai respon dari REST server)
                    if(loginobject.getStatus().equals("Tempat Sewa")){
                        simpleProgressBar.setVisibility(View.VISIBLE);
                        progressLayout.setVisibility(View.VISIBLE);
                        // Simpan data email user ke sharedpref
                        SaveSharedPreferences.setLoggedInTS(getApplicationContext(), true);
                        SaveSharedPreferences.setId(getApplicationContext(),id_user);
                        // Buka layar home
                        openHome();

                    }
                   else {
                        Toast.makeText(LoginActivity.this,"Username atau password salah",Toast.LENGTH_SHORT).show();
                        simpleProgressBar.setVisibility(View.GONE);
                        progressLayout.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(LoginActivity.this,"Error! Coba lagi!",Toast.LENGTH_SHORT).show();
                    simpleProgressBar.setVisibility(View.GONE);
                    progressLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                // Jika gagal, beri notif
                Toast.makeText(LoginActivity.this, "Username dan password tidak sesuai" , Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    //this method used to set Create account TextView text and click event( maltipal colors
    // for TextView yet not supported in Xml so i have done it programmatically)
    private void initCreateAccountTextView() {
        TextView textViewCreateAccount = (TextView) findViewById(R.id.textViewCreateAccount);
        textViewCreateAccount.setText(fromHtml("<font color='#000000'>Belum memiliki Akun ? </font><font color='#03A9F4'>Daftar</font>"));
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PendaftaranActivity.class);
                startActivity(intent);
            }
        });
    }

    //This method is for handling fromHtml method deprecation
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btPilihEmail:
                signIn();
                break;

        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn(){
        Intent intent= Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);
    }
    private void signOut(){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });
    }
    private void handleResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account= result.getSignInAccount();
            String email = account.getEmail();
            Email.setText(email);

            final Dialog dialog = new Dialog(LoginActivity.this);
            dialog.setContentView(R.layout.layout_email);
            final TextView judul =(TextView) dialog.findViewById(R.id.judul);
            final TextView text=(TextView) dialog.findViewById(R.id.dataemail);
            judul.setText("Apakah anda yakin akan login dengan akun ini?");
            text.setText(email);
            Button dialogButton = (Button) dialog.findViewById(R.id.bt_ok);
            Button batal = (Button) dialog.findViewById(R.id.bt_batal);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = text.getText().toString().trim();

                    // Cek form login
                    if (!email.isEmpty()) {
                        // Lakukan login
                        loginEmail(email);
                    }
                    else {
                        // Notif user
                        Toast.makeText(getApplicationContext(),
                                "Gagal", Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });
            batal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signOut();
                    dialog.dismiss();
                }
            });
            dialog.show();
        }else{
            updateUI(false);
        }
    }
    private void updateUI(boolean isLogin){
        if(isLogin){
            SignIn.setVisibility(View.GONE);
        }else{
            SignIn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_CODE){
            GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }
}
