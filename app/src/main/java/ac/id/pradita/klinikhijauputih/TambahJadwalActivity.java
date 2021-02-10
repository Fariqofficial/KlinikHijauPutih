package ac.id.pradita.klinikhijauputih;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.google.common.reflect.Reflection.initialize;

public class TambahJadwalActivity extends AppCompatActivity {

    EditText hari, keterangan;
    DatabaseReference reference;
    ProgressDialog dialog;
    Button btn_tambah;
    FirebaseUser user;
    String hari_praktek, ket_dokter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jadwal);

        hari = findViewById(R.id.hariPraktek);
        keterangan = findViewById(R.id.keterangan);
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
            tambahJadwalSukses(hari_praktek, ket_dokter);
        }
    }

    private boolean validate() {
        boolean valid = true;
        if (hari_praktek.isEmpty()) {
            hari.setError("Harap Masukkan Hari Praktek Dokter!");
            valid = false;
        }
        if (ket_dokter.isEmpty()) {
            keterangan.setError("Harap Masukkan Keterangan Dokter Apa!");
         }
        return valid;
    }

    public void initialize(){
        hari_praktek = hari.getText().toString().trim();
        ket_dokter = keterangan.getText().toString().trim();
    }

    private void tambahJadwalSukses(String hari_praktek, String ket_dokter) {
        dialog.show();
        dialog.setMessage("Mohon Tunggu..");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Dokter");

        DatabaseReference databaseReference = reference.push();

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("id_dokter", databaseReference.getKey());
        hashMap.put("hari", hari_praktek);
        hashMap.put("ket_dokter", ket_dokter);

        databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Berhasil Menambahkan Jadwal!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
            }
        });
    }
}