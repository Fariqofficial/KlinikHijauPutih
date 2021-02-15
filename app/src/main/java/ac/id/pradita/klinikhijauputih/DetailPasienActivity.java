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

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ac.id.pradita.klinikhijauputih.model.Pasien;


public class DetailPasienActivity extends AppCompatActivity {

    TextView tvKTP, tvNama, tvAlamat, tvJenkel, tvUmur, tvStatus, tvTelpon, tvIbuPasien, tvPasangan;
    ProgressDialog dialog;
    Button edit, hapus;
    String id_pasien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pasien);

        getSupportActionBar().setTitle("Detail Pasien");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvKTP = findViewById(R.id.ktpPasien);
        tvNama = findViewById(R.id.namaPasien);
        tvAlamat = findViewById(R.id.alamatPasien);
        tvJenkel = findViewById(R.id.jenkel);
        tvUmur = findViewById(R.id.umur);
        tvStatus = findViewById(R.id.status);
        tvTelpon = findViewById(R.id.telpPasien);
        tvIbuPasien = findViewById(R.id.ibuPasien);
        tvPasangan = findViewById(R.id.pasanganPasien);
        edit = findViewById(R.id.editDtlPasien);
        hapus = findViewById(R.id.hapusDtlPasien);

        id_pasien = getIntent().getStringExtra("id_pasien");
        Log.d("cekId", id_pasien);

        dialog = new ProgressDialog(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData(id_pasien);
    }

    private void getData(String idPasien) {
        FirebaseDatabase.getInstance().getReference("Pasien").child(idPasien).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Pasien pasien = snapshot.getValue(Pasien.class);

                tvNama.setText(pasien.getNama());
                tvKTP.setText(pasien.getNo_ktp());
                tvAlamat.setText(pasien.getAlamat());
                tvJenkel.setText(pasien.getJenis_kelamin());
                tvUmur.setText(pasien.getUmur());
                tvStatus.setText(pasien.getStatus());
                tvTelpon.setText(pasien.getNo_hp());
                tvIbuPasien.setText(pasien.getNama_ibu());
                tvPasangan.setText(pasien.getNama_pasangan());

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), EditPasienActivity.class);
                        intent.putExtra("id_pasien", pasien.getId_pasien());
                        startActivity(intent);
                    }
                });

                hapus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailPasienActivity.this);
                        builder.setMessage("Apakah Anda Yakin Hapus Data Ini?");
                        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getApplicationContext(), "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                                FirebaseDatabase.getInstance().getReference("Pasien").child(id_pasien).removeValue();
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