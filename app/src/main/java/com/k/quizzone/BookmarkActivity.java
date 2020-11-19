package com.k.quizzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    public static final String FILE_NAME="Quizzers";
    public static final String KEY_NAME="Sorular";
    private List<SoruModel> list;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Kaydedilenler");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferences=getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor= preferences.edit();
        gson= new Gson();
        getBookmarks();
        recyclerView=findViewById(R.id.recycleBook);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        BookmarksAdapter bookmarksAdapter = new BookmarksAdapter(list);
        recyclerView.setAdapter(bookmarksAdapter);

    }
    protected void onPause(){
        super.onPause();
        storeBookmarks();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void getBookmarks(){
        String json=preferences.getString(KEY_NAME,"");
        Type type=new TypeToken<List<SoruModel>>(){}.getType();
        list= gson.fromJson(json,type);
        if(list==null){
            list=new ArrayList<>();
        }
    }
    private  void storeBookmarks(){
        String json=gson.toJson(list);

        editor.putString(KEY_NAME,json);
        editor.commit();
    }
}
