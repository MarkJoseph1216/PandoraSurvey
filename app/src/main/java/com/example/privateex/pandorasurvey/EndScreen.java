package com.example.privateex.pandorasurvey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class EndScreen extends AppCompatActivity {

    ImageView imgPandora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_end_screen);

        imgPandora = (ImageView) findViewById(R.id.imgPandora);
        imgPandora.startAnimation(AnimationUtils.loadAnimation(EndScreen.this, R.anim.random));

        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(2000);
                } catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(EndScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }; timer.start();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
