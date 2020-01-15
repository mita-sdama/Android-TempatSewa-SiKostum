package com.example.sikostumtempat.Adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sikostumtempat.EditAlamatActivity;
import com.example.sikostumtempat.MODEL.Alamat;
import com.example.sikostumtempat.MODEL.Data;
import com.example.sikostumtempat.MODEL.Region;
import com.example.sikostumtempat.MODEL.UniqueCode;
import com.example.sikostumtempat.R;
import com.example.sikostumtempat.REST.APIWilayah;
import com.example.sikostumtempat.REST.WilayahInterface;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlamatAdapter extends RecyclerView.Adapter<AlamatAdapter.AlamatViewHolder> {
    String code = "";
    Region namaProvinsi;
    Region namaKabupaten;
    Region namaKecamatan;
    Region namaKelurahan;
    List<Alamat> daftarAlamat;
    public AlamatAdapter(List<Alamat> daftarAlamat) {
        this.daftarAlamat = daftarAlamat;
    }
    @NonNull
    @Override
    public AlamatAdapter.AlamatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_alamat,parent,false);
        AlamatViewHolder mHolder = new AlamatViewHolder(view);
        return mHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull final AlamatAdapter.AlamatViewHolder holder, final int position) {

        holder.tvId_Al.setText(daftarAlamat.get(position).getId_alamat());
        holder.tvLabel.setText(daftarAlamat.get(position).getLabel_alamat());
        holder.tvAlamat.setText(daftarAlamat.get(position).getAlamat());
        final String alamat = daftarAlamat.get(position).getAlamat();
        final String myProvinsi = daftarAlamat.get(position).getProvinsi();
        final WilayahInterface apiService = APIWilayah.getClient().create(WilayahInterface.class);
        Call<UniqueCode> call = apiService.getUniqueCode();
        holder.proggressLoad.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<UniqueCode>() {
            @Override
            public void onResponse(Call<UniqueCode> call, Response<UniqueCode> response) {

                code = "MeP7c5ne" + response.body().getUniqueCode();
                Call<Data> call2 = apiService.getProvinceList(code);

                call2.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        int provinsiKu = 0;
                        final List<Region> daftarProvinsi = response.body().getData();
                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarAlamat.get(position).getProvinsi()) ){
                                holder.tvProvinsi.setText(capitalize(daftarProvinsi.get(i).name));
                                provinsiKu = i;
                            }


                        }

                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {

                    }
                });

                final Call<Data> call3 = apiService.getKabupatenList(code,Long.valueOf(daftarAlamat.get(position).getProvinsi()));

                call3.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        final List<Region> daftarProvinsi = response.body().getData();
                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarAlamat.get(position).getKota())){
                                holder.tvKota.setText(capitalize(daftarProvinsi.get(i).getName()));


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {

                    }
                });

                Call<Data> call4 = apiService.getKecamatanList(code,Long.valueOf(daftarAlamat.get(position).getKota()));

                call4.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {

                        final List<Region> daftarProvinsi = response.body().getData();
                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarAlamat.get(position).getKecamatan()) ){
                                holder.tvKecamatan.setText(capitalize(daftarProvinsi.get(i).getName()));


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {

                    }
                });

                Call<Data> call5 = apiService.getKelurahanList(code, Long.valueOf(daftarAlamat.get(position).getKecamatan()));

                call5.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {

                        final List<Region> daftarProvinsi = response.body().getData();
                        for (int i = 0; i < daftarProvinsi.size(); i++) {
                            if (daftarProvinsi.get(i).id == Long.valueOf(daftarAlamat.get(position).getDesa())){
                                holder.tvDesa.setText(capitalize(daftarProvinsi.get(i).getName()));
                                holder.proggressLoad.setVisibility(View.GONE);
                                holder.tvProvinsi.setVisibility(View.VISIBLE);
                                holder.batasProv.setVisibility(View.VISIBLE);
                                holder.tvKota.setVisibility(View.VISIBLE);
                                holder.tvKetKecamatan.setVisibility(View.VISIBLE);
                                holder.tvKecamatan.setVisibility(View.VISIBLE);
                                holder.tvDesa.setVisibility(View.VISIBLE);
                                holder.tvKetDesa.setVisibility(View.VISIBLE);
                                holder.tvLabel.setVisibility(View.VISIBLE);
                                holder.tvAlamat.setVisibility(View.VISIBLE);
                                holder.lokasiKu.setVisibility(View.VISIBLE);
                                final String googleMaps = "com.google.android.apps.maps";

                                final String lokasi = ""+alamat+"";
                                holder.lokasiKu.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final Uri gmmIntentUri = Uri.parse("google.navigation:q=" +lokasi);
                                        final Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                        mapIntent.setPackage(googleMaps);
                                        v.getContext().startActivity(mapIntent);
                                    }
                                });





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


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(), EditAlamatActivity.class);
                intent2.putExtra("id_alamat",daftarAlamat.get(position).getId_alamat());
                intent2.putExtra("label_alamat",daftarAlamat.get(position).getLabel_alamat());
                intent2.putExtra("alamat", daftarAlamat.get(position).getAlamat());
                intent2.putExtra("provinsi", daftarAlamat.get(position).getProvinsi());
                intent2.putExtra("kota",daftarAlamat.get(position).getKota());
                intent2.putExtra("kecamatan",daftarAlamat.get(position).getKecamatan());
                intent2.putExtra("desa",daftarAlamat.get(position).getDesa());
                v.getContext().startActivity(intent2);

            }
        });
    }
    private void loadUniqueCode() {
        WilayahInterface apiService = APIWilayah.getClient().create(WilayahInterface.class);
        Call<UniqueCode> call = apiService.getUniqueCode();

        call.enqueue(new Callback<UniqueCode>() {
            @Override
            public void onResponse(Call<UniqueCode> call, Response<UniqueCode> response) {
                code = "MeP7c5ne" + response.body().getUniqueCode();
            }

            @Override
            public void onFailure(Call<UniqueCode> call, Throwable t) {

            }
        });
    }

    public Region getNamaProvinsi(final long id){
        WilayahInterface apiService = APIWilayah.getClient().create(WilayahInterface.class);

        Call<Data> call = apiService.getProvinceList(code);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                final List<Region> daftarProvinsi = response.body().getData();
                // isi data pertama dengan string 'Silakan Pilih!'
                for (int i = 0; i < daftarProvinsi.size(); i++) {
                    if (daftarProvinsi.get(i).id == id ){
                        setNamaProvinsi(daftarProvinsi.get(i));
                    }
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });

        return namaProvinsi;
    }
    public Region getNamaKabupaten(final long idProv, final long id){
        WilayahInterface apiService = APIWilayah.getClient().create(WilayahInterface.class);

        Call<Data> call = apiService.getKabupatenList(code,idProv);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                final List<Region> daftarProvinsi = response.body().getData();
                // isi data pertama dengan string 'Silakan Pilih!'
                for (int i = 0; i < daftarProvinsi.size(); i++) {
                    if (daftarProvinsi.get(i).id == id ){
                        setNamaKabupaten(daftarProvinsi.get(i));
                    }
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
        return namaKabupaten;
    }
    public Region getNamaKecamatan(final long idKab, final long id){
        WilayahInterface apiService = APIWilayah.getClient().create(WilayahInterface.class);

        Call<Data> call = apiService.getKecamatanList(code,idKab);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                final List<Region> daftarProvinsi = response.body().getData();
                // isi data pertama dengan string 'Silakan Pilih!'
                for (int i = 0; i < daftarProvinsi.size(); i++) {
                    if (daftarProvinsi.get(i).id == id ){
                        setNamaKecamatan(daftarProvinsi.get(i));
                    }
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
        return namaKecamatan;
    }
    public Region getNamaKelurahan(final long idKec, final long id){
        WilayahInterface apiService = APIWilayah.getClient().create(WilayahInterface.class);

        Call<Data> call = apiService.getKelurahanList(code,idKec);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                final List<Region> daftarProvinsi = response.body().getData();
                // isi data pertama dengan string 'Silakan Pilih!'
                for (int i = 0; i < daftarProvinsi.size(); i++) {
                    if (daftarProvinsi.get(i).id == id ){
                        setNamaKelurahan(daftarProvinsi.get(i));
                    }
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
        return namaKelurahan;
    }



    public void setNamaProvinsi(Region nama){
        namaProvinsi = nama;
    }

    public void setNamaKabupaten(Region nama){
        namaKabupaten = nama;
    }
    public  void setNamaKecamatan(Region nama){
        namaKecamatan = nama;
    }
    public  void setNamaKelurahan(Region nama){
        namaKelurahan = nama;
    }
    @Override
    public int getItemCount() {
        return daftarAlamat.size();
    }

    public class AlamatViewHolder extends RecyclerView.ViewHolder {

        TextView tvId_Al,tvLabel,tvAlamat,tvProvinsi, tvKota, tvKecamatan, tvDesa, tvKetKecamatan, tvKetDesa,batasProv;
        ImageView gambar;
        Button lokasiKu;
        final ProgressBar proggressLoad;
        public AlamatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId_Al =(TextView) itemView.findViewById(R.id.tvIdAl);
            tvLabel = (TextView) itemView.findViewById(R.id.tvLabel);
            tvAlamat = (TextView) itemView.findViewById(R.id.tvAlamat);
            tvProvinsi = (TextView) itemView.findViewById(R.id.tvProvinsi);
            tvKota = (TextView) itemView.findViewById(R.id.tvKota);
            tvKecamatan = (TextView) itemView.findViewById(R.id.tvKecamatan);
            tvKetKecamatan = (TextView) itemView.findViewById(R.id.tvKetKec);
            tvKetDesa = (TextView) itemView.findViewById(R.id.tvKetDes);
            tvDesa = (TextView) itemView.findViewById(R.id.tvDesa);
            batasProv = (TextView) itemView.findViewById(R.id.batas);
            gambar = (ImageView) itemView.findViewById(R.id.profile_image);
            proggressLoad = (ProgressBar) itemView.findViewById(R.id.simpleProgressBarr);
            lokasiKu = (Button) itemView.findViewById(R.id.btn_navigasi);



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
