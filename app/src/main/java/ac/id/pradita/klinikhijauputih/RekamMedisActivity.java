package ac.id.pradita.klinikhijauputih;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ac.id.pradita.klinikhijauputih.model.Dokter;
import ac.id.pradita.klinikhijauputih.model.Pasien;
import ac.id.pradita.klinikhijauputih.model.RekamMedis;

public class RekamMedisActivity extends AppCompatActivity {
    RecyclerView rvRekamMedis;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    EditText dari, sampai;
    Button btn_filter;
    Calendar calendar = Calendar.getInstance();
    Locale id = new Locale("in", "ID");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", id);
    Date date_min, date_max;
    Context context;
    FloatingActionButton fabPrint;
    Query mQuery;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekam_medis);

        getSupportActionBar().setTitle("Rekam Medis Staff");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sampai = findViewById(R.id.tglSampai);
        dari = findViewById(R.id.tglDari);
        btn_filter = findViewById(R.id.filter);
        fabPrint = findViewById(R.id.fabPrint);

        context = this;

        rvRekamMedis = findViewById(R.id.rvRekamMedis);
        rvRekamMedis.setLayoutManager(new LinearLayoutManager(this));
        reference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        dari.setOnFocusChangeListener((v, hasFocus) -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(year, month, dayOfMonth);
                    dari.setText(simpleDateFormat.format(calendar.getTime()));
                    date_min = calendar.getTime();
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        sampai.setOnFocusChangeListener((v, hasFocus) -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(year, month, dayOfMonth);
                    sampai.setText(simpleDateFormat.format(calendar.getTime()));
                    date_max = calendar.getTime();
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        btn_filter.setOnClickListener(v -> {
            getData();
        });

        fabPrint.setOnClickListener(v -> {
            print();
        });
    }

    private void print() {
        bitmap = loadBitmapFromView(rvRekamMedis, rvRekamMedis.getWidth(), rvRekamMedis.getHeight());
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

        File mypath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "filename.pdf");
        try {
            document.writeTo(new FileOutputStream(mypath));
            document.close();
            Toast.makeText(this, "Berkas PDF berhasil dibuat", Toast.LENGTH_LONG).show();
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
        String tgl1 = dari.getText().toString().trim();
        String tgl2 = sampai.getText().toString().trim();

        if (!tgl1.isEmpty() && !tgl2.isEmpty()) {
            mQuery = reference.child("Rekam Medis").orderByChild("tanggal").startAt(tgl1).endAt(tgl2);
        } else {
            mQuery = reference.child("Rekam Medis");
        }

        mQuery.keepSynced(true);

        FirebaseRecyclerAdapter<RekamMedis, ListRekamMedisViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RekamMedis, ListRekamMedisViewHolder>
                (RekamMedis.class, R.layout.item_rekam_medis, ListRekamMedisViewHolder.class, mQuery) {
            @Override
            protected void populateViewHolder(ListRekamMedisViewHolder listRekamMedisViewHolder, RekamMedis rekamMedis, int i) {
                FirebaseDatabase.getInstance().getReference("Pasien").child(rekamMedis.getId_pasien()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Pasien pasien = snapshot.getValue(Pasien.class);
                            listRekamMedisViewHolder.setNamaPasien(pasien.getNama());
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
                            listRekamMedisViewHolder.setNamaDokter(dokter.getNama());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                listRekamMedisViewHolder.setTglDibuat(rekamMedis.getTanggal());

                listRekamMedisViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), DetailRekamMedis.class);
                        intent.putExtra("id_rekMedis", rekamMedis.getId_rekMedis());
                        startActivity(intent);
                    }
                });
            }
        };

        rvRekamMedis.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ListRekamMedisViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView namaPasien, namaDokter, tglDibuat;

        public ListRekamMedisViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        void setNamaPasien(String nama_pasien) {
            namaPasien = mView.findViewById(R.id.namaPasien);
            namaPasien.setText(nama_pasien);
        }

        void setNamaDokter(String nama_dokter) {
            namaDokter = mView.findViewById(R.id.namaDokter);
            namaDokter.setText(nama_dokter);
        }

        void setTglDibuat(String tanggal_dibuat) {
            tglDibuat = mView.findViewById(R.id.tglDibuat);
            tglDibuat.setText(tanggal_dibuat);
        }
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