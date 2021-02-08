package ac.id.pradita.klinikhijauputih;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ac.id.pradita.klinikhijauputih.adapter.PasienAdapter;
import ac.id.pradita.klinikhijauputih.model.Pasien;

public class PasienActivity extends AppCompatActivity {

    RecyclerView rv_pasien;
    List<Pasien> pasienList;
    PasienAdapter adapter;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasien);

        rv_pasien = findViewById(R.id.rvPasien);

        rv_pasien.setHasFixedSize(true);
        rv_pasien.setLayoutManager(new LinearLayoutManager(this));

        pasienList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Pasien");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){
                    Pasien data = ds.getValue(Pasien.class);
                    pasienList.add(data);
                }
                adapter = new PasienAdapter(pasienList);
                rv_pasien.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}