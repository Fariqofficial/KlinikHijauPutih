package ac.id.pradita.klinikhijauputih.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ac.id.pradita.klinikhijauputih.EditPasienActivity;
import ac.id.pradita.klinikhijauputih.R;
import ac.id.pradita.klinikhijauputih.model.Pasien;

public class PasienAdapter extends RecyclerView.Adapter {

    List<Pasien> pasiens;

    public PasienAdapter(List<Pasien> pasiens) {
        this.pasiens = pasiens;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pasien, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Pasien pasien = pasiens.get(position);

        viewHolder.namaPasien.setText(pasien.getNama());
        viewHolder.jenisKelamin.setText(pasien.getJenis_kelamin());
        viewHolder.umurPasien.setText(pasien.getUmur());
        
    }

    @Override
    public int getItemCount() {
        return pasiens.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView namaPasien, jenisKelamin, umurPasien;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            namaPasien = itemView.findViewById(R.id.nama_pasien);
            jenisKelamin = itemView.findViewById(R.id.jenkel);
            umurPasien = itemView.findViewById(R.id.umur);
        }
    }
}