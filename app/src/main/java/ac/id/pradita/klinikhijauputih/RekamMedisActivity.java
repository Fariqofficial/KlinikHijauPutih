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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ac.id.pradita.klinikhijauputih.model.Dokter;
import ac.id.pradita.klinikhijauputih.model.Pasien;
import ac.id.pradita.klinikhijauputih.model.RekamMedis;

public class RekamMedisActivity extends AppCompatActivity {
    RecyclerView rvRekamMedis;
    DatabaseReference reference;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekam_medis);

        getSupportActionBar().setTitle("Rekam Medis Staff");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvRekamMedis = findViewById(R.id.rvRekamMedis);
        rvRekamMedis.setLayoutManager(new LinearLayoutManager(this));
        reference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        
        getData();

    }

    private void getData() {
        Query mQuery = reference.child("Rekam Medis");
        mQuery.keepSynced(true);

        FirebaseRecyclerAdapter<RekamMedis, ListRekamMedisViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RekamMedis, ListRekamMedisViewHolder>
        (RekamMedis.class, R.layout.item_rekam_medis, ListRekamMedisViewHolder.class, mQuery) {
            @Override
            protected void populateViewHolder(ListRekamMedisViewHolder listRekamMedisViewHolder, RekamMedis rekamMedis, int i) {
                FirebaseDatabase.getInstance().getReference("Pasien").child(rekamMedis.getId_pasien()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Pasien pasien = snapshot.getValue(Pasien.class);
                        listRekamMedisViewHolder.setNamaPasien(pasien.getNama());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                FirebaseDatabase.getInstance().getReference("Dokter").child("id_dokter").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Dokter dokter = snapshot.getValue(Dokter.class);
                        listRekamMedisViewHolder.setNamaDokter(dokter.getNama());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                listRekamMedisViewHolder.setTglDibuat(rekamMedis.getTanggal());

                listRekamMedisViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), DetailRekamMedisDokterActivity.class);
                        intent.putExtra("id_rekMedis", rekamMedis.getId_rekMedis());
                        startActivity(intent);
                    }
                });
            }
        };

        rvRekamMedis.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ListRekamMedisViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView namaPasien, namaDokter, tglDibuat;

        public ListRekamMedisViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        void setNamaPasien(String nama_pasien) {
            namaPasien = mView.findViewById(R.id.namaPasien);
            namaPasien.setText(nama_pasien);
        }

        void setNamaDokter(String nama_dokter) {
            namaDokter = mView.findViewById(R.id.namaDokter);
            namaDokter.setText(nama_dokter);
        }

        void setTglDibuat(String tanggal_dibuat) {
            tglDibuat = mView.findViewById(R.id.tglDibuat);
            tglDibuat.setText(tanggal_dibuat);
        }
    }
}