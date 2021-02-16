package ac.id.pradita.klinikhijauputih;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ac.id.pradita.klinikhijauputih.model.Staff;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    TextView namaStaff, email;
    DatabaseReference reference;
    ProgressDialog dialog;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initNavDrawer();

        getSupportActionBar().setTitle("Home");

        namaStaff = findViewById(R.id.Staff);
        email = findViewById(R.id.email_staff);

        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        drawerLayout = findViewById(R.id.drawerLayout);

        dialog = new ProgressDialog(this);
        user = FirebaseAuth.getInstance().getCurrentUser();

        getNamaStaff();

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void getNamaStaff() {
        dialog.show();
        dialog.setMessage("Mohon Tunggu..");
        FirebaseDatabase.getInstance().getReference("Staff").child(user.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Staff staff = snapshot.getValue(Staff.class);

                        namaStaff.setText(staff.getNama());

                        dialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void initNavDrawer() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                switch (id) {
                    case R.id.home:
                        Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.pendaftaran:
                        Intent pendaftaran = new Intent(HomeActivity.this, PendaftaranActivity.class);
                        startActivity(pendaftaran);
                        break;
                    case R.id.pasien:
                        Intent pasien = new Intent(HomeActivity.this, PasienActivity.class);
                        startActivity(pasien);
                        break;
                    case R.id.dokter:
                        Intent dokter = new Intent(HomeActivity.this, DokterActivity.class);
                        startActivity(dokter);
                        break;
                    case R.id.rekamMedis:
                        Intent rekMedis = new Intent(HomeActivity.this, RekamMedisActivity.class);
                        startActivity(rekMedis);
                        break;
                    case R.id.logOut:
                        FirebaseAuth.getInstance().signOut();
                        Intent logout = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(logout);
                        finish();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}