package com.k.quizzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SoruActivity extends AppCompatActivity {
    public static final String FILE_NAME="Quizzers";
    public static final String KEY_NAME="Sorular";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private TextView question,sayac;
    private ImageButton bookmarkButton;
    private LinearLayout optionsCon;
    private Button shareBtn,nextBtn;

    private int count=0;
    private  List<SoruModel> soruModelList;
    private int position=0;
    private int score=0;
    private String kategoriAdi;
    private double setNo;

    private List<SoruModel> bookmarksList;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private int kayitSayi;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soru);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        question=findViewById(R.id.question_txtview);
        sayac=findViewById(R.id.sayac_txtview);
        bookmarkButton=findViewById(R.id.bookbtn);
        optionsCon=findViewById(R.id.optionsCon);
        shareBtn=findViewById(R.id.sharebtn);
        nextBtn=findViewById(R.id.nextbtn);

        soruModelList=new ArrayList<>();
        preferences=getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor= preferences.edit();
        gson= new Gson();

        kategoriAdi=getIntent().getStringExtra("kategori");
        setNo=getIntent().getIntExtra("sets",1);

        getBookmarks();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modeles()){
                    bookmarksList.remove(kayitSayi);
                    bookmarkButton.setImageDrawable(getDrawable(R.drawable.ic_action_name));

                }else{
                    bookmarksList.add(soruModelList.get(position));
                    bookmarkButton.setImageDrawable(getDrawable(R.drawable.bookmark_dolu));
                }
            }
        });


        myRef.child("Sets").child(kategoriAdi).child("Sorular").orderByChild("setNo").startAt(setNo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    soruModelList.add(dataSnapshot.getValue(SoruModel.class));
                }
                if(soruModelList.size()>0){

                    for(int i=0 ; i <4 ; i++){
                        optionsCon.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                checkAnswer((Button)view);
                            }
                        });
                    }
                    playAnim(question,0,soruModelList.get(position).getSoru());
                    nextBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            nextBtn.setEnabled(false);
                            nextBtn.setAlpha(0.7f);
                            enableOption(true);
                            position++;
                            if(position==soruModelList.size()){

                                Intent ıntent = new Intent(SoruActivity.this,SkorActivity.class);
                                ıntent.putExtra("skor",score);
                                ıntent.putExtra("sayi",soruModelList.size());
                                startActivity(ıntent);
                                finish();
                                return;
                            }
                            count=0;
                            playAnim(question,0,soruModelList.get(position).getSoru());
                        }
                    });
                    shareBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String body = soruModelList.get(position).getSoru()+"\n"+
                                    soruModelList.get(position).getSeca()+"\n"+
                                    soruModelList.get(position).getSecb()+"\n"+
                                    soruModelList.get(position).getSecc()+"\n"+
                                    soruModelList.get(position).getSecd();

                            Intent shareIntent= new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("plain/text");
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT,"QuizZone");
                            shareIntent.putExtra(Intent.EXTRA_TEXT,body);
                            startActivity(Intent.createChooser(shareIntent,"Paylaş"));
                        }
                    });
                }else {
                    finish();
                    Toast.makeText(SoruActivity.this,"Soru yok!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SoruActivity.this, error.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });



    }
    protected void onPause(){
        super.onPause();
        storeBookmarks();
    }
    private void playAnim(final View view, final int value, final String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    if(value==0&& count <4){
                        String option="";
                        if(count==0){
                            option=soruModelList.get(position).getSeca();
                        }else if(count==1){
                            option=soruModelList.get(position).getSecb();
                        }else if(count==2){
                            option=soruModelList.get(position).getSecc();
                        }else if(count==3){
                            option=soruModelList.get(position).getSecd();
                        }
                        playAnim(optionsCon.getChildAt(count),0,option);
                        count++;
                    }
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        //data change
                        if(value==0){
                            try {
                                ((TextView)view).setText(data);
                                sayac.setText(position+1+"/"+soruModelList.size());
                                if (modeles()){
                                    bookmarkButton.setImageDrawable(getDrawable(R.drawable.bookmark_dolu));
                                }else{
                                    bookmarkButton.setImageDrawable(getDrawable(R.drawable.ic_action_name));
                                }
                            }catch (ClassCastException e){
                                ((Button)view).setText(data);
                            }
                            view.setTag(data);
                            playAnim(view ,1,data);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }
    private void checkAnswer(Button selectOption){
        enableOption(false);
        nextBtn.setEnabled(true);
        nextBtn.setAlpha(1);
        if(selectOption.getText().toString().equals(soruModelList.get(position).getCevap())){
            //doğru
            score++;
            selectOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        }else{
            //yanlış
            selectOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Button correctOpt = (Button) optionsCon.findViewWithTag(soruModelList.get(position).getCevap());
            correctOpt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        }


    }
    private void enableOption(boolean enable){
        for (int i=0; i<4; i++){
            optionsCon.getChildAt(i).setEnabled(enable);
            if(enable){
                optionsCon.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EAEAEA")));
            }
        }
    }
    private void getBookmarks(){
        String json=preferences.getString(KEY_NAME,"");
        Type type=new TypeToken<List<SoruModel>>(){}.getType();
        bookmarksList= gson.fromJson(json,type);
        if(bookmarksList==null){
            bookmarksList=new ArrayList<>();
        }
    }
    private boolean modeles(){
        boolean matched = false;
        int i=0;
        for(SoruModel model: bookmarksList){

            if(model.getSoru().equals(soruModelList.get(position).getSoru())
                    && model.getCevap().equals(soruModelList.get(position).getCevap())
                    && model.getSetNo()==soruModelList.get(position).getSetNo()){
                matched=true;
                kayitSayi=i;
            }
            i++;
        }
        return matched;
    }
    private  void storeBookmarks(){
        String json=gson.toJson(bookmarksList);

        editor.putString(KEY_NAME,json);
        editor.commit();
    }
}
