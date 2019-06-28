package com.example.privateex.pandorasurvey;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Welcome extends AppCompatActivity {

    TextView txtWelcomeName;
    ImageView imgPandora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

        txtWelcomeName = (TextView) findViewById(R.id.txtWelcome);
        imgPandora = (ImageView) findViewById(R.id.imgPandora);

        txtWelcomeName.setVisibility(View.INVISIBLE);
        imgPandora.startAnimation(AnimationUtils.loadAnimation(Welcome.this, R.anim.bounce));
        txtWelcomeName.startAnimation(AnimationUtils.loadAnimation(Welcome.this, R.anim.rotate));
        txtWelcomeName.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        String title = intent.getStringExtra("Title");

        txtWelcomeName.setText("Thank you " + title + name + " " +"for your participation! Hope to see you soon!");

        Toast.makeText(this, "Thank you " + title + name + " " +"for your participation! Hope to see you soon!", Toast.LENGTH_LONG).show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Welcome.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3400);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
