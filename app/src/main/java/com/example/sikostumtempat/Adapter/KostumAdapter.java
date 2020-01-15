package com.example.sikostumtempat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sikostumtempat.EditKostumActivity;
import com.example.sikostumtempat.KostumActivity;
import com.example.sikostumtempat.MODEL.GetKostum;
import com.example.sikostumtempat.MODEL.Kostum;
import com.example.sikostumtempat.R;
import com.example.sikostumtempat.REST.APIClient;
import com.example.sikostumtempat.REST.APIInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KostumAdapter extends RecyclerView.Adapter<KostumAdapter.KostumViewHolder> {
    APIInterface mApiInterface;
    Handler mHandler;
    String url_photo;

    private static boolean dialogResult;
    List<Kostum> daftarKostum;
    public KostumAdapter(List<Kostum> daftarKostum){
        this.daftarKostum= daftarKostum;
    }
    @NonNull
    @Override
    public KostumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kostum,parent,false);
        KostumViewHolder mHolder=new KostumViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final KostumAdapter.KostumViewHolder holder, final int position) {
        holder.tvIdKostum.setText(daftarKostum.get(position).getId_kostum());
        holder.tvIdTempat.setText(daftarKostum.get(position).getId_tempat());
        holder.tvIdKategori.setText(daftarKostum.get(position).getId_kategori());
        holder.tvNamaKostum.setText(daftarKostum.get(position).getNama_kostum());
        holder.tvHargaKostum.setText(daftarKostum.get(position).getHarga_kostum());
        holder.tvJumlahKostum.setText(daftarKostum.get(position).getJumlah_kostum());
        holder.tvDeskripsiKostum.setText(daftarKostum.get(position).getDeskripsi_kostum());


        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final APIInterface mApiInterface = APIClient.getClient().create(APIInterface.class);
                RequestBody reqid_kostum =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (holder.tvIdKostum.getText().toString().isEmpty()) ?
                                        "" : holder.tvIdKostum.getText().toString());
                Call<GetKostum>mKostum= mApiInterface.deleteKostum(reqid_kostum);
                mKostum.enqueue(new Callback<GetKostum>() {
                    @Override
                    public void onResponse(Call<GetKostum> call, Response<GetKostum> response) {
                        Log.d("EditReview", response.body().getStatus());
                        if (response.body().getStatus().equals("success")){
                            Toast.makeText(holder.itemView.getContext(), "Berhasil Hapus", Toast.LENGTH_SHORT).show();
                            openKostum(holder.itemView.getContext());

                        }else{
                            Toast.makeText(holder.itemView.getContext(), "Gagal Hapus", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetKostum> call, Throwable t) {

                    }
                });

            }
        });
        if(daftarKostum.get(position).getFoto_kostum()!=""){
            Picasso.with(holder.itemView.getContext()).load(APIClient.BASE_URL+"uploads/"+daftarKostum.get(position).getFoto_kostum()).into(holder.imgFotoKostum);
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.kostum_icon).into(holder.imgFotoKostum);
        }
        url_photo = APIClient.BASE_URL+"uploads/"+daftarKostum.get(position).getFoto_kostum();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent= new Intent(v.getContext(), EditKostumActivity.class);
                mIntent.putExtra("id_kostum",daftarKostum.get(position).getId_kostum());
                mIntent.putExtra("id_tempat",daftarKostum.get(position).getId_tempat());
                mIntent.putExtra("id_kategori", daftarKostum.get(position).getId_kategori());
                mIntent.putExtra("nama_kostum", daftarKostum.get(position).getNama_kostum());
                mIntent.putExtra("harga_kostum", daftarKostum.get(position).getHarga_kostum());
                mIntent.putExtra("jumlah_kostum", daftarKostum.get(position).getJumlah_kostum());
                mIntent.putExtra("deskripsi_kostum",daftarKostum.get(position).getDeskripsi_kostum());
                mIntent.putExtra("foto_kostum", daftarKostum.get(position).getFoto_kostum());
                mIntent.putExtra("foto_kostum_url", url_photo);
                v.getContext().startActivity(mIntent);

            }
        });
    }



    @Override
    public int getItemCount() {
        return daftarKostum.size();
    }
    private void openKostum(Context context){
        Intent intent = new Intent(context,KostumActivity.class);
        context.startActivity(intent);
    }
    public class KostumViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdKostum,tvIdKategori,tvNamaKostum,tvHargaKostum,tvJumlahKostum,tvDeskripsiKostum, tvIdTempat;
        ImageView imgFotoKostum;
        Button hapus;
        public KostumViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdKostum= (TextView) itemView.findViewById(R.id.tvIdKostum);
            tvIdTempat = (TextView)itemView.findViewById(R.id.tvIdTempat);
            tvIdKategori = (TextView) itemView.findViewById(R.id.tvIdKategori);
            tvNamaKostum = (TextView) itemView.findViewById(R.id.tRnamaKostum);
            tvHargaKostum = (TextView) itemView.findViewById(R.id.tRHargaKostum);
            tvJumlahKostum = (TextView) itemView.findViewById(R.id.tvRJumlah);
            tvDeskripsiKostum= (TextView) itemView.findViewById(R.id.tRDeskripsi);
            imgFotoKostum= (ImageView) itemView.findViewById(R.id.rFoto_kostum);
            hapus = (Button) itemView.findViewById(R.id.bRHapus);

        }
    }
}
