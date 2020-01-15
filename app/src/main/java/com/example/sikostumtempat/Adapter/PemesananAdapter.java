package com.example.sikostumtempat.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sikostumtempat.MODEL.Data;
import com.example.sikostumtempat.MODEL.Pemesanan;
import com.example.sikostumtempat.MODEL.Region;
import com.example.sikostumtempat.MODEL.UniqueCode;
import com.example.sikostumtempat.R;
import com.example.sikostumtempat.REST.APIClient;
import com.example.sikostumtempat.REST.APIInterface;
import com.example.sikostumtempat.REST.APIWilayah;
import com.example.sikostumtempat.REST.WilayahInterface;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PemesananAdapter extends RecyclerView.Adapter<PemesananAdapter.PemesananViewHolder> {

    APIInterface mApiInterface;
    String code = "";
    Region namaProvinsi;
    Region namaKabupaten;
    Region namaKecamatan;
    Region namaKelurahan;
    String url_photo;
    Locale localeID= new Locale("in","ID");
    NumberFormat format= NumberFormat.getCurrencyInstance(Locale.ENGLISH);
    List<Pemesanan> daftarPemesanan;

    public  PemesananAdapter(List<Pemesanan>daftarPemesanan){
        this.daftarPemesanan= daftarPemesanan;
    }
    @NonNull
    @Override
    public PemesananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pemesanan,parent,false);
        PemesananViewHolder mHolder = new PemesananViewHolder(view);
        return  mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PemesananAdapter.PemesananViewHolder holder, final int position) {
        NumberFormat formatRupiah=NumberFormat.getCurrencyInstance(localeID);
        holder.tvIdTempat.setText(daftarPemesanan.get(position).getId_tempat());
        holder.tvNamaUser.setText(daftarPemesanan.get(position).getNama_user());
        holder.email.setText(daftarPemesanan.get(position).getEmail());
        holder.no_hp.setText(daftarPemesanan.get(position).getNo_hp());
        holder.tvAlamat.setText(daftarPemesanan.get(position).getAlamat());

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
                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarPemesanan.get(position).getProvinsi()) ){
                                holder.provinsi.setText(capitalize(daftarProvinsi.get(i).name));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {

                    }
                });

                final Call<Data> call3 = apiService.getKabupatenList(code,Long.valueOf(daftarPemesanan.get(position).getProvinsi()));

                call3.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        final List<Region> daftarProvinsi = response.body().getData();
                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarPemesanan.get(position).getKota())){
                                holder.kota.setText(capitalize(daftarProvinsi.get(i).getName()));


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {

                    }
                });

                Call<Data> call4 = apiService.getKecamatanList(code,Long.valueOf(daftarPemesanan.get(position).getKota()));

                call4.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {

                        final List<Region> daftarProvinsi = response.body().getData();
                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarPemesanan.get(position).getKecamatan()) ){
                                holder.kecamatan.setText(capitalize(daftarProvinsi.get(i).getName()));


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {

                    }
                });

                Call<Data> call5 = apiService.getKelurahanList(code, Long.valueOf(daftarPemesanan.get(position).getKecamatan()));

                call5.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {

                        final List<Region> daftarProvinsi = response.body().getData();
                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarPemesanan.get(position).getDesa())){
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
        holder.tglTrans.setText(daftarPemesanan.get(position).getTgl_transaksi());
        holder.tvNamaKostum.setText(daftarPemesanan.get(position).getNama_kostum());
        holder.jumlah.setText(daftarPemesanan.get(position).getJumlah());

        Integer harga = Integer.parseInt(daftarPemesanan.get(position).getHarga_kostum());
        Integer jml = Integer.parseInt(daftarPemesanan.get(position).getJumlah());


        holder.hargaKostum.setText(formatRupiah.format(harga));
        holder.status_log.setText(daftarPemesanan.get(position).getStatus_log());
        holder.tgl_sewa.setText(daftarPemesanan.get(position).getTgl_sewa());
        holder.tgl_kembali.setText(daftarPemesanan.get(position).getTgl_kembali());


        Integer totalBayar = harga*jml;

        holder.total.setText(formatRupiah.format(totalBayar));


        if(daftarPemesanan.get(position).getFoto_kostum()!=""){
            Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarPemesanan.get(position).getFoto_kostum()).into(holder.foto_kostum);
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.kostum_icon).into(holder.foto_kostum);
        }
        url_photo = APIClient.BASE_URL+"uploads/"+daftarPemesanan.get(position).getFoto_kostum();

    }

    @Override
    public int getItemCount() {
        return daftarPemesanan.size();
    }

    public class PemesananViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdTempat,tvNamaUser, email, no_hp, tvAlamat, provinsi, kota, kecamatan, desa, tglTrans,tvNamaKostum,jumlah,hargaKostum,status_log, tgl_sewa, tgl_kembali,total;
        ImageView foto_kostum;
        public PemesananViewHolder(@NonNull View itemView) {
            super(itemView);
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
