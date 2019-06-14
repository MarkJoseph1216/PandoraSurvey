package com.example.privateex.pandorasurvey;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;



public class  MainActivity extends AppCompatActivity {

    ImageView imgLucerne;
    Button btnGetStarted, btnSettings;
    TextView txtSurvey, txtLoading;
    ProgressBar loadingBar;
    Dialog dialogSettings;
    Button btnCancel, btnEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetStarted = (Button) findViewById(R.id.btnGetStarted);
        btnSettings = (Button) findViewById(R.id.btnSettings);
        imgLucerne = (ImageView) findViewById(R.id.imgLucerne);
        txtSurvey = (TextView) findViewById(R.id.txtSurvey);
        txtLoading = (TextView) findViewById(R.id.txtLoading);
        loadingBar = (ProgressBar) findViewById(R.id.loadingBar);

        dialogSettings = new Dialog(MainActivity.this);

        imgLucerne.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoomin));
        btnGetStarted.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.downtoup));
        btnSettings.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.downtoup));

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                txtSurvey.setVisibility(View.VISIBLE);
                txtSurvey.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in));
            }
        }, 1000);

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGetStarted.setEnabled(false);
                txtSurvey.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out));
                txtSurvey.setVisibility(View.INVISIBLE);
                loadingBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in));
                txtLoading.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in));
                loadingBar.setVisibility(View.VISIBLE);
                txtLoading.setVisibility(View.VISIBLE);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, FirstSurvey.class);
                        startActivity(intent);
                        finish();
                    }
                }, 2500);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMessageSettings();
            }
        });
    }

    public void showPopupMessageSettings() {
        dialogSettings.setContentView(R.layout.dialog_settings);
        btnCancel = (Button) dialogSettings.findViewById(R.id.btnCancel);
        btnEnter = (Button) dialogSettings.findViewById(R.id.btnEnter);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSettings.dismiss();
            }
        });

        dialogSettings.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSettings.setCanceledOnTouchOutside(false);
        dialogSettings.show();
    }
}
