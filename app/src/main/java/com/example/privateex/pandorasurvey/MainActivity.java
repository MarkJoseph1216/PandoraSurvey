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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.privateex.pandorasurvey.Survey.Survey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class  MainActivity extends AppCompatActivity {

    ImageView imgLucerne;
    Button btnGetStarted, btnSettings;
    TextView txtSurvey, txtLoading;
    ProgressBar loadingBar;
    Dialog dialogSettings;
    Button btnCancel, btnEnter;
    Spinner chooseBranch;
    String[] branches;
    private RequestQueue requestQueue;

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
        requestQueue = Volley.newRequestQueue(this);

        loadBranches();

        imgLucerne.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoomin));
        //btnGetStarted.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.downtoup));
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
        chooseBranch = (Spinner) dialogSettings.findViewById(R.id.chooseBranches);




        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSettings.dismiss();
            }
        });

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSettings.dismiss();
                btnSettings.setVisibility(View.INVISIBLE);
                btnGetStarted.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.downtoup));
                btnGetStarted.setVisibility(View.VISIBLE);
            }
        });

        dialogSettings.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSettings.setCanceledOnTouchOutside(false);
        dialogSettings.show();
    }


    private void loadBranches(){
        String testSample = "http://192.168.100.102:8000/api/branches";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, testSample, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject o = jsonArray.getJSONObject(i);
                        String branches = o.getString("branch");
                        String branchCode = o.getString("branch_code");

                        Toast.makeText(MainActivity.this, branches + branchCode, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            Toast.makeText(MainActivity.this, "" + error, Toast.LENGTH_LONG).show();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);

    }
}
