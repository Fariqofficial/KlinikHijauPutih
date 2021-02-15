package ac.id.pradita.klinikhijauputih;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.google.common.reflect.Reflection.initialize;

public class DaftarDokterActivity extends AppCompatActivity {
    EditText etNoKTP, etNamaDokter, etAlamatDokter, etPoliDokter, etTelpDokter, etEmailDokter, etPasswordDokter;
    DatabaseReference reference;
    ProgressDialog dialog;
    Button btn_daftar;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    String ktp_dokter, nama_dokter, alamat_dokter, poli_dokter, telp_dokter, email_dokter, pass_dokter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_dokter);

        getSupportActionBar().setTitle("Daftar Dokter");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etNoKTP = findViewById(R.id.nomorKTPDokter);
        etNamaDokter = findViewById(R.id.nama_dokter);
        etAlamatDokter = findViewById(R.id.alamatDokter);
        etPoliDokter = findViewById(R.id.poliDokter);
        etTelpDokter = findViewById(R.id.telpDokter);
        etEmailDokter = findViewById(R.id.emailDokter);
        etPasswordDokter = findViewById(R.id.passDokter);
        btn_daftar = findViewById(R.id.daftarDokter);

        firebaseAuth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);

        user = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference();

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daftarDokter();
            }
        });

    }

    private void daftarDokter() {
        initialize();
        if (!validate()) {
            Toast.makeText(this, "Gagal Mendaftarkan Dokter!", Toast.LENGTH_LONG).show();
        } else {
            daftarDokterBerhasil(email_dokter, pass_dokter, ktp_dokter, nama_dokter, alamat_dokter, poli_dokter, telp_dokter);
        }
    }

    private boolean validate() {
        boolean valid = true;
        if (ktp_dokter.isEmpty()) {
            etNoKTP.setError("Harap Masukkan Nomor KTP Dokter!");
            valid = false;
        }
        if (nama_dokter.isEmpty()) {
            etNamaDokter.setError("Harap Masukkan Nama Dokter!");
            valid = false;
        }
        if (alamat_dokter.isEmpty()) {
            etAlamatDokter.setError("Harap Masukkan Alamat Dokter!");
            valid = false;
        }

        if (poli_dokter.isEmpty()) {
            etPoliDokter.setError("Harap Masukkan Poli Dokter!");
            valid = false;
        }
        if (telp_dokter.isEmpty()) {
            etTelpDokter.setError("Harap Masukkan Nomor Telpon Dokter!");
            valid = false;
        }
        if (email_dokter.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email_dokter).matches()) {
            etEmailDokter.setError("Harap Masukkan Email Dokter!");
            valid = false;
        }
        if (pass_dokter.isEmpty() || pass_dokter.length() < 8) {
            etPasswordDokter.setError("Harap Masukkan Password Lebih Dari 8 Karakter");
            valid = false;
        }
        return valid;
    }

    public void initialize() {
        ktp_dokter = etNoKTP.getText().toString().trim();
        nama_dokter = etNamaDokter.getText().toString().trim();
        alamat_dokter = etAlamatDokter.getText().toString().trim();
        poli_dokter = etPoliDokter.getText().toString().trim();
        telp_dokter = etTelpDokter.getText().toString().trim();
        email_dokter = etEmailDokter.getText().toString().trim();
        pass_dokter = etPasswordDokter.getText().toString().trim();
    }

    private void daftarDokterBerhasil(String email_dokter, String pass_dokter, String ktp_dokter, String nama_dokter, String alamat_dokter, String poli_dokter, String telp_dokter) {
        dialog.show();
        dialog.setMessage("Mohon Tunggu..");

        firebaseAuth.createUserWithEmailAndPassword(email_dokter, pass_dokter)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        assert user != null;

                        String dokterId = user.getUid();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Dokter").child(dokterId);

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("id_dokter", dokterId);
                        hashMap.put("ktp", ktp_dokter);
                        hashMap.put("nama", nama_dokter);
                        hashMap.put("alamat", alamat_dokter);
                        hashMap.put("poli", poli_dokter);
                        hashMap.put("telpon", telp_dokter);
                        hashMap.put("email", email_dokter);
                        hashMap.put("password", pass_dokter);

                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                FirebaseAuth.getInstance().signOut();
                                Toast.makeText(getApplicationContext(), "Berhasil Mendaftarkan Dokter!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                dialog.dismiss();
                                finish();
                            }
                        });
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