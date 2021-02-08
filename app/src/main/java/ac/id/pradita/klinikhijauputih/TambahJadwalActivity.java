package ac.id.pradita.klinikhijauputih;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class TambahJadwalActivity extends AppCompatActivity {
    EditText nama, hari, keterangan, namaStaff;
    DatabaseReference reference;
    ProgressDialog dialog;
    Button btn_tambah;
    FirebaseUser user;
    String nama_dokter, hari_praktek, keterangan_dokter, nama_staff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jadwal);
        
        nama = findViewById(R.id.nama_dokter);
        hari = findViewById(R.id.hariPraktek);
        keterangan = findViewById(R.id.keterangan);
        namaStaff = findViewById(R.id.nama_staff);
        btn_tambah = findViewById(R.id.tambahJadwal);

        dialog = new ProgressDialog(this);

        user = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference();
        
        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahJadwal();
            }
        });
        
    }

    private void tambahJadwal() {
        initialize();
        if (!validate()) {
            Toast.makeText(this, "Gagal Menambahkan Jadwal!", Toast.LENGTH_LONG).show();
        } else {
            tambahJadwalBerhasil(nama_dokter, hari_praktek, keterangan_dokter, nama_staff);
        }
    }

    private boolean validate() {
        boolean valid = true;
        if (nama_dokter.isEmpty()) {
            nama.setError("Harap Masukkan Nama Dokter!");
            valid = false;
        }
        if (hari_praktek.isEmpty()) {
            hari.setError("Harap Masukkan Hari Praktek Dokter!");
            valid = false;
        }
        if (keterangan_dokter.isEmpty()) {
            keterangan.setError("Harap Masukkan Keterangan Dokter!");
            valid = false;
        }

        if (nama_staff.isEmpty()) {
            namaStaff.setError("Harap Masukkan Nama Staff!");
         }
        return valid;
    }

    public void initialize(){
        nama_dokter = nama.getText().toString().trim();
        hari_praktek = hari.getText().toString().trim();
        keterangan_dokter = keterangan.getText().toString().trim();
        nama_staff = namaStaff.getText().toString().trim();
    }

    private void tambahJadwalBerhasil(String nama_dokter, String hari_praktek, String keterangan_dokter, String nama_staff) {
        dialog.show();
        dialog.setMessage("Mohon Tunggu..");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Jadwal");

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id_jadwal", reference.push().getKey());
        hashMap.put("nama_dokter", nama_dokter);
        hashMap.put("hari_praktek", hari_praktek);
        hashMap.put("keterangan_dokter", keterangan_dokter);
        hashMap.put("nama_staff", nama_staff);

        reference.push().setValue(hashMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getApplicationContext(), "Berhasil Menambahkan Jadwal!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

    }
}