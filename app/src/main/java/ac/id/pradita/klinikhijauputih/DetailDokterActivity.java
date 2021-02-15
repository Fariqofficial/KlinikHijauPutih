package ac.id.pradita.klinikhijauputih;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ac.id.pradita.klinikhijauputih.model.Dokter;

public class DetailDokterActivity extends AppCompatActivity {
    TextView tvNama, tvNoKtp, tvAlamat, tvTelp, tvPoli, tvEmail, tvHariPraktek;
    ProgressDialog dialog;
    Button edit, hapus, perbaruiJadwal;
    String id_dokter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dokter);

        getSupportActionBar().setTitle("Detail Dokter & Jadwal");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvNama = findViewById(R.id.nama_dokter);
        tvNoKtp = findViewById(R.id.nomorKTPDokter);
        tvAlamat = findViewById(R.id.alamatDokter);
        tvTelp = findViewById(R.id.telpDokter);
        tvPoli = findViewById(R.id.poliDokter);
        tvEmail = findViewById(R.id.emailDokter);
        edit = findViewById(R.id.editDtlDokter);
        hapus = findViewById(R.id.hapusDtlDokter);
        perbaruiJadwal = findViewById(R.id.editJadwalDokter);
        tvHariPraktek = findViewById(R.id.hariPraktek);

        id_dokter = getIntent().getStringExtra("id_dokter");

        dialog = new ProgressDialog(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getDetailDokter(id_dokter);
    }

    private void getDetailDokter(String id_dokter) {
        FirebaseDatabase.getInstance().getReference("Dokter").child(id_dokter).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Dokter dokter = snapshot.getValue(Dokter.class);

                tvNama.setText(dokter.getNama());
                tvNoKtp.setText(dokter.getKtp());
                tvAlamat.setText(dokter.getAlamat());
                tvTelp.setText(dokter.getTelpon());
                tvPoli.setText(dokter.getPoli());
                tvEmail.setText(dokter.getEmail());
                tvHariPraktek.setText(dokter.getHari());

                perbaruiJadwal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), EditJadwalActivity.class);
                        intent.putExtra("id_dokter", dokter.getId_dokter());
                        startActivity(intent);
                    }
                });

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), EditDokterActivity.class);
                        intent.putExtra("id_dokter", dokter.getId_dokter());
                        startActivity(intent);
                    }
                });

                hapus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailDokterActivity.this);
                        builder.setMessage("Apakah Anda Yakin Hapus Data Ini?");
                        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getApplicationContext(), "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                                FirebaseDatabase.getInstance().getReference("Dokter").child(id_dokter).removeValue();
                                Intent intent = new Intent(getApplicationContext(), RekamMedisDokterActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
                        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Terjadi Kesalahan, Silahkan Coba Lagi", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}