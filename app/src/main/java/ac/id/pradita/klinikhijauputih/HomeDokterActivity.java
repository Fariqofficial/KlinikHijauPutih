package ac.id.pradita.klinikhijauputih;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HomeDokterActivity extends AppCompatActivity {
    CardView menuRekamMedis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dokter);

        menuRekamMedis = findViewById(R.id.menuRekamMedis);

        menuRekamMedis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RekamMedisDokterActivity.class);
                startActivity(intent);
            }
        });
    }
}