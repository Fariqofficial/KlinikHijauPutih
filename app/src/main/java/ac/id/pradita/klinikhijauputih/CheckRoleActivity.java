package ac.id.pradita.klinikhijauputih;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckRoleActivity extends AppCompatActivity {
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_role);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        getUserLoginData();
    }

    private void getUserLoginData() {
        String emailLoggedIn = mUser.getEmail();

        String emailRole = emailLoggedIn.substring(emailLoggedIn.indexOf("@") + 1, emailLoggedIn.length());
        Log.d("tess", emailRole);

        if (emailRole.equals("staff.com")) {
            Intent waliKelas = new Intent(getApplicationContext(), HomeActivity.class);
            waliKelas.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(waliKelas);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "aaaa", Toast.LENGTH_SHORT).show();
        }
    }
}