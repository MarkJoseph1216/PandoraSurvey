package com.example.privateex.pandorasurvey;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    ImageView imgLucerne;
    Button btnGetStarted, btnSettings;
    TextView txtSurvey, txtLoading;
    ProgressBar loadingBar;
    Dialog dialogSettings;
    Button btnCancel, btnEnter;
    Spinner chooseBranch;
    ArrayAdapter<String> adapterBranches;
    String IMEI_Number_Holder;
    TelephonyManager telephonyManager;
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

        // JSON Request
        loadBranches();
        getParseJSONIMEI();

        //Animations
        imgLucerne.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoomin));
        btnSettings.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.downtoup));

        //Getting IMEI Number
        telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        IMEI_Number_Holder = telephonyManager.getDeviceId();
        Log.d("IMEI", IMEI_Number_Holder);

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

    //Show Settings
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

    //loadingBranches
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


                        Toast.makeText(MainActivity.this, branches + branchCode, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    //Sending IMEI
    private void getParseJSONIMEI() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Survey.url_test_imei_registered, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject o = jsonArray.getJSONObject(i);
                        String message = o.getString("message");

                        if(message.equals("Request success")){
                        }
                        else {
                            Toast.makeText(MainActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("imei", IMEI_Number_Holder);
                return params;
            }
        };
        MySingleton.getInstance(MainActivity.this).addToRequestque(stringRequest);
        requestQueue.add(stringRequest);
    }
}
