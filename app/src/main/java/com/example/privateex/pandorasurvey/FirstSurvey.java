package com.example.privateex.pandorasurvey;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.example.privateex.pandorasurvey.Fragments.FirstQuestions;
import com.example.privateex.pandorasurvey.Survey.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstSurvey extends AppCompatActivity {

    private ProgressDialog progressDL;
    private RequestQueue requestQueue;
    private StringRequest request;

    TextInputLayout inputFirst, inputLast, inputEmail, inputMobile, inputDate;
    TextInputEditText edtFirstName, edtLastName, edtEmail, edtMobile, edtBirthDate;
    ImageView imgName, imgEmail, imgMobile, imgDate, imgPandora;
    Calendar myCalendar;
    Button btnSubmit;
    CheckBox chckMs, chckMrs, chckMr;
    Dialog dialogMessage;

    String fName = "";
    String lName = "";
    String mobNo = "";
    String bday = "";
    String email = "";
    int mYear, mMonth, mDay;
    String currentDateandTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_first_survey);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        imgPandora = (ImageView) findViewById(R.id.imgLucerne1);

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

        chckMs = (CheckBox) findViewById(R.id.chckMs);
        chckMrs = (CheckBox) findViewById(R.id.chckMrs);
        chckMr = (CheckBox) findViewById(R.id.chckMr);

        inputFirst.setVisibility(View.INVISIBLE);
        inputLast.setVisibility(View.INVISIBLE);
        inputEmail.setVisibility(View.INVISIBLE);
        inputMobile.setVisibility(View.INVISIBLE);
        inputDate.setVisibility(View.INVISIBLE);

        chckMs.setVisibility(View.INVISIBLE);
        chckMrs.setVisibility(View.INVISIBLE);
        chckMr.setVisibility(View.INVISIBLE);

        imgName.setVisibility(View.INVISIBLE);
        imgEmail.setVisibility(View.INVISIBLE);
        imgMobile.setVisibility(View.INVISIBLE);
        imgDate.setVisibility(View.INVISIBLE);

        btnSubmit.setVisibility(View.INVISIBLE);

        myCalendar = Calendar.getInstance();
        requestQueue = Volley.newRequestQueue(this);
        dialogMessage = new Dialog(FirstSurvey.this);

        new CheckInternet().execute();

        currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        //Animations
        final Animation myAnim = AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.bounce);
        imgPandora.startAnimation(myAnim);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        imgPandora.startAnimation(myAnim);
        imgMobile.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.lefttoright));
        imgMobile.setVisibility(View.VISIBLE);
        inputMobile.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.fade_in));
        inputMobile.setVisibility(View.VISIBLE);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppStatus.getInstance(FirstSurvey.this).isOnline()) {
                    fName = edtFirstName.getText().toString();
                    lName = edtLastName.getText().toString();
                    email = edtEmail.getText().toString();
                    mobNo = edtMobile.getText().toString();

                    if (fName.equals("")) {
                        edtFirstName.setError("Required");
                        if (lName.equals("")) {
                            edtLastName.setError("Required");
                        } else if (email.equals("")) {
                            edtEmail.setError("Required");
                        } else if (mobNo.equals("")) {
                            edtMobile.setError("Required");
                        } else if (mobNo.length() < 13 && !mobNo.isEmpty()) {
                            edtMobile.setError("Invalid Mobile Number");
                        } else if (fName.equals("") && lName.equals("") && email.equals("")) {
                            Toast.makeText(FirstSurvey.this, "Field's are Empty!", Toast.LENGTH_SHORT).show();
                        }
                        Pattern pattern1 = Pattern.compile("^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+");
                        Matcher matcher1 = pattern1.matcher(email);
                         if (!matcher1.matches()) {
                            Toast.makeText(FirstSurvey.this, "Please input a Valid Email Address! ", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        showPopupMessage();
                    }
                } else {
                    Toast.makeText(FirstSurvey.this, "You are not online, Please Activate your wifi/data first.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        chckMs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Survey.title = "Ms.";
                    chckMrs.setChecked(false);
                    chckMr.setChecked(false);
                }
            }
        });
        chckMrs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Survey.title = "Mrs.";
                    chckMs.setChecked(false);
                    chckMr.setChecked(false);
                }
            }
        });
        chckMr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Survey.title = "Mr.";
                    chckMrs.setChecked(false);
                    chckMs.setChecked(false);
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
                new DatePickerDialog(FirstSurvey.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                final Calendar c = Calendar.getInstance();
//                mYear = c.get(Calendar.YEAR);
//                mMonth = c.get(Calendar.MONTH);
//                mDay = c.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(FirstSurvey.this,
//                        android.R.style.Theme_Holo_Dialog_MinWidth, new android.app.DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
//                                edtBirthDate.setText(String.format("yyyy-MM-dd", year, (monthOfYear + 1), dayOfMonth));
//                            }
//                        }, mYear, mMonth, mDay);
//                datePickerDialog.show();
            }
        });

        UsPhoneNumberFormatter addLineNumberFormatter = new UsPhoneNumberFormatter(
                new WeakReference<EditText>(edtMobile));
        edtMobile.addTextChangedListener(addLineNumberFormatter);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgName.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.lefttoright));
                inputFirst.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.fade_in));
                inputFirst.setVisibility(View.VISIBLE);
                inputLast.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.fade_in));
                inputLast.setVisibility(View.VISIBLE);
                chckMs.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.fade_in));
                chckMs.setVisibility(View.VISIBLE);
                chckMrs.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.fade_in));
                chckMrs.setVisibility(View.VISIBLE);
                chckMr.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.fade_in));
                chckMr.setVisibility(View.VISIBLE);
            }
        }, 600);

        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgEmail.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.lefttoright));
                imgEmail.setVisibility(View.VISIBLE);
                inputEmail.startAnimation(AnimationUtils.loadAnimation(FirstSurvey.this, R.anim.fade_in));
                inputEmail.setVisibility(View.VISIBLE);
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

//    Check Customer if exist or not
//    private void getParseJSONCheckCustomer() {
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                Survey.url_check_costumer,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONArray jsonArray = new JSONArray(response);
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject o = jsonArray.getJSONObject(i);
//
//                                String message = o.getString("message");
//
//                                if (message.equals("success")) {
//                                    if (chckMs.isChecked()) {
//                                        String checkMs = chckMs.getText().toString();
//                                        getParseJSONRegisterCustomer(fName, lName, email, mobNo, bday, checkMs);
//                                    }
//                                    if (chckMrs.isChecked()) {
//                                        String checkMrs = chckMrs.getText().toString();
//                                        getParseJSONRegisterCustomer(fName, lName, email, mobNo, bday, checkMrs);
//                                    }
//                                    if (chckMr.isChecked()) {
//                                        String checkMr = chckMr.getText().toString();
//                                        getParseJSONRegisterCustomer(fName, lName, email, mobNo, bday, checkMr);
//                                    }
//                                } else {
//                                    Toast.makeText(FirstSurvey.this, "" + message, Toast.LENGTH_SHORT).show();
//                                    final Intent intent = new Intent(FirstSurvey.this, EndScreen.class);
//                                    startActivity(intent);
//                                }
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        }) {
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//
//                params.put("title", "default");
//                params.put("firstname", fName);
//                params.put("lastname", lName);
//                params.put("email", email);
//                params.put("mobile", mobNo);
//                params.put("birthday", bday);
//
//                return params;
//            }
//        };
//        MySingleton.getInstance(FirstSurvey.this).addToRequestque(stringRequest);
//        requestQueue.add(stringRequest);
//    }

    private void getParseJSONRegister() {
        request = new StringRequest(Request.Method.POST, Survey.sample_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                             if(jsonObject.names().get(0).equals("success")){
                                    dialogMessage.dismiss();
                                    ClearEditText();
                                    final Intent intent = new Intent(FirstSurvey.this, SecondSurvey.class);
                                    startActivity(intent);
                                    finish();
                    }   else {
                        Toast.makeText(getApplicationContext(), "Error" +jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();

                hashMap.put("title", Survey.title);
                hashMap.put("firstname",edtFirstName.getText().toString());
                hashMap.put("lastname",edtLastName.getText().toString());
                hashMap.put("email",edtEmail.getText().toString());
                hashMap.put("mobile",edtMobile.getText().toString());
                hashMap.put("birthday",edtBirthDate.getText().toString());


                return hashMap;
            }
        };
        requestQueue.add(request);

    }

    //Register new customer
//    private void getParseJSONRegisterCustomer(final String name, final String lastname, final String emailuser, final String no, final String date, final String checkMs) {
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                Survey.url_creater_new_costumer,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONArray jsonArray = new JSONArray(response);
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject o = jsonArray.getJSONObject(i);
//                                String message = o.getString("message");
//
//                                if (message.equals("success")) {
//                                    dialogMessage.dismiss();
//                                    ClearEditText();
//                                    final Intent intent = new Intent(FirstSurvey.this, SecondSurvey.class);
//                                    startActivity(intent);
//                                    finish();
//                                } else {
//                                    Toast.makeText(FirstSurvey.this, "" + message, Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        }) {
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//
//                params.put("title", checkMs);
//                params.put("firstname", name);
//                params.put("lastname", lastname);
//                params.put("email", emailuser);
//                params.put("mobile", no);
//                params.put("birthday", date);
//                params.put("datecreated_at", currentDateandTime);
//                params.put("dateupdated_at", currentDateandTime);
//
//                return params;
//            }
//        };
//        MySingleton.getInstance(FirstSurvey.this).addToRequestque(stringRequest);
//        requestQueue.add(stringRequest);
//    }

    //Checking Internet Connection
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
            if (activeNetwork != null) {
                if (activeNetwork.isFailover()) errmsg = "Internet connection fail over.";
                result = activeNetwork.isAvailable() || activeNetwork.isConnectedOrConnecting();
            } else {
                errmsg = "No internet connection.";
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean bResult) {
            progressDL.dismiss();
            if (!bResult) {
                Toast.makeText(FirstSurvey.this, errmsg, Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Clear All
    private void ClearEditText() {
        edtFirstName.setText("");
        edtLastName.setText("");
        edtMobile.setText("");
        edtEmail.setText("");
        edtBirthDate.setText("");
        chckMs.setChecked(false);
        chckMrs.setChecked(false);
        chckMr.setChecked(false);
    }

    //BackPressed Disabled
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "Please Complete your process....", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    //Show popupMessage when Submit
    private void showPopupMessage() {
        dialogMessage.setContentView(R.layout.message_layout);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(800);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //getParseJSONCheckCustomer();
                    getParseJSONRegister();
                }
            }
        };
        timer.start();
        dialogMessage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogMessage.setCanceledOnTouchOutside(false);
        dialogMessage.show();
    }
}
