package ac.id.pradita.klinikhijauputih;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class BuatAkunStaffActivity extends AppCompatActivity {
    DatabaseReference reference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_akun_staff);

        auth = FirebaseAuth.getInstance();
        bikinAkunStaff();
    }

    private void bikinAkunStaff() {
        auth.createUserWithEmailAndPassword("arizal@staff.com", "klinikhp123")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser staff = auth.getCurrentUser();
                        assert staff != null;

                        String staffId = staff.getUid();
                        reference = FirebaseDatabase.getInstance().getReference("Staff").child(staffId);

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("id_staff", staffId);
                        hashMap.put("nama", "Ahmad Rizal");
                        hashMap.put("alamat", "Jl. Ketapang No.3");
                        hashMap.put("email", "arizal@staff.com");
                        hashMap.put("no_hp", "08128290708");
                        hashMap.put("username", "arizal");
                        hashMap.put("password", "klinikhp123");

                        reference.setValue(hashMap);
                    }

                });
    }
}