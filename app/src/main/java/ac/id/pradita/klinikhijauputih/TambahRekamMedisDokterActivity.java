package ac.id.pradita.klinikhijauputih;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class TambahRekamMedisDokterActivity extends AppCompatActivity {

    EditText edtAnastesa, edtDiagnosa, edtTerapi, edtResep;
    Button btnTambah;
    DatabaseReference reference;
    ProgressDialog dialog;
    FirebaseUser user;
    TextView tvIdDokter, tvIdPasien;
    String id_pasien, anastesa, diagnosa, terapi, resep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_rekam_medis_dokter);

        getSupportActionBar().setTitle("Tambah Rekam Medis");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvIdDokter = findViewById(R.id.id_dokter);
        tvIdPasien = findViewById(R.id.id_pasien);
        edtAnastesa = findViewById(R.id.anastesa);
        edtDiagnosa = findViewById(R.id.diagnosa);
        edtTerapi = findViewById(R.id.terapi);
        edtResep = findViewById(R.id.resep);
        btnTambah = findViewById(R.id.tambahRekMedisDokter);

        id_pasien = getIntent().getStringExtra("id_pasien");

        dialog = new ProgressDialog(this);

        user = FirebaseAuth.getInstance().getCurrentUser();

        tvIdDokter.setText(user.getUid());
        tvIdPasien.setText(id_pasien);

        reference = FirebaseDatabase.getInstance().getReference();

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahRekamMedis();
            }
        });

    }

    private void tambahRekamMedis() {
        initialize();
        if (!validate()) {
            Toast.makeText(this, "Gagal Menambahkan Data!", Toast.LENGTH_LONG).show();
        } else {
            tambahData(id_pasien, anastesa, diagnosa, terapi, resep);
        }
    }

    private void tambahData(String idPasien, String anastesa, String diagnosa, String terapi, String resep) {
        dialog.show();
        dialog.setMessage("Mohon Tunggu..");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Rekam Medis");

        DatabaseReference databaseReference = reference.push();

        Date currentDate = new Date();
        long currentTime = currentDate.getTime();
        Timestamp timestamp = new Timestamp(currentTime);
        String currentTimeStamp = new SimpleDateFormat("dd-MM-yyyy").format(timestamp);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id_rekMedis", databaseReference.getKey());
        hashMap.put("id_dokter", user.getUid());
        hashMap.put("id_pasien", idPasien);
        hashMap.put("anastesa", anastesa);
        hashMap.put("diagnosa", diagnosa);
        hashMap.put("terapi", terapi);
        hashMap.put("resep", resep);
        hashMap.put("tanggal", currentTimeStamp);

        databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Berhasil Menambahkan Data!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), RekamMedisDokterActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
            }
        });
    }

    private boolean validate() {
        boolean valid = true;
        if (anastesa.isEmpty()) {
            edtAnastesa.setError("Harap Masukan Anastesa!");
            valid = false;
        }
        if (diagnosa.isEmpty()) {
            edtDiagnosa.setError("Harap Masukan Diagnosa!");
            valid = false;
        }
        if (terapi.isEmpty()) {
            edtTerapi.setError("Harap Masukan Terapi!");
            valid = false;
        }
        if (resep.isEmpty()) {
            edtResep.setError("Harap Masukan Resep");
            valid = false;
        }
        return valid;
    }

    public void initialize() {
        anastesa = edtAnastesa.getText().toString().trim();
        diagnosa = edtDiagnosa.getText().toString().trim();
        terapi = edtTerapi.getText().toString().trim();
        resep = edtResep.getText().toString().trim();
    }
}