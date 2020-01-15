package com.example.sikostumtempat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class BerandaFragment extends Fragment {
    private Button tempat_sewa,kostum, riwayat,komentar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView =inflater.inflate(R.layout.fragment_beranda, null);

        tempat_sewa = rootView.findViewById(R.id.tempat_sewa_menu);
        kostum = rootView.findViewById(R.id.kostum_menu);
        riwayat = rootView.findViewById(R.id.riwayat_menu);
        komentar = rootView.findViewById(R.id.komentar_menu);

        tempat_sewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TempatSewaActivity.class);
                startActivity(intent);
            }
        });
        kostum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getContext(),KostumActivity.class);
                startActivity(intent);
            }
        });
        riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(),RiwayatActivity.class);
                startActivity(intent);
            }
        });
        komentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(), KomentarActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
