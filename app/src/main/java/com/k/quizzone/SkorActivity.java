package com.k.quizzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class SkorActivity extends AppCompatActivity {

    private TextView skor, derece;
    private Button bitir;
    private String dogru, yanlis;
    private int doru=0,top=0;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skor);
        skor=findViewById(R.id.txt3);
        derece=findViewById(R.id.txt4);
        bitir=findViewById(R.id.bitir_button);
        doru=getIntent().getIntExtra("skor",0);
        top=getIntent().getIntExtra("sayi",0);
        dogru=String.valueOf(doru);
        yanlis=String.valueOf(top-doru);

        skor.setText("Doğru: " + dogru + " Yanlış: "+ yanlis);
        derece.setText(derecelendir(doru,top));

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3385965964855097/7450867657");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());



        bitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                Intent intent = new Intent(SkorActivity.this,KategoriActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private String derecelendir(int dogru,int toplam){
        int a = toplam/5;
        if(dogru==0){ return "Berbat";} else
        if(dogru<=a){return "Çok Kötü";} else
        if(dogru<=a*2){return "Kötü";} else
        if(dogru<=a*3){return "Orta";} else
        if(dogru<=a*4){return "İyi";} else
        if(dogru<=toplam){return "Mükemmel";} else
        {return "Sonuç Yok";}

    }

}

