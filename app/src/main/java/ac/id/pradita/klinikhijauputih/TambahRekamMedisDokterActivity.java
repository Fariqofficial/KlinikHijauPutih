package ac.id.pradita.klinikhijauputih;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.google.common.reflect.Reflection.initialize;

public class TambahRekamMedisDokterActivity extends AppCompatActivity {

    EditText edtNamaDokter, edtNamaPasien, edtAnastesa, edtDiagnosa, edtTerapi, edtResep;
    Button btnTambah;
    DatabaseReference reference;
    ProgressDialog dialog;
    FirebaseUser user;
    String nama_dokter, nama_pasien, anastesa, diagnosa, terapi, resep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_rekam_medis_dokter);

        edtNamaDokter = findViewById(R.id.nama_dokter);
        edtNamaPasien = findViewById(R.id.nama_pasien);
        edtAnastesa = findViewById(R.id.anastesa);
        edtDiagnosa = findViewById(R.id.diagnosa);
        edtTerapi = findViewById(R.id.terapi);
        edtResep = findViewById(R.id.resep);
        btnTambah = findViewById(R.id.tambahRekMedisDokter);

        dialog = new ProgressDialog(this);

        user = FirebaseAuth.getInstance().getCurrentUser();

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
            Toast.makeText(this, "Gagal Menginput Data!", Toast.LENGTH_LONG).show();
        } else {
            tambahData(nama_dokter, nama_pasien, anastesa, diagnosa, terapi, resep);
        }
    }

    private void tambahData(String nama_dokter, String nama_pasien, String anastesa, String diagnosa, String terapi, String resep) {
        dialog.show();
        dialog.setMessage("Mohon Tunggu..");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Rekam Medis");

        DatabaseReference databaseReference = reference.push();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id_rekamMedis", databaseReference.getKey());
    }

    private boolean validate(){
         boolean valid = true;
         if (nama_dokter.isEmpty()){
             edtNamaDokter.setError("Harap Masukan Nama Dokter!");
             valid = false;
         }
         if (nama_pasien.isEmpty()){
             edtNamaPasien.setError("Harap Masukan Nama Pasien!");
             valid = false;
         }
         if (anastesa.isEmpty()){
             edtAnastesa.setError("Harap Masukan Anastesa!");
             valid = false;
         }
         if (diagnosa.isEmpty()){
             edtDiagnosa.setError("Harap Masukan Diagnosa!");
             valid = false;
         }
         if (terapi.isEmpty()){
             edtTerapi.setError("Harap Masukan Terapi!");
             valid = false;
         }
         if (resep.isEmpty()){
             edtResep.setError("Harap Masukan Resep");
         }
          return valid;
    }
}