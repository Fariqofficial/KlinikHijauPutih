package ac.id.pradita.klinikhijauputih;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ac.id.pradita.klinikhijauputih.model.Pasien;
import ac.id.pradita.klinikhijauputih.model.RekamMedis;

public class DetailRekamMedis extends AppCompatActivity {
    TextView nama_pasien, idRekMedis, idPasien, idDokter, anastesa, diagnosa, terapi, resep, tglResep;
    FloatingActionButton print;
    ProgressDialog dialog;
    String id_rekamMedis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_rekam_medis);

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
        print = findViewById(R.id.print);

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
                        if (snapshot.exists()) {
                            if (snapshot.exists()) {
                                Pasien pasien = snapshot.getValue(Pasien.class);
                                nama_pasien.setText(pasien.getNama());
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                print.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), PrintDetailRekamMedisActivity.class);
                    intent.putExtra("nama_pasien", nama_pasien.getText().toString().trim());
                    intent.putExtra("anastesa", rm.getAnastesa());
                    intent.putExtra("diagnosa", rm.getDiagnosa());
                    intent.putExtra("terapi", rm.getTerapi());
                    intent.putExtra("resep", rm.getResep());
                    intent.putExtra("tanggal", rm.getTanggal());
                    intent.putExtra("id_rekMedis", rm.getId_rekMedis());
                    intent.putExtra("id_dokter", rm.getId_dokter());
                    intent.putExtra("id_pasien", rm.getId_pasien());
                    startActivity(intent);
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