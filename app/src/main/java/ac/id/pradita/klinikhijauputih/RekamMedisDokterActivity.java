package ac.id.pradita.klinikhijauputih;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class RekamMedisDokterActivity extends AppCompatActivity {
    FloatingActionButton fabListPasien;
    RecyclerView rvRekMedisDokter;
    DatabaseReference reference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekam_medis_dokter);

        getSupportActionBar().setTitle("Rekam Medis Dokter");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvRekMedisDokter = findViewById(R.id.rvRekMedisDokter);

        rvRekMedisDokter.setLayoutManager(new LinearLayoutManager(this));

        fabListPasien = findViewById(R.id.fabListPasien);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference();

        fabListPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PasienActivity.class);
                intent.putExtra("page_request", "RM_Dokter");
                startActivity(intent);
            }
        });

        getListData();
    }

    private void getListData() {
        Query mQuery = reference.child("Rekam Medis").orderByChild("id_dokter").equalTo(firebaseUser.getUid());
        mQuery.keepSynced(true);

        FirebaseRecyclerAdapter<RekamMedis, ListRekamMedisViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RekamMedis, ListRekamMedisViewHolder>(
                RekamMedis.class, R.layout.item_rekam_medis, ListRekamMedisViewHolder.class, mQuery) {
            @Override
            protected void populateViewHolder(ListRekamMedisViewHolder listRekamMedisViewHolder, RekamMedis rekamMedis, int i) {
                FirebaseDatabase.getInstance().getReference("Pasien").child(rekamMedis.getId_pasien()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Pasien pasien = snapshot.getValue(Pasien.class);
                            listRekamMedisViewHolder.setNamaPasien(pasien.getNama());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                FirebaseDatabase.getInstance().getReference("Dokter").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Dokter dokter = snapshot.getValue(Dokter.class);
                            listRekamMedisViewHolder.setNamaDokter(dokter.getNama());
                        }
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
        rvRekMedisDokter.setAdapter(firebaseRecyclerAdapter);
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

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}