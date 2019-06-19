package com.example.privateex.pandorasurvey;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
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
import com.android.volley.RetryPolicy;
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
    String IMEI_Number_Holder;
    TelephonyManager telephonyManager;
    private RequestQueue requestQueue;
    ArrayList<String> arrayBranches;
    ArrayList<String> arrayBranchCode;
    HashMap<Integer,String> branchCodeArrayList = new HashMap<Integer, String>();
    String[] branchArray;
    String branch;

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
        arrayBranches = new ArrayList<>();
        arrayBranchCode = new ArrayList<>();

        // JSON Request
        getParseJSONIMEI();

        //Animations
        imgLucerne.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoomin));
        //btnSettings.setVisibility(View.INVISIBLE);

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

    //Sending IMEI and Getting Branches
    private void getParseJSONIMEI() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Survey.url_test_imei_registered, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject o = jsonArray.getJSONObject(i);

                        String message = o.getString("message");

                        Log.d("Message", message);

                        if(message.equals("success")){
                            String branchcode = o.getString("branch_code");
                            Survey.branchCode = branchcode;
                            Toast.makeText(MainActivity.this, Survey.branchCode, Toast.LENGTH_SHORT).show();
                          //  dialogCheckingIMEI.dismiss();
                            btnGetStarted.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.downtoup));
                            btnGetStarted.setVisibility(View.VISIBLE);
                            btnSettings.setVisibility(View.INVISIBLE);
                        }
                        else if(message.equals("not found")){
                            btnSettings.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.downtoup));
                            btnSettings.setVisibility(View.VISIBLE);
                            getJSONBranches();
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

    //GettingAllBranches
    public void getJSONBranches(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Survey.url_branches, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArrayResponse = new JSONArray(response);
                    branchArray = new String[jsonArrayResponse.length()];
                    for (int index = 0; index < jsonArrayResponse.length(); index++) {
                        JSONObject parentObject = jsonArrayResponse.getJSONObject(index);
                        String branch = parentObject.getString("branch");
                        JSONObject details = parentObject.getJSONObject("details");
                        String branch_code = details.getString("branch_code");

                        branchCodeArrayList.put(index, branch_code);

                        branchArray[index] = branch;

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
        });
        MySingleton.getInstance(MainActivity.this).addToRequestque(stringRequest);
        requestQueue.add(stringRequest);
    }

    //SendingIMEIandBranch
    public void JSONSendingIMEIandBranch(final String IMEI, final String branches){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Survey.imei_registered, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject o = jsonArray.getJSONObject(i);

                        String message = o.getString("message");
                        if(message.equals("success")){
                            Toast.makeText(MainActivity.this, "Registered Successful!", Toast.LENGTH_SHORT).show();
                        }
                        else {

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
                params.put("imei", IMEI);
                params.put("branch_code", Survey.branchCode);

                return params;
            }
        };
        MySingleton.getInstance(MainActivity.this).addToRequestque(stringRequest);
        requestQueue.add(stringRequest);
    }

    //Show Settings
    public void showPopupMessageSettings() {
        dialogSettings.setContentView(R.layout.dialog_settings);
        btnCancel = (Button) dialogSettings.findViewById(R.id.btnCancel);
        btnEnter = (Button) dialogSettings.findViewById(R.id.btnEnter);
        chooseBranch = (Spinner) dialogSettings.findViewById(R.id.chooseBranches);

        //Adapter Spinner
        ArrayAdapter<String> adapterBranches = new ArrayAdapter<String>(dialogSettings.getContext(),
                R.layout.spinner_layout, branchArray);
        adapterBranches.setDropDownViewResource(R.layout.spinner_layout);
        chooseBranch.setAdapter(adapterBranches);

        chooseBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                branch = chooseBranch.getSelectedItem().toString();
                Survey.branchCode = branchCodeArrayList.get(chooseBranch.getSelectedItemPosition());

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSettings.dismiss();
            }
        });
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONSendingIMEIandBranch(IMEI_Number_Holder, Survey.branchCode);

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
}
