package ac.id.pradita.klinikhijauputih;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

//                editDataPasien();
            }
        });

    }

    public void initialize() {

    }

    private void editDataPasien(String edit_ktp, String edit_nama, String edit_alamat, String edit_jenkel, String edit_umur, String edit_status,
                                String edit_namaIbu, String edit_pasangan) {

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