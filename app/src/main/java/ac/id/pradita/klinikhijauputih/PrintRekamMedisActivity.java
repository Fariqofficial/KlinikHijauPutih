package ac.id.pradita.klinikhijauputih;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ac.id.pradita.klinikhijauputih.model.Dokter;
import ac.id.pradita.klinikhijauputih.model.Pasien;
import ac.id.pradita.klinikhijauputih.model.RekamMedis;

public class PrintRekamMedisActivity extends AppCompatActivity {
    RecyclerView rvPrintRekamMedis;
    DatabaseReference reference;
    Query mQuery;
    String tgl1, tgl2;
    Bitmap bitmap;
    RelativeLayout rlPrintRekamMedis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_rekam_medis);

        getSupportActionBar().setTitle("Print Rekam Medis");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rlPrintRekamMedis = findViewById(R.id.rlPrintRekamMedis);
        rvPrintRekamMedis = findViewById(R.id.rvPrint);
        rvPrintRekamMedis.setLayoutManager(new LinearLayoutManager(this));
        reference = FirebaseDatabase.getInstance().getReference();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                print();
            }
        }, 500);
    }

    private void print() {
        bitmap = loadBitmapFromView(rlPrintRekamMedis, rlPrintRekamMedis.getWidth(), rlPrintRekamMedis.getHeight());
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

        File mypath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "rekam_medis.pdf");
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

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    private void getData() {
        tgl1 = getIntent().getStringExtra("tgl1");
        tgl2 = getIntent().getStringExtra("tgl2");

        if (!tgl1.isEmpty() && !tgl2.isEmpty()) {
            mQuery = reference.child("Rekam Medis").orderByChild("tanggal").startAt(tgl1).endAt(tgl2);
        } else {
            mQuery = reference.child("Rekam Medis");
        }
        mQuery.keepSynced(true);

        FirebaseRecyclerAdapter<RekamMedis, ListPrintRekamMedisViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RekamMedis, ListPrintRekamMedisViewHolder>
                (RekamMedis.class, R.layout.item_print_rekam_medis, ListPrintRekamMedisViewHolder.class, mQuery) {

            @Override
            protected void populateViewHolder(ListPrintRekamMedisViewHolder listPrintRekamMedisViewHolder, RekamMedis rekamMedis, int i) {
                FirebaseDatabase.getInstance().getReference("Pasien").child(rekamMedis.getId_pasien()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Pasien pasien = snapshot.getValue(Pasien.class);
                            listPrintRekamMedisViewHolder.setNamaPasien(pasien.getNama());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                FirebaseDatabase.getInstance().getReference("Dokter").child(rekamMedis.getId_dokter()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Dokter dokter = snapshot.getValue(Dokter.class);
                            listPrintRekamMedisViewHolder.setNamaDokter(dokter.getNama());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                listPrintRekamMedisViewHolder.setTglDibuat(rekamMedis.getTanggal());
            }
        };
        rvPrintRekamMedis.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ListPrintRekamMedisViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView namaPasien, namaDokter, tglDibuat;

        public ListPrintRekamMedisViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        void setNamaPasien(String nama_pasien) {
            namaPasien = mView.findViewById(R.id.nama_pasien);
            namaPasien.setText(nama_pasien);
        }

        void setNamaDokter(String nama_dokter) {
            namaDokter = mView.findViewById(R.id.nama_dokter);
            namaDokter.setText(nama_dokter);
        }

        void setTglDibuat(String tanggal_dibuat) {
            tglDibuat = mView.findViewById(R.id.tglDibuat);
            tglDibuat.setText(tanggal_dibuat);
        }
    }
}