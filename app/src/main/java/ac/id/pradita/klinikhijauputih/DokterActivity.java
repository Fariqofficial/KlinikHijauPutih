package ac.id.pradita.klinikhijauputih;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import ac.id.pradita.klinikhijauputih.model.Dokter;

public class DokterActivity extends AppCompatActivity {
    RecyclerView rv_dokter;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokter);
        
        rv_dokter = findViewById(R.id.rvDokter);
        rv_dokter.setLayoutManager(new LinearLayoutManager(this));
        reference = FirebaseDatabase.getInstance().getReference();
        
        getDataDokter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDataDokter();
    }

    private void getDataDokter() {
        Query mQuery = reference.child("Dokter");
        mQuery.keepSynced(true);

        FirebaseRecyclerAdapter<Dokter, ListDokterViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Dokter, ListDokterViewHolder>
                (Dokter.class, R.layout.item_dokter, ListDokterViewHolder.class, mQuery) {
            @Override
            protected void populateViewHolder(ListDokterViewHolder listDokterViewHolder, Dokter dokter, int position) {
                listDokterViewHolder.setNamaDokter(dokter.getNama());
                listDokterViewHolder.setHariPraktek(dokter.getHari());
                listDokterViewHolder.setPoli(dokter.getPoli());

                listDokterViewHolder.mView.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), DetailDokterActivity.class);
                    intent.putExtra("id_dokter", dokter.getId_dokter());
                    startActivity(intent);
                });
            }
        };
        rv_dokter.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ListDokterViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView tvNamaDokter, tvHari, tvPoli;

        public ListDokterViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        void setNamaDokter(String nama_dokter) {
            tvNamaDokter = mView.findViewById(R.id.nama_dokter);
            tvNamaDokter.setText(nama_dokter);
        }

        void setHariPraktek(String hariPraktek) {
            tvHari = mView.findViewById(R.id.hariPraktek);
            tvHari.setText(hariPraktek);
        }

        void setPoli(String poliDokter) {
            tvPoli = mView.findViewById(R.id.poliDokter);
            tvPoli.setText(poliDokter);
        }
    }
}