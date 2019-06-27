package com.example.privateex.pandorasurvey;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.privateex.pandorasurvey.MainActivity;


public class SplashScreen extends AppCompatActivity {

    ImageView imgPandora;
    private int STORAGE_PERMISSION_CODE = 1;
    String[] PERMISSIONS = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);

        imgPandora = (ImageView) findViewById(R.id.imgPandora);

        if(ContextCompat.checkSelfPermission(SplashScreen.this,
                Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED){

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

        } else {
            requestPermissions();
        }
    }

    private void requestPermissions(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This permission is needed to Access System Settings")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(SplashScreen.this, PERMISSIONS, STORAGE_PERMISSION_CODE);
                        }
                    }). setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
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
            } else {
                Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}