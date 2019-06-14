package com.example.privateex.pandorasurvey;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.PowerManager;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.privateex.pandorasurvey.JSON_Connection.JSONParser;
import com.example.privateex.pandorasurvey.Survey.*;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.privateex.pandorasurvey.Survey.Survey.url_create_product;


public class FirstSurvey extends AppCompatActivity {

    private ProgressDialog progressDL;
    private PowerManager.WakeLock wlStayAwake;
    private RequestQueue requestQueue;

    JSONParser jsonParser = new JSONParser();



    TextInputLayout inputFirst, inputLast, inputEmail, inputMobile, inputDate;
    TextInputEditText edtFirstName, edtLastName, edtEmail, edtMobile, edtBirthDate;
    ImageView imgName, imgEmail, imgMobile, imgDate;
    Calendar myCalendar;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_survey);

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wlStayAwake = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "wakelocktag");
        wlStayAwake.acquire();

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        inputFirst = (TextInputLayout) findViewById(R.id.inputFirst);
        inputLast = (TextInputLayout) findViewById(R.id.inputLast);
        inputEmail = (TextInputLayout) findViewById(R.id.inputEmail);
        inputMobile = (TextInputLayout) findViewById(R.id.inputMobile);
        inputDate = (TextInputLayout) findViewById(R.id.inputDate);

        edtFirstName = (TextInputEditText) findViewById(R.id.edtFirstName);
        edtLastName = (TextInputEditText) findViewById(R.id.edtLastName);
        edtEmail = (TextInputEditText) findViewById(R.id.edtEmail);
        edtMobile = (TextInputEditText) findViewById(R.id.edtMobile);
        edtBirthDate = (TextInputEditText) findViewById(R.id.edtBirthDate);

        imgName = (ImageView) findViewById(R.id.imgName);
        imgEmail = (ImageView) findViewById(R.id.imgEmail);
        imgMobile = (ImageView) findViewById(R.id.imgPhone);
        imgDate = (ImageView) findViewById(R.id.imgBirth);

        inputFirst.setVisibility(View.INVISIBLE);
        inputLast.setVisibility(View.INVISIBLE);
        inputEmail.setVisibility(View.INVISIBLE);
        inputMobile.setVisibility(View.INVISIBLE);
        inputDate.setVisibility(View.INVISIBLE);

        imgName.setVisibility(View.INVISIBLE);
        imgEmail.setVisibility(View.INVISIBLE);
        imgMobile.setVisibility(View.INVISIBLE);
        imgDate.setVisibility(View.INVISIBLE);

        btnSubmit.setVisibility(View.INVISIBLE);

        imgName.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.lefttoright));
        inputFirst.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.fade_in));
        inputFirst.setVisibility(View.VISIBLE);
        inputLast.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.fade_in));
        inputLast.setVisibility(View.VISIBLE);

        myCalendar = Calendar.getInstance();
        requestQueue = Volley.newRequestQueue(this);

//        checkNetworkStatus();

        new CheckInternet().execute();

         btnSubmit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 getParseJSONRegister();

             }
         });



        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEdtBirth();
            }
        };

        edtBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(FirstSurvey.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgEmail.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.lefttoright));
                imgEmail.setVisibility(View.VISIBLE);
                inputEmail.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.fade_in));
                inputEmail.setVisibility(View.VISIBLE);
            }
        }, 600);

        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgMobile.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.lefttoright));
                imgMobile.setVisibility(View.VISIBLE);
                inputMobile.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.fade_in));
                inputMobile.setVisibility(View.VISIBLE);
            }
        }, 1100);

        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgDate.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.lefttoright));
                imgDate.setVisibility(View.VISIBLE);
                inputDate.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.fade_in));
                inputDate.setVisibility(View.VISIBLE);
            }
        }, 1600);

        final Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                btnSubmit.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.downtoup));
                btnSubmit.setVisibility(View.VISIBLE);
            }
        }, 1800);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("MM / dd / yyyy ");
        String currentDate = mdformat.format(calendar.getTime());

        edtBirthDate.setText(currentDate);

    }

    private void updateEdtBirth() {
        String myFormat = "MM/ dd/ yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtBirthDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void checkNetworkStatus()
    {
        if (AppStatus.getInstance(this).isOnline()) {

            Toast.makeText(this,"You are online!!!!",Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(this,"You are not online!!!!",Toast.LENGTH_SHORT).show();

        }
    }

    private void getParseJSONRegister() {
        String URL_DATA_REGISTER = "http://ad9b44d6.ngrok.io/register.php/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Survey.url_create_product, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject o = jsonArray.getJSONObject(i);
                        String message = o.getString("message");

                        if(message.equals("success")){
                            Toast.makeText(FirstSurvey.this, "Register Successful!" , Toast.LENGTH_SHORT).show();
                        }
                        else if(message.equals("Username Exists!")){
                            Toast.makeText(FirstSurvey.this, "Username is Already Exists!" , Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(FirstSurvey.this, "" + message, Toast.LENGTH_SHORT).show();
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

                params.put("firstname", edtFirstName.getText().toString());
                params.put("lastname", edtLastName.getText().toString());
                params.put("email", edtEmail.getText().toString());
                params.put("mobile", edtMobile.getText().toString());

                return params;
            }
        };

        MySingleton.getInstance(FirstSurvey.this).addToRequestque(stringRequest);
        requestQueue.add(stringRequest);
    }

    private class CheckInternet extends AsyncTask<Void, Void, Boolean> {
        String errmsg = "";

        @Override
        protected void onPreExecute() {
            progressDL = ProgressDialog.show(FirstSurvey.this, "", "Checking internet connection.");
            wlStayAwake.acquire();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean result = false;

            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if(activeNetwork != null) {
                if(activeNetwork.isFailover()) errmsg = "Internet connection fail over.";
                result = activeNetwork.isAvailable() || activeNetwork.isConnectedOrConnecting();
            }
            else {
                errmsg = "No internet connection.";
            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean bResult) {
            progressDL.dismiss();
            if(!bResult) {
                Toast.makeText( FirstSurvey.this, errmsg, Toast.LENGTH_SHORT).show();
            }

        }


    }




}
