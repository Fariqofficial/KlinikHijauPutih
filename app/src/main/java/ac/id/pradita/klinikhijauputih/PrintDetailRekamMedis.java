package ac.id.pradita.klinikhijauputih;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PrintDetailRekamMedis extends AppCompatActivity {
    TextView nama_pasien, idRekMedis, idPasien, idDokter, anastesa, diagnosa, terapi, resep, tglResep;
    String m_nama_pasien, m_idRekMedis, m_idPasien, m_idDokter, m_anastesa, m_diagnosa, m_terapi, m_resep, m_tglResep;
    Bitmap bitmap;
    LinearLayout llPrint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_detail_rekam_medis);

        getSupportActionBar().setTitle("Print Detail Rekam Medis");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nama_pasien = findViewById(R.id.nama_pasien);
        idRekMedis = findViewById(R.id.idRekMedis);
        idPasien = findViewById(R.id.idPasien);
        idDokter = findViewById(R.id.idDokter);
        anastesa = findViewById(R.id.anastesa);
        diagnosa = findViewById(R.id.diagnosa);
        terapi = findViewById(R.id.terapi);
        resep = findViewById(R.id.resep);
        tglResep = findViewById(R.id.tglResep);
        llPrint = findViewById(R.id.llPrint);

        m_nama_pasien = getIntent().getStringExtra("nama_pasien");
        m_idRekMedis = getIntent().getStringExtra("id_rekMedis");
        m_idPasien = getIntent().getStringExtra("id_pasien");
        m_idDokter = getIntent().getStringExtra("id_dokter");
        m_anastesa = getIntent().getStringExtra("anastesa");
        m_diagnosa = getIntent().getStringExtra("diagnosa");
        m_terapi = getIntent().getStringExtra("terapi");
        m_resep = getIntent().getStringExtra("resep");
        m_tglResep = getIntent().getStringExtra("tanggal");

        nama_pasien.setText(m_nama_pasien);
        idRekMedis.setText(m_idRekMedis);
        idPasien.setText(m_idPasien);
        idDokter.setText(m_idDokter);
        anastesa.setText(m_anastesa);
        diagnosa.setText(m_diagnosa);
        terapi.setText(m_terapi);
        resep.setText(m_resep);
        tglResep.setText(m_tglResep);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                print();
            }
        }, 500);
    }

    private void print() {
        bitmap = loadBitmapFromView(llPrint, llPrint.getWidth(), llPrint.getHeight());
        createPdf();
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    private void createPdf() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels;
        float width = displaymetrics.widthPixels;

        int convertHighet = (int) hight, convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        File mypath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "detail_rekam_medis.pdf");
        try {
            document.writeTo(new FileOutputStream(mypath));
            document.close();
            Toast.makeText(this, "Berkas PDF berhasil dibuat", Toast.LENGTH_LONG).show();
            super.onBackPressed();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

    }
}