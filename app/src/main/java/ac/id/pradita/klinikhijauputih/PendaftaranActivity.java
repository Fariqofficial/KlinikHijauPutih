package ac.id.pradita.klinikhijauputih;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class PendaftaranActivity extends AppCompatActivity {
    CardView daftarDokter, daftarPasien, daftarJadwal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendaftaran);

        getSupportActionBar().setTitle("Pendaftaran");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        daftarDokter = findViewById(R.id.pendaftaranDokter);
        daftarPasien = findViewById(R.id.pendaftaranPasien);
        daftarJadwal = findViewById(R.id.pendaftaranJadwal);

        daftarDokter.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), DaftarDokterActivity.class));
        });

        daftarPasien.setOnClickListener(v -> {
           startActivity(new Intent(getApplicationContext(), DaftarPasienActivity.class));
        });

        daftarJadwal.setOnClickListener(v -> {
         startActivity(new Intent(getApplicationContext(), DokterActivity.class));
        });
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