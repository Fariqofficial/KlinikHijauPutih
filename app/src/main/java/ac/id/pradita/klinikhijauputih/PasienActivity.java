package ac.id.pradita.klinikhijauputih;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.annotations.NotNull;

import ac.id.pradita.klinikhijauputih.model.Pasien;

public class PasienActivity extends AppCompatActivity {

    RecyclerView rv_pasien;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasien);

        rv_pasien = findViewById(R.id.rvPasien);

        rv_pasien.setLayoutManager(new LinearLayoutManager(this));

        reference = FirebaseDatabase.getInstance().getReference();

        getListData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getListData();
    }

    private void getListData() {
        Query mQuery = reference.child("Pasien");
        mQuery.keepSynced(true);

        FirebaseRecyclerAdapter<Pasien, ListPasienViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Pasien, ListPasienViewHolder>(
                Pasien.class, R.layout.item_pasien, ListPasienViewHolder.class, mQuery) {
            @Override
            protected void populateViewHolder(@NotNull ListPasienViewHolder listPasienViewHolder, Pasien pasien, int position) {
                listPasienViewHolder.setNamaPasien(pasien.getNama());
                listPasienViewHolder.setJenkel(pasien.getJenis_kelamin());
                listPasienViewHolder.setUmurPasien(pasien.getUmur());

                listPasienViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // tulis intent disini bwang
                        startActivity(new Intent(getApplicationContext(), DetailPasienActivity.class));

                    }
                });
            }
        };
        rv_pasien.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ListPasienViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView tvNamaPasien, tvJenkel, tvUmur;

        public ListPasienViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        void setNamaPasien(String nama_pasien) {
            tvNamaPasien = mView.findViewById(R.id.nama_pasien);
            tvNamaPasien.setText(nama_pasien);
        }

        void setJenkel(String jenis_kelamin) {
            tvJenkel = mView.findViewById(R.id.jenkel);
            tvJenkel.setText(jenis_kelamin);
        }

        void setUmurPasien(String umur_pasien) {
            tvUmur = mView.findViewById(R.id.umur);
            tvUmur.setText(umur_pasien);
        }
    }
}