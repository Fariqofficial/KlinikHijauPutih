package ac.id.pradita.klinikhijauputih;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import ac.id.pradita.klinikhijauputih.model.RekamMedis;

public class EditRekamMedisDokterActivity extends AppCompatActivity {

    TextView id_dokter, id_pasien;
    EditText anastesa, diagnosa, terapi, resep;
    ProgressDialog dialog;
    Button btnEdit;
    String id_rekamMedis, idDokter, idPasien, str_anastesa, str_diagnosa, str_terapi, str_resep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rekam_medis_dokter);

        getSupportActionBar().setTitle("Edit Rekam Medis");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id_dokter = findViewById(R.id.id_dokter);
        id_pasien = findViewById(R.id.id_pasien);
        anastesa = findViewById(R.id.edtAnastesa);
        diagnosa = findViewById(R.id.edtDiagnosa);
        terapi = findViewById(R.id.edtTerapi);
        resep = findViewById(R.id.edtResep);
        btnEdit = findViewById(R.id.edtRekMedisDokter);

        id_rekamMedis = getIntent().getStringExtra("id_rekMedis");

        dialog = new ProgressDialog(this);

        getDataRekMedis(id_rekamMedis);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialize();
                editRekMedis(id_rekamMedis, idDokter, idPasien, str_anastesa, str_diagnosa, str_terapi, str_resep);
            }
        });

    }

    private void editRekMedis(String id_rekamMedis, String idDokter, String idPasien, String str_anastesa, String str_diagnosa, String str_terapi, String str_resep) {
        dialog.show();
        dialog.setMessage("Updating..");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Rekam Medis").child(id_rekamMedis);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("anastesa", str_anastesa);
        hashMap.put("diagnosa", str_diagnosa);
        hashMap.put("terapi", str_terapi);
        hashMap.put("resep", str_resep);

        reference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Data Berhasil Diubah!", Toast.LENGTH_SHORT).show();
                    EditRekamMedisDokterActivity.super.onBackPressed();
                    dialog.dismiss();
                }
            }
        });
    }

    public void initialize() {
        str_anastesa = anastesa.getText().toString().trim();
        str_diagnosa = diagnosa.getText().toString().trim();
        str_terapi = terapi.getText().toString().trim();
        str_resep = resep.getText().toString().trim();
    }

    private void getDataRekMedis(String id_rekamMedis) {
        dialog.show();
        dialog.setMessage("Mohon Tunggu..");
        FirebaseDatabase.getInstance().getReference("Rekam Medis").child(id_rekamMedis)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        RekamMedis rekMed = snapshot.getValue(RekamMedis.class);

                        anastesa.setText(rekMed.getAnastesa());
                        diagnosa.setText(rekMed.getDiagnosa());
                        terapi.setText(rekMed.getTerapi());
                        resep.setText(rekMed.getResep());
                        id_dokter.setText(rekMed.getId_dokter());
                        id_pasien.setText(rekMed.getId_pasien());

                        dialog.dismiss();
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