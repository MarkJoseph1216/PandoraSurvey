package com.example.privateex.pandorasurvey.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.privateex.pandorasurvey.R;

public class EditProfileActivity extends AppCompatActivity {

    Button btnConfirmInformation, btnEditInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_profile);

        btnConfirmInformation = (Button) findViewById(R.id.btnConfirm);
        btnEditInformation = (Button) findViewById(R.id.btnEditInformation);
        btnEditInformation.setVisibility(View.INVISIBLE);

        EditProfileActivity.this.overridePendingTransition(R.anim.downtoup, R.anim.fadeout_intent);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.uptodown);
    }
}
