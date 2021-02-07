package ac.id.pradita.klinikhijauputih;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class DetailPasienActivity extends AppCompatActivity {
    EditText no_ktp, nama_pasien, alamat_rumah, umur, no_telp, nama_ibu, nama_pasangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pasien);
    }
}