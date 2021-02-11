package ac.id.pradita.klinikhijauputih;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import ac.id.pradita.klinikhijauputih.model.Pasien;

public class EditPasienActivity extends AppCompatActivity {
    EditText etNamaPasien, etNomorKtpPasien, etAlamatPasien, etUmurPasien, etTelpPasien, etNamaIbuPasien, etNamAPasanganPasien;
    DatabaseReference reference;
    ProgressDialog dialog;
    FirebaseUser user;
    Button btnEdit;
    Spinner edtJenkel, edtStatus;
    String id_pasien, nomorKTP, namaPasien, alamatPasien, umurPasien, noTelpPasien, ibuPasien, pasanganPasien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pasien);

        etNomorKtpPasien = findViewById(R.id.editKtpPasien);
        etNamaPasien = findViewById(R.id.editNamaPasien);
        etAlamatPasien = findViewById(R.id.edtAlamatPasien);
        edtJenkel = findViewById(R.id.edtSpinnerJenkel);
        etUmurPasien = findViewById(R.id.edtUmur);
        etTelpPasien = findViewById(R.id.edtTelpPasien);
        edtStatus = findViewById(R.id.edtSpinnerStatus);
        etNamaIbuPasien = findViewById(R.id.edtIbuPasien);
        etNamAPasanganPasien = findViewById(R.id.edtPasanganPasien);
        btnEdit = findViewById(R.id.editDataPasien);

        id_pasien = getIntent().getStringExtra("id_pasien");

        dialog = new ProgressDialog(this);

        getDataPasien(id_pasien);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialize();
                editDataPasien(id_pasien, nomorKTP, namaPasien, alamatPasien, umurPasien, noTelpPasien, ibuPasien, pasanganPasien);
                }
        });

    }

    private void editDataPasien(String id_pasien, String nomorKTP, String namaPasien, String alamatPasien, String umurPasien, String noTelpPasien, String ibuPasien, String pasanganPasien) {
        dialog.show();
        dialog.setMessage("Updating..");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Pasien").child(id_pasien);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("no_ktp", nomorKTP);
        hashMap.put("nama", namaPasien);
        hashMap.put("alamat", alamatPasien);
    //  hashMap.put("jenis_kelamin", rt_edit);
        hashMap.put("umur", umurPasien);
   //   hashMap.put("status", kec_edit);
        hashMap.put("no_hp", noTelpPasien);
        hashMap.put("nama_ibu", ibuPasien);
        hashMap.put("nama_pasangan", pasanganPasien);

        reference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Data Berhasil Diubah!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
            }
        });

    }

    public void initialize() {
        nomorKTP = etNomorKtpPasien.getText().toString().trim();
        namaPasien = etNamaPasien.getText().toString().trim();
        alamatPasien = etAlamatPasien.getText().toString().trim();
        umurPasien = etUmurPasien.getText().toString().trim();
        noTelpPasien = etTelpPasien.getText().toString().trim();
        ibuPasien = etNamaIbuPasien.getText().toString().trim();
        pasanganPasien = etNamAPasanganPasien.getText().toString().trim();
    }


    private void getDataPasien(String idPasien) {
        dialog.show();
        dialog.setMessage("Mohon Tunggu..");
        FirebaseDatabase.getInstance().getReference("Pasien").child(idPasien)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Pasien pasien = snapshot.getValue(Pasien.class);

                        etNomorKtpPasien.setText(pasien.getNo_ktp());
                        etNamaPasien.setText(pasien.getNama());
                        etAlamatPasien.setText(pasien.getAlamat());
                        etUmurPasien.setText(pasien.getUmur());
                        etNamaIbuPasien.setText(pasien.getNama_ibu());
                        etNamAPasanganPasien.setText(pasien.getNama_pasangan());

                        dialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Maaf Terjadi Kesalahan, Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
    }
}