package ac.id.pradita.klinikhijauputih;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.google.common.reflect.Reflection.initialize;

public class BuatAkunStaffActivity extends AppCompatActivity {

    EditText nama_staff, email_staff, alamat_staff, hp_staff, username_staff, password_staff;
    ProgressDialog progressDialog;
    Button btnBuatAkun;
    DatabaseReference reference;
    FirebaseAuth auth;
    String namaStaff, emailStaff, alamatStaff, hpStaff, usernameStaff, passwordStaff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_akun_staff);

        nama_staff = findViewById(R.id.nama_staff);
        email_staff = findViewById(R.id.email_staff);
        alamat_staff = findViewById(R.id.alamatStaff);
        hp_staff = findViewById(R.id.hp_staff);
        username_staff = findViewById(R.id.username_staff);
        password_staff = findViewById(R.id.password_staff);
        btnBuatAkun = findViewById(R.id.buatAkunStaff);

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        btnBuatAkun.setOnClickListener(v -> {
            buatAkun();
        });

    }

    private void buatAkun() {
        initialize();
        if (!validate()) {
            Toast.makeText(this, "Buat Akun Gagal!", Toast.LENGTH_SHORT).show();
        } else {
            bikinAkunStaff(namaStaff, emailStaff, alamatStaff, hpStaff, usernameStaff, passwordStaff);
        }
    }

    private void bikinAkunStaff(String namaStaff, String emailStaff, String alamatStaff, String hpStaff, String usernameStaff, String passwordStaff) {
        progressDialog.show();
        progressDialog.setMessage("Mohon Tunggu..");
        auth.createUserWithEmailAndPassword(emailStaff, passwordStaff)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        FirebaseUser staff = auth.getCurrentUser();
                        assert  staff != null;

                        String staffId = staff.getUid();
                        reference = FirebaseDatabase.getInstance().getReference("Staff").child(staffId);

                        HashMap<String,String> hashMap = new HashMap<>();
                        hashMap.put("id_staff", staffId);
                        hashMap.put("nama", namaStaff);
                        hashMap.put("alamat", alamatStaff);
                        hashMap.put("email", emailStaff);
                        hashMap.put("no_hp", hpStaff);
                        hashMap.put("username", usernameStaff);
                        hashMap.put("password", passwordStaff);

                        reference.setValue(hashMap).addOnCompleteListener(task1 -> {
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);
                                    progressDialog.dismiss();

                        });
                            } else {
                                Toast.makeText(getApplicationContext(), "Buat akun gagal!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                });
    }

    public void initialize() {
        namaStaff = nama_staff.getText().toString().trim();
        alamatStaff = alamat_staff.getText().toString().trim();
        emailStaff = email_staff.getText().toString().trim();
        hpStaff = hp_staff.getText().toString().trim();
        usernameStaff = username_staff.getText().toString().trim();
        passwordStaff = password_staff.getText().toString().trim();
    }

    public boolean validate(){
        boolean valid = true;
        if (namaStaff.isEmpty()) {
            nama_staff.setError("Harap Masukkan Nama Staff!");
            valid = false;
        }
        if (alamatStaff.isEmpty()) {
            alamat_staff.setError("Harap Masukkan Alamat Staff!");
            valid = false;
        }

        if (emailStaff.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailStaff).matches()) {
            email_staff.setError("Harap Masukkan Alamat Email Yang Valid!");
            valid = false;
        }
        if (hpStaff.isEmpty()) {
            hp_staff.setError("Harap Masukkan Nomor Handphone Staff!");
            valid = false;
        }
        if (usernameStaff.isEmpty()) {
            username_staff.setError("Harap Masukkan Username!");
            valid = false;
        }
        if (passwordStaff.isEmpty() || passwordStaff.length() < 8) {
            password_staff.setError("Harap Masukkan Password Anda Lebih Dari 8 Karakter");
         }
        return valid;
    }
}