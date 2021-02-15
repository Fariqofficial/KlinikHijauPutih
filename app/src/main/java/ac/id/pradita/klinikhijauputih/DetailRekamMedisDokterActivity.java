package ac.id.pradita.klinikhijauputih;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ac.id.pradita.klinikhijauputih.model.Pasien;
import ac.id.pradita.klinikhijauputih.model.RekamMedis;

public class DetailRekamMedisDokterActivity extends AppCompatActivity {
    TextView nama_pasien, idRekMedis, idPasien, idDokter, anastesa, diagnosa, terapi, resep, tglResep;
    Button edit, hapus;
    ProgressDialog dialog;
    String id_rekamMedis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_rekam_medis_dokter);

        getSupportActionBar().setTitle("Detail Rekam Medis");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nama_pasien = findViewById(R.id.nama_pasien);
        idRekMedis = findViewById(R.id.idRekMedis);
        idPasien = findViewById(R.id.idPasien);
        idDokter = findViewById(R.id.idDokter);
        anastesa = findViewById(R.id.anastesa);
        diagnosa = findViewById(R.id.diagnosa);
        terapi = findViewById(R.id.terapi);
        resep = findViewById(R.id.resep);
        tglResep = findViewById(R.id.tglResep);
        edit = findViewById(R.id.editDtlRekMedis);
        hapus = findViewById(R.id.hapusDtlRekMedis);

        id_rekamMedis = getIntent().getStringExtra("id_rekMedis");

        dialog = new ProgressDialog(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData(id_rekamMedis);
    }

    private void getData(String id_rekamMedis) {
        FirebaseDatabase.getInstance().getReference("Rekam Medis").child(id_rekamMedis).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RekamMedis rm = snapshot.getValue(RekamMedis.class);
                anastesa.setText(rm.getAnastesa());
                diagnosa.setText(rm.getDiagnosa());
                terapi.setText(rm.getTerapi());
                resep.setText(rm.getResep());
                tglResep.setText(rm.getTanggal());
                idRekMedis.setText(rm.getId_rekMedis());
                idDokter.setText(rm.getId_dokter());
                idPasien.setText(rm.getId_pasien());

                FirebaseDatabase.getInstance().getReference("Pasien").child(rm.getId_pasien()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Pasien pasien = snapshot.getValue(Pasien.class);
                            nama_pasien.setText(pasien.getNama());
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), EditRekamMedisDokterActivity.class);
                        intent.putExtra("id_rekMedis", rm.getId_rekMedis());
                        startActivity(intent);
                    }
                });

                hapus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailRekamMedisDokterActivity.this);
                        builder.setMessage("Apakah Anda Yakin Hapus Data Ini?");
                        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getApplicationContext(), "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                                FirebaseDatabase.getInstance().getReference("Rekam Medis").child(id_rekamMedis).removeValue();
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