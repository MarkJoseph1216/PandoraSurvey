package com.example.privateex.pandorasurvey;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.privateex.pandorasurvey.Fragments.FirstQuestions;
import com.example.privateex.pandorasurvey.Survey.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FirstSurvey extends AppCompatActivity {

    private ProgressDialog progressDL;
    private RequestQueue requestQueue;

    TextInputLayout inputFirst, inputLast, inputEmail, inputMobile, inputDate;
    TextInputEditText edtFirstName, edtLastName, edtEmail, edtMobile, edtBirthDate;
    ImageView imgName, imgEmail, imgMobile, imgDate;
    Calendar myCalendar;
    Button btnSubmit;

    String fName = "";
    String lName = "";
    String mobNo = "";
    String bday = "";
    String email="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_survey);
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


        //checkNetworkStatus();

        imgName.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.lefttoright));
        inputFirst.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.fade_in));
        inputFirst.setVisibility(View.VISIBLE);
        inputLast.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.fade_in));
        inputLast.setVisibility(View.VISIBLE);
        myCalendar = Calendar.getInstance();
        requestQueue = Volley.newRequestQueue(this);

        new CheckInternet().execute();

         btnSubmit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (AppStatus.getInstance(FirstSurvey.this).isOnline())
                 {
                     fName = edtFirstName.getText().toString();
                     lName = edtLastName.getText().toString();
                     email = edtEmail.getText().toString();
                     mobNo = edtMobile.getText().toString();

                     if(fName.equals("")) {
                         edtFirstName.setError("Required");
                     }
                     if(lName.equals("")) {
                         edtLastName.setError("Required");
                     }
                     if(email.equals("")) {
                         edtEmail.setError("Required");
                     }
                     if(mobNo.length() < 11 && !mobNo.isEmpty()){
                         edtMobile.setError("Invalid Mobile Number");
                     }
                     else if(fName.equals("") && lName.equals("") && email.equals("")){
                         Toast.makeText(FirstSurvey.this, "Field's are Empty!", Toast.LENGTH_SHORT).show();
                     } else {
                         getParseJSONCheckCustomer();
                     }
                 } else {
                     Toast.makeText(FirstSurvey.this,"You are not online, Please Activate your wifi/data first.",Toast.LENGTH_SHORT).show();
                 }
             }
         });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
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
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = mdformat.format(calendar.getTime());
    }

    //Updating Birthday Every Click in EditText
    private void updateEdtBirth() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtBirthDate.setText(sdf.format(myCalendar.getTime()));

        bday = edtBirthDate.getText().toString();
    }

    private void checkNetworkStatus()
    {
        if (AppStatus.getInstance(this).isOnline())
        {
            Toast.makeText(this,"You are online!!!!",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"You are not online!!!!",Toast.LENGTH_SHORT).show();
        }
    }

    //Check Customer if exist or not
    private void getParseJSONCheckCustomer() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Survey.url_check_costumer,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject o = jsonArray.getJSONObject(i);

                //        Toast.makeText(FirstSurvey.this, ""+response, Toast.LENGTH_SHORT).show();

                        String message = o.getString("message");

                        if(message.equals("success")){
                            Toast.makeText(FirstSurvey.this, "User not exist!!!" , Toast.LENGTH_SHORT).show();
                            getParseJSONRegisterCustomer(fName,lName,email,mobNo,bday);

                        }
                        else {
                            Toast.makeText(FirstSurvey.this, "" + message, Toast.LENGTH_SHORT).show();
                            final Intent intent = new Intent(FirstSurvey.this, EndScreen.class);
                            startActivity(intent);



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

                params.put("code", "default");
                params.put("firstname", fName);
                params.put("lastname", lName);
                params.put("email", email);
                params.put("mobile", mobNo);
                params.put("birthday",bday);

                return params;
            }
        };
        MySingleton.getInstance(FirstSurvey.this).addToRequestque(stringRequest);
        requestQueue.add(stringRequest);
    }

    //Register new customer
    private void getParseJSONRegisterCustomer(final String name, final String lastname,final String emailuser , final String no, final String date) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Survey.url_creater_new_costumer,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject o = jsonArray.getJSONObject(i);

                                //        Toast.makeText(FirstSurvey.this, ""+response, Toast.LENGTH_SHORT).show();

                                String message = o.getString("message");

                                if(message.equals("success")){
                                    Toast.makeText(FirstSurvey.this, "Succesfully Saved!!" , Toast.LENGTH_SHORT).show();
                                    final Intent intent = new Intent(FirstSurvey.this, SecondSurvey.class);
                                    startActivity(intent);
                                    ClearEditText();
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

                params.put("code", "default");
                params.put("firstname", name);
                params.put("lastname", lastname);
                params.put("email", emailuser);
                params.put("mobile", no);
                params.put("birthday",date);

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
    private void ClearEditText()
    {
        edtFirstName.setText("");
        edtLastName.setText("");
        edtMobile.setText("");
        edtEmail.setText("");
        edtBirthDate.setText("");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            Toast.makeText(this, "Please Complete your process....", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
