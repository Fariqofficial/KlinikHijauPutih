package ac.id.pradita.klinikhijauputih;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginStaffActivity extends AppCompatActivity {

    String email, password;
    EditText emailStaff, passwordStaff;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    Button loginStaff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_staff);

        emailStaff = findViewById(R.id.emailStaff);
        passwordStaff = findViewById(R.id.passwordStaff);
        loginStaff = findViewById(R.id.loginStaff);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        loginStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialize();

                if (!form_validation()) {
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    processLogin(email, password);
                }
            }
        });
    }

    private void initialize() {
        email = emailStaff.getText().toString().trim();
        password = passwordStaff.getText().toString().trim();
    }

    private boolean form_validation() {
        boolean is_valid = true;

        if (email.isEmpty()) {
            emailStaff.setError("Ups, tidak boleh kosong");
            is_valid = false;
        }
        if (password.isEmpty()) {
            passwordStaff.setError("Ups, tidak boleh kosong");
            is_valid = false;
        }
        return is_valid;
    }

    private void processLogin(final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Ups, data tidak ditemukan. Periksa kembali email dan password Anda!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}