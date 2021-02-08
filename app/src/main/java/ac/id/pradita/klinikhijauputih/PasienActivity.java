package ac.id.pradita.klinikhijauputih;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import ac.id.pradita.klinikhijauputih.model.Pasien;

public class PasienActivity extends AppCompatActivity {

    RecyclerView rv_pasien;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasien);

        rv_pasien = findViewById(R.id.rvPasien);

        rv_pasien.setLayoutManager(new LinearLayoutManager(this));

        reference = FirebaseDatabase.getInstance().getReference();

        getListData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getListData();
    }

    private void getListData() {
        Query mQuery = reference.child("Pasien");
        mQuery.keepSynced(true);

        FirebaseRecyclerAdapter<Pasien, ListPasienViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Pasien, ListPasienViewHolder>(
                Pasien.class, R.layout.item_pasien, ListPasienViewHolder.class, mQuery) {
            @Override
            protected void populateViewHolder(ListPasienViewHolder listPasienViewHolder, Pasien pasien, int i) {
                listPasienViewHolder.setNamaPasien(pasien.getNama());

                listPasienViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // tulis intent disini bwang

                    }
                });
            }
        };
        rv_pasien.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ListPasienViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView tvNamaPasien;

        public ListPasienViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        void setNamaPasien(String nama_pasien) {
            tvNamaPasien = mView.findViewById(R.id.nama_pasien);
            tvNamaPasien.setText(nama_pasien);
        }
    }
}