package com.example.privateex.pandorasurvey;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.privateex.pandorasurvey.MainActivity;


public class SplashScreen extends AppCompatActivity {

    ImageView imgPandora;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        imgPandora = (ImageView) findViewById(R.id.imgPandora);

        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.splash);
        imgPandora.startAnimation(rotate);
        final Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(3000);
                } catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        }; timer.start();
    }
}
