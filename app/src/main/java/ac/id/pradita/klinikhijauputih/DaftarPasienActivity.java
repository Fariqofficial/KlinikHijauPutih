package ac.id.pradita.klinikhijauputih;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DaftarPasienActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText no_ktp, nama_pasien, alamat_rumah, umur, no_telp, nama_ibu, nama_pasangan;
    DatabaseReference reference;
    ProgressDialog dialog;
    Button btn_daftar;
    FirebaseUser user;
    Spinner jenkel, status;
    String nomorKTP, namaPasien, alamatPasien, umurPasien, noTelpPasien, ibuPasien, pasanganPasien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pasien);

        no_ktp = findViewById(R.id.no_KtpPasien);
        nama_pasien = findViewById(R.id.namaPasien);
        alamat_rumah = findViewById(R.id.alamatPasien);
        umur = findViewById(R.id.umur);
        no_telp = findViewById(R.id.telpPasien);
        nama_ibu = findViewById(R.id.ibuPasien);
        nama_pasangan = findViewById(R.id.pasanganPasien);
        jenkel = findViewById(R.id.spinnerJenkel);
        status = findViewById(R.id.spinnerStatus);
        btn_daftar = findViewById(R.id.daftarPasien);

        dialog = new ProgressDialog(this);

        user = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference();

        jenkel.setOnItemSelectedListener(this);
        String jeniskelamin[] = {"Laki - Laki", "Perempuan"};
        jenkel.setPrompt("Pilih Jenis Kelamin");

        ArrayAdapter adapterJenkel = new ArrayAdapter(this, android.R.layout.simple_list_item_1, jeniskelamin);
        jenkel.setOnItemSelectedListener(this);
        jenkel.setAdapter(adapterJenkel);

        status.setOnItemSelectedListener(this);
        String statusPerkawinan[] = {"Menikah", "Belum Menikah"};
        status.setPrompt("Pilih Status Perkawinan");

        ArrayAdapter adapterStatus = new ArrayAdapter(this, android.R.layout.simple_list_item_1, statusPerkawinan);
        status.setOnItemSelectedListener(this);
        status.setAdapter(adapterStatus);


        btn_daftar.setOnClickListener(v -> {
            daftarPasien();
        });

    }

    private void daftarPasien() {
        initialize();
        if (!validate()) {
            Toast.makeText(this, "Gagal Mendaftarkan Pasien!", Toast.LENGTH_LONG).show();
        } else {
            daftarPasienSukses(nomorKTP, namaPasien, alamatPasien, umurPasien, noTelpPasien, ibuPasien, pasanganPasien);
        }
    }

    private boolean validate() {
        boolean valid = true;
        if (nomorKTP.isEmpty()) {
            no_ktp.setError("Harap Masukkan Nomor KTP Pasien!");
            valid = false;
        }
        if (namaPasien.isEmpty()) {
            nama_pasien.setError("Harap Masukkan Nama Pasien!");
            valid = false;
        }
        if (alamatPasien.isEmpty()) {
            alamat_rumah.setError("Harap Masukkan Alamat Pasien!");
            valid = false;
        }

        if (umurPasien.isEmpty()) {
            umur.setError("Harap Masukkan Umur Pasien!");
            valid = false;
        }
        if (noTelpPasien.isEmpty()) {
            no_telp.setError("Harap Masukkan Nomor Handphone Pasien!");
            valid = false;
        }
        if (ibuPasien.isEmpty()) {
            nama_ibu.setError("Harap Masukkan Nama Ibu Pasien!");
            valid = false;
        }
        if (pasanganPasien.isEmpty()) {
            nama_pasangan.setError("Harap Masukkan Nama Pasangan atau Boleh Diisi '-' Jika Tidak Ada ");
        }
        return valid;
    }

    public void initialize(){
        nomorKTP = no_ktp.getText().toString().trim();
        namaPasien = nama_pasien.getText().toString().trim();
        alamatPasien = alamat_rumah.getText().toString().trim();
        umurPasien = umur.getText().toString().trim();
        noTelpPasien = no_telp.getText().toString().trim();
        ibuPasien = nama_ibu.getText().toString().trim();
        pasanganPasien = nama_pasangan.getText().toString().trim();
    }

    private void daftarPasienSukses(String nomorKTP, String namaPasien, String alamatPasien, String umurPasien, String noTelpPasien, String ibuPasien, String pasanganPasien) {
        dialog.show();
        dialog.setMessage("Mohon Tunggu..");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Pasien");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id_pasien", reference.push().getKey());
        hashMap.put("no_ktp", nomorKTP);
        hashMap.put("nama", namaPasien);
        hashMap.put("alamat", alamatPasien);
        hashMap.put("jenis_kelamin", jenkel.getSelectedItem().toString());
        hashMap.put("umur", umurPasien);
        hashMap.put("status", status.getSelectedItem().toString());
        hashMap.put("no_hp", noTelpPasien);
        hashMap.put("nama_ibu", ibuPasien);
        hashMap.put("nama_pasangan", pasanganPasien);

        reference.push().setValue(hashMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getApplicationContext(), "Berhasil Mendaftarkan Pasien!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String memilih_item = (String) parent.getItemAtPosition(position);

        if (memilih_item == "Custom") {
            final AlertDialog.Builder alertInput = new AlertDialog.Builder(DaftarPasienActivity.this);
            alertInput.setTitle("");

            alertInput.setCancelable(true);
            alertInput.setNegativeButton("Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    String input
                }
            });
            alertInput.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(DaftarPasienActivity.this, "Harap Pilih Terlebih Dahulu!", Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog alertDialog = alertInput.create();
            alertDialog.show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}