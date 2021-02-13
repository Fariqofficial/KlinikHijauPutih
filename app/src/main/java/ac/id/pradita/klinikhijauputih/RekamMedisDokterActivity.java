package ac.id.pradita.klinikhijauputih;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RekamMedisDokterActivity extends AppCompatActivity {
    FloatingActionButton fabListPasien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekam_medis_dokter);

        fabListPasien = findViewById(R.id.fabListPasien);

        fabListPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PasienActivity.class);
                intent.putExtra("page_request", "RM_Dokter");
                startActivity(intent);
            }
        });
    }
}