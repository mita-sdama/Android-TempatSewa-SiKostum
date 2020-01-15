package com.example.sikostumtempat.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sikostumtempat.MODEL.Data;
import com.example.sikostumtempat.MODEL.GetDenda;
import com.example.sikostumtempat.MODEL.Pemesanan;
import com.example.sikostumtempat.MODEL.Region;
import com.example.sikostumtempat.MODEL.UniqueCode;
import com.example.sikostumtempat.R;
import com.example.sikostumtempat.REST.APIClient;
import com.example.sikostumtempat.REST.APIInterface;
import com.example.sikostumtempat.REST.APIWilayah;
import com.example.sikostumtempat.REST.WilayahInterface;
import com.example.sikostumtempat.SewaActivity;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SewaAdapter extends RecyclerView.Adapter<SewaAdapter.SewaViewHolder> {

    APIInterface mApiInterface;
    String code = "";
    Region namaProvinsi;
    Region namaKabupaten;
    Region namaKecamatan;
    Region namaKelurahan;
    String url_photo;
    List<Pemesanan> daftarSewa;
    Locale localeID= new Locale("in","ID");
    NumberFormat format= NumberFormat.getCurrencyInstance(Locale.ENGLISH);

    public  SewaAdapter(List<Pemesanan>daftarSewa){
        this.daftarSewa= daftarSewa;
    }

    @NonNull
    @Override
    public SewaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sewa,parent,false);
        SewaViewHolder mHolder = new SewaViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SewaAdapter.SewaViewHolder holder, final int position) {
        NumberFormat formatRupiah=NumberFormat.getCurrencyInstance(localeID);
        holder.tvIdLog.setText(daftarSewa.get(position).getId_log());
        holder.id_detail.setText(daftarSewa.get(position).getId_detail());
        holder.tvIdTempat.setText(daftarSewa.get(position).getId_tempat());
        holder.tvNamaUser.setText(daftarSewa.get(position).getNama_user());
        holder.email.setText(daftarSewa.get(position).getEmail());
        holder.no_hp.setText(daftarSewa.get(position).getNo_hp());
        holder.tvAlamat.setText(daftarSewa.get(position).getAlamat());
        final WilayahInterface apiService = APIWilayah.getClient().create(WilayahInterface.class);
        Call<UniqueCode> call = apiService.getUniqueCode();
        call.enqueue(new Callback<UniqueCode>() {
            @Override
            public void onResponse(Call<UniqueCode> call, Response<UniqueCode> response) {

                code = "MeP7c5ne" + response.body().getUniqueCode();
                Call<Data> call2 = apiService.getProvinceList(code);

                call2.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {

                        final List<Region> daftarProvinsi = response.body().getData();
                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarSewa.get(position).getProvinsi()) ){
                                holder.provinsi.setText(capitalize(daftarProvinsi.get(i).name));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {

                    }
                });

                final Call<Data> call3 = apiService.getKabupatenList(code,Long.valueOf(daftarSewa.get(position).getProvinsi()));

                call3.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        final List<Region> daftarProvinsi = response.body().getData();
                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarSewa.get(position).getKota())){
                                holder.kota.setText(capitalize(daftarProvinsi.get(i).getName()));


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {

                    }
                });

                Call<Data> call4 = apiService.getKecamatanList(code,Long.valueOf(daftarSewa.get(position).getKota()));

                call4.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {

                        final List<Region> daftarProvinsi = response.body().getData();
                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarSewa.get(position).getKecamatan()) ){
                                holder.kecamatan.setText(capitalize(daftarProvinsi.get(i).getName()));


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {

                    }
                });

                Call<Data> call5 = apiService.getKelurahanList(code, Long.valueOf(daftarSewa.get(position).getKecamatan()));

                call5.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {

                        final List<Region> daftarProvinsi = response.body().getData();
                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarSewa.get(position).getDesa())){
                                holder.desa.setText(capitalize(daftarProvinsi.get(i).getName()));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {

                    }
                });





            }

            @Override
            public void onFailure(Call<UniqueCode> call, Throwable t) {

            }
        });


        holder.tglTrans.setText(daftarSewa.get(position).getTgl_transaksi());
        holder.tvNamaKostum.setText(daftarSewa.get(position).getNama_kostum());
        holder.jumlah.setText(daftarSewa.get(position).getJumlah());
        Integer harga = Integer.parseInt(daftarSewa.get(position).getHarga_kostum());
        Integer jml = Integer.parseInt(daftarSewa.get(position).getJumlah());

        holder.hargaKostum.setText(formatRupiah.format(harga));
        holder.status_log.setText(daftarSewa.get(position).getStatus_log());
        holder.tgl_sewa.setText(daftarSewa.get(position).getTgl_sewa());
        holder.tgl_kembali.setText(daftarSewa.get(position).getTgl_kembali());


        Integer totalBayar = harga*jml;

        holder.total.setText(formatRupiah.format(totalBayar));


        if(daftarSewa.get(position).getFoto_kostum()!=""){
            Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarSewa.get(position).getFoto_kostum()).into(holder.foto_kostum);
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.kostum_icon).into(holder.foto_kostum);
        }
        url_photo = APIClient.BASE_URL+"uploads/"+daftarSewa.get(position).getFoto_kostum();
        holder.updateSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(holder.itemView.getContext());
                dialog.setContentView(R.layout.layout_customdialog);
                dialog.setTitle("Input Denda");
                final EditText text = (EditText) dialog.findViewById(R.id.tv_denda);
                text.setText("");
                final EditText keterangan = (EditText) dialog.findViewById(R.id.tv_ket);
                keterangan.setText("");
                Button dialogButton = (Button) dialog.findViewById(R.id.bt_ok);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final APIInterface mApiInterface = APIClient.getClient().create(APIInterface.class);
                        RequestBody reqid_detail = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                holder.id_detail.getText().toString() );
                        RequestBody reqdenda = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                text.getText().toString());
                        RequestBody reqketerangan = MultipartBody.create(MediaType.parse("multipart/form-data"),
                                keterangan.getText().toString());
                        Call<GetDenda> mDenda=mApiInterface.postDenda(reqid_detail,reqdenda,reqketerangan);
                        mDenda.enqueue(new Callback<GetDenda>() {
                            @Override
                            public void onResponse(Call<GetDenda> call, Response<GetDenda> response) {
                                if (response.body().getStatus().equals("success")){
                                    Toast.makeText(holder.itemView.getContext(), "Denda disimpan", Toast.LENGTH_SHORT).show();
                                    openBeranda(holder.itemView.getContext());;

                                }else{

                                    Toast.makeText(holder.itemView.getContext(), "Gagal Hapus", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<GetDenda> call, Throwable t) {

                            }
                        });


                    }
                });
                dialog.show();
            }

        });


    }

    @Override
    public int getItemCount() {
        return daftarSewa.size();
    }
    private void openBeranda(Context context){
        Intent intent = new Intent(context, SewaActivity.class);
        context.startActivity(intent);
    }

    public class SewaViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdLog,tvIdTempat,tvNamaUser, id_detail,email, no_hp, tvAlamat, provinsi, kota, kecamatan, desa, tglTrans,tvNamaKostum,jumlah,hargaKostum,status_log, tgl_sewa, tgl_kembali,total;
        ImageView foto_kostum;
        Button updateSelesai;
        public SewaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdLog=(TextView) itemView.findViewById(R.id.tvIdLog);
            tvIdTempat=(TextView) itemView.findViewById(R.id.tvIdTempat);
            tvNamaUser = (TextView) itemView.findViewById(R.id.tNamaUser);
            email = (TextView) itemView.findViewById(R.id.tEMailUser);
            no_hp = (TextView) itemView.findViewById(R.id.tNoHp);
            tvAlamat =(TextView) itemView.findViewById(R.id.tvAlamatUser);
            provinsi = (TextView) itemView.findViewById(R.id.tvProv);
            kota = (TextView) itemView.findViewById(R.id.tvKot);
            kecamatan = (TextView) itemView.findViewById(R.id.tvKec);
            desa = (TextView) itemView.findViewById(R.id.tvDes);
            tglTrans =(TextView) itemView.findViewById(R.id.tvTglTrans);
            tvNamaKostum=(TextView) itemView.findViewById(R.id.tJenengKostum);
            jumlah =(TextView) itemView.findViewById(R.id.tvJmlh);
            hargaKostum=(TextView) itemView.findViewById(R.id.tvHg);
            status_log =(TextView)itemView.findViewById(R.id.tvStatus_log);
            foto_kostum =(ImageView) itemView.findViewById(R.id.gbrKstm);
            tgl_sewa = (TextView) itemView.findViewById(R.id.tgl_sewa);
            tgl_kembali = (TextView) itemView.findViewById(R.id.tgl_kembali);
            total = (TextView) itemView.findViewById(R.id.tvTotal);
            updateSelesai = (Button) itemView.findViewById(R.id.btSelesai);
            id_detail=(TextView) itemView.findViewById(R.id.tvIdDetailDenda);
        }
    }
    private String capitalize(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }

}
