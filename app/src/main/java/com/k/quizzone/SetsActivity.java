package com.k.quizzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;

import androidx.appcompat.widget.Toolbar;

public class SetsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("baslik"));
        gridView=findViewById(R.id.gridview);

        GridAdapter gridAdapter = new GridAdapter(getIntent().getIntExtra("sets",0),getIntent().getStringExtra("baslik"));
        gridView.setAdapter(gridAdapter);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
