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

import static com.google.common.reflect.Reflection.initialize;

public class DaftarDokterActivity extends AppCompatActivity {
    EditText etNoKTP, etNamaDokter, etAlamatDokter, etPoliDokter, etTelpDokter, etUsernameDokter, etPasswordDokter;
    DatabaseReference reference;
    ProgressDialog dialog;
    Button btn_daftar;
    FirebaseUser user;
    String ktp_dokter, nama_dokter, alamat_dokter, poli_dokter, telp_dokter, username_dokter, pass_dokter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_dokter);

        etNoKTP = findViewById(R.id.nomorKTPDokter);
        etNamaDokter = findViewById(R.id.nama_dokter);
        etAlamatDokter = findViewById(R.id.alamatDokter);
        etPoliDokter = findViewById(R.id.poliDokter);
        etTelpDokter = findViewById(R.id.telpDokter);
        etUsernameDokter = findViewById(R.id.usernameDokter);
        etPasswordDokter = findViewById(R.id.passDokter);
        btn_daftar = findViewById(R.id.daftarDokter);

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
            daftarDokterBerhasil(ktp_dokter, nama_dokter, alamat_dokter, poli_dokter, telp_dokter, username_dokter, pass_dokter);
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
        if (username_dokter.isEmpty()) {
            etUsernameDokter.setError("Harap Masukkan Username Dokter!");
            valid = false;
        }
        if (pass_dokter.isEmpty() || pass_dokter.length() < 8) {
            etPasswordDokter.setError("Harap Masukkan Password Lebih Dari 8 Karakter");
        }
        return valid;
    }

    private void daftarDokterBerhasil(String ktp_dokter, String nama_dokter, String alamat_dokter, String poli_dokter, String telp_dokter, String username_dokter, String pass_dokter) {
    }
}