package ac.id.pradita.klinikhijauputih;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
                nama_pasien.setText(rm.getNama_pasien());
              //  idRekMedis.setText(rm.getId_rekMedis());
              //  idPasien.setText(rm.getId_pasien());
              //  idDokter.setText(rm.getId_dokter());
                anastesa.setText(rm.getAnastesa());
                diagnosa.setText(rm.getDiagnosa());
                terapi.setText(rm.getTerapi());
                resep.setText(rm.getResep());

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), EditRekamMedisDokterActivity.class);
                        intent.putExtra("id_rekMedis", rm.getId_rekMedis());
                        startActivity(intent);
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