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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import ac.id.pradita.klinikhijauputih.model.Dokter;

import static com.google.common.reflect.Reflection.initialize;

public class EditDokterActivity extends AppCompatActivity {
    EditText etKTPDokter, etNamaDokter, etAlamatDokter, etPoliDokter, etTelponDokter, etEmailDokter, etPassword;
    ProgressDialog dialog;
    Button btnEdit;
    String id_dokter, ktp_dokter, nama_dokter, alamat_dokter, poli_dokter, telp_dokter, email_dokter, pass_dokter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dokter);

        etKTPDokter = findViewById(R.id.edtKTPDokter);
        etNamaDokter = findViewById(R.id.edt_namaDokter);
        etAlamatDokter = findViewById(R.id.edtAlamatDokter);
        etPoliDokter = findViewById(R.id.edtPoliDokter);
        etTelponDokter = findViewById(R.id.edtTelpDokter);
        etEmailDokter = findViewById(R.id.edt_emailDokter);
        etPassword = findViewById(R.id.edtPassDokter);

        id_dokter = getIntent().getStringExtra("id_dokter");

        dialog = new ProgressDialog(this);

        getDataDokter(id_dokter);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialize();
                editDataDokter(id_dokter, ktp_dokter, nama_dokter, alamat_dokter, poli_dokter, telp_dokter, email_dokter, pass_dokter);
            }
        });
    }

    private void editDataDokter(String id_dokter, String ktp_dokter, String nama_dokter, String alamat_dokter, String poli_dokter, String telp_dokter, String email_dokter, String pass_dokter) {
        dialog.show();
        dialog.setMessage("Updating..");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Dokter").child(id_dokter);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ktp", ktp_dokter);
        hashMap.put("nama", nama_dokter);
        hashMap.put("alamat", alamat_dokter);
        hashMap.put("poli", poli_dokter);
        hashMap.put("telpon", telp_dokter);
        hashMap.put("email", email_dokter);
        hashMap.put("password", pass_dokter);

        reference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Data Berhasil Diubah!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
            }
        });
    }

    public void initialize() {
        ktp_dokter = etKTPDokter.getText().toString().trim();
        nama_dokter = etNamaDokter.getText().toString().trim();
        alamat_dokter = etAlamatDokter.getText().toString().trim();
        poli_dokter = etPoliDokter.getText().toString().trim();
        telp_dokter = etTelponDokter.getText().toString().trim();
        email_dokter = etEmailDokter.getText().toString().trim();
        pass_dokter = etPassword.getText().toString().trim();
    }

    private void getDataDokter(String id_dokter) {
        dialog.show();
        dialog.setMessage("Mohon Tunggu..");

        FirebaseDatabase.getInstance().getReference("Dokter").child(id_dokter)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Dokter dokter = snapshot.getValue(Dokter.class);

                        etKTPDokter.setText(dokter.getKtp());
                        etNamaDokter.setText(dokter.getNama());
                        etAlamatDokter.setText(dokter.getAlamat());
                        etPoliDokter.setText(dokter.getPoli());
                        etTelponDokter.setText(dokter.getTelpon());
                        etEmailDokter.setText(dokter.getEmail());
                        etPassword.setText(dokter.getPassword());

                        dialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Terjadi Kesalahan, Silahkan Coba Lagi", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
    }
}