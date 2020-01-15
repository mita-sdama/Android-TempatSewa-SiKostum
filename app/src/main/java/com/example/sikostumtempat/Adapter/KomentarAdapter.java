package com.example.sikostumtempat.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sikostumtempat.MODEL.Komentar;
import com.example.sikostumtempat.R;
import com.example.sikostumtempat.REST.APIInterface;

import java.util.List;

public class KomentarAdapter extends RecyclerView.Adapter<KomentarAdapter.KomentarViewHolder> {
    APIInterface mApiInterface;
    List<Komentar> daftarKomentar;
    public KomentarAdapter(List<Komentar> daftarKomentar) {
        this.daftarKomentar= daftarKomentar;
    }

    @NonNull
    @Override
    public KomentarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_komentar,parent,false);
        KomentarViewHolder mHolder= new KomentarViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull KomentarAdapter.KomentarViewHolder holder, int position) {
        holder.tglTransaksi.setText(daftarKomentar.get(position).getTgl_transaksi());
        holder.namaPenyewa.setText(daftarKomentar.get(position).getNama());
        holder.namaKostum.setText(daftarKomentar.get(position).getNama_kostum());
        holder.komentar.setText(daftarKomentar.get(position).getKomentar());

    }

    @Override
    public int getItemCount() {
        return  daftarKomentar.size();
    }

    public class KomentarViewHolder extends RecyclerView.ViewHolder {
        TextView tglTransaksi,namaPenyewa,namaKostum,komentar;
        ImageView imgKomentar;
        public KomentarViewHolder(@NonNull View itemView) {
            super(itemView);
            tglTransaksi= itemView.findViewById(R.id.tglTransaksi);
            namaPenyewa= itemView.findViewById(R.id.tNamaPenyewa);
            namaKostum = itemView.findViewById(R.id.tNama_kostum);
            komentar = itemView.findViewById(R.id.tKomentar);

        }
    }
}
