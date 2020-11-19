package com.k.quizzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class KategoriActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private RecyclerView recyclerview;
    private List<KategoriModel> list;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);
        toolbar=findViewById(R.id.toolbar);
        progressBar=findViewById(R.id.progressBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Kategoriler");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerview=findViewById(R.id.kategoriRV);

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);

        list = new ArrayList<>();
        final KategoriAdapter adapter= new KategoriAdapter(list);
        recyclerview.setAdapter(adapter);

        myRef.child("Kategoriler").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    list.add(dataSnapshot.getValue(KategoriModel.class));
                }
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(KategoriActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
