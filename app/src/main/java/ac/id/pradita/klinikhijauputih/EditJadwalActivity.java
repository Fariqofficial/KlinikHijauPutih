package ac.id.pradita.klinikhijauputih;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import ac.id.pradita.klinikhijauputih.model.Dokter;

public class EditJadwalActivity extends AppCompatActivity {

    EditText hari, keterangan;
    DatabaseReference reference;
    ProgressDialog dialog;
    Button btn_tambah;
    FirebaseUser user;
    String id_dokter, hari_praktek, ket_dokter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_jadwal);

        getSupportActionBar().setTitle("Daftar Jadwal Dokter");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        hari = findViewById(R.id.hariPraktek);
        keterangan = findViewById(R.id.keterangan);
        btn_tambah = findViewById(R.id.tambahJadwal);
        
        dialog = new ProgressDialog(this);
        id_dokter = getIntent().getStringExtra("id_dokter");

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialize();
                tambahDataJadwal(id_dokter, hari_praktek, ket_dokter);
            }
        });
    }

    private void tambahDataJadwal(String id_dokter, String hari_praktek, String ket_dokter) {
        dialog.show();
        dialog.setMessage("Mohon Tunggu..");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Dokter").child(id_dokter);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("hari", hari_praktek);
        hashMap.put("ket_dokter", ket_dokter);

        reference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Data Berhasil Ditambah!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
            }
        });
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
