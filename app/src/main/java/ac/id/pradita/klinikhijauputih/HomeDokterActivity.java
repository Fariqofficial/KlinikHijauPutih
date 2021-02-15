package ac.id.pradita.klinikhijauputih;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeDokterActivity extends AppCompatActivity {
    CardView menuRekamMedis;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dokter);

        getSupportActionBar().setTitle("Home");

        drawerLayout = findViewById(R.id.drawerLayout);

        menuRekamMedis = findViewById(R.id.menuRekamMedis);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        initNavDrawer();

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        menuRekamMedis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RekamMedisDokterActivity.class);
                startActivity(intent);
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
                        Intent intent = new Intent(getApplicationContext(), HomeDokterActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.rekamMedis:
                        Intent rekMedis = new Intent(getApplicationContext(), RekamMedisDokterActivity.class);
                        startActivity(rekMedis);
                        break;
                    case R.id.logOut:
                        FirebaseAuth.getInstance().signOut();
                        Intent logout = new Intent(HomeDokterActivity.this, LoginActivity.class);
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