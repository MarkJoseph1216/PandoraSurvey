package com.example.privateex.pandorasurvey.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.privateex.pandorasurvey.AppStatus;
import com.example.privateex.pandorasurvey.FirstSurvey;
import com.example.privateex.pandorasurvey.MainActivity;
import com.example.privateex.pandorasurvey.R;
import com.example.privateex.pandorasurvey.SecondSurvey;
import com.example.privateex.pandorasurvey.Survey.Survey;
import com.example.privateex.pandorasurvey.UsPhoneNumberFormatter;
import com.example.privateex.pandorasurvey.Welcome;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfileActivity extends AppCompatActivity {

    Button btnConfirmInformation, btnEditInformation;
    DatePicker datePicker;
    TextInputEditText edtFirstName, edtLastName, edtEmail, edtMobile, edtBirthDate;
    CountryCodePicker countryCodePicker;
    CheckBox chckMs, chckMrs, chckMr;

    Button btnCancelDatePicker, btnOkDatePicker;
    Dialog dialogDatePicker;
    String dateMonth, dateDay;

    String fName = "";
    String lName = "";
    String mobNo = "";
    String bday = "";
    String email = "";
    String id = "";
    private RequestQueue requestQueue;
    private StringRequest request;

    private ProgressDialog pDialog;
    Dialog dialogMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_profile);

        btnConfirmInformation = (Button) findViewById(R.id.btnConfirm);
        btnEditInformation = (Button) findViewById(R.id.btnEditInformation);

        countryCodePicker = (CountryCodePicker) findViewById(R.id.countryCodePicker);

        edtFirstName = (TextInputEditText) findViewById(R.id.edtFirstName);
        edtLastName = (TextInputEditText) findViewById(R.id.edtLastName);
        edtEmail = (TextInputEditText) findViewById(R.id.edtEmail);
        edtMobile = (TextInputEditText) findViewById(R.id.edtMobile);
        edtBirthDate = (TextInputEditText) findViewById(R.id.edtBirthDate);

        chckMs = (CheckBox) findViewById(R.id.chckMs);
        chckMrs = (CheckBox) findViewById(R.id.chckMrs);
        chckMr = (CheckBox) findViewById(R.id.chckMr);

        btnEditInformation.setVisibility(View.INVISIBLE);

        dialogDatePicker = new Dialog(EditProfileActivity.this);
        requestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(EditProfileActivity.this);
        dialogMessage = new Dialog(EditProfileActivity.this);

        EditProfileActivity.this.overridePendingTransition(R.anim.downtoup, R.anim.fadeout_intent);

        btnConfirmInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Survey.countryCode = countryCodePicker.getFullNumberWithPlus();
                String mobile = edtMobile.getText().toString();
                String firstname = edtFirstName.getText().toString();
                String lastName = edtLastName.getText().toString();

                if (AppStatus.getInstance(EditProfileActivity.this).isOnline()) {
                    if (mobile.equals("") || firstname.equals("") || lastName.equals("")) {
                        Toast.makeText(EditProfileActivity.this, "Field's Are Empty!", Toast.LENGTH_SHORT).show();
                    } else if (mobile.length() < 12) {
                        edtMobile.setError("Invalid Mobile Number");
                    } else {
                        getParseJSONCheckProfile();
                    }
                } else {
                    Toast.makeText(EditProfileActivity.this, "No Internet Connection, Please Enable your Data/Wifi!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEditInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(EditProfileActivity.this).isOnline()) {

                    Survey.countryCode = countryCodePicker.getFullNumberWithPlus();
                    fName = edtFirstName.getText().toString();
                    lName = edtLastName.getText().toString();
                    email = edtEmail.getText().toString();
                    mobNo = edtMobile.getText().toString();

                    Pattern pattern1 = Pattern.compile("^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+");
                    Matcher matcher1 = pattern1.matcher(email);

                    if (fName.equals("") && lName.equals("") && email.equals("")) {
                        Toast.makeText(EditProfileActivity.this, "Field's are Empty!", Toast.LENGTH_SHORT).show();
                    }
                    if (mobNo.equals("")) {
                        edtMobile.setError("Required");
                    }
                    else if (fName.equals("")) {
                        edtFirstName.setError("Required");
                    }
                    else if (lName.equals("")) {
                        edtLastName.setError("Required");
                    }
                    else if (email.equals("")) {
                        edtEmail.setError("Required");
                    }
                    else if (mobNo.length() < 12 && !mobNo.isEmpty()) {
                        edtMobile.setError("Invalid Mobile Number");
                    }
                    else if (!matcher1.matches()) {
                        Toast.makeText(EditProfileActivity.this, "Please input a Valid Email Address! ", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        new RegisterNewCustomer().execute();
                    }
                } else {
                    Toast.makeText(EditProfileActivity.this, "You are not online, Please Activate your wifi/data first.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edtBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopupDatePicker();
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

        UsPhoneNumberFormatter addLineNumberFormatter = new UsPhoneNumberFormatter(
                new WeakReference<EditText>(edtMobile));
        edtMobile.addTextChangedListener(addLineNumberFormatter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.uptodown);
    }

    //Show DatePicker
    private void showPopupDatePicker() {
        dialogDatePicker.setContentView(R.layout.showdatepicker);
        datePicker = (DatePicker) dialogDatePicker.findViewById(R.id.datePicker);
        btnCancelDatePicker = (Button) dialogDatePicker.findViewById(R.id.btnCancel);
        btnOkDatePicker = (Button) dialogDatePicker.findViewById(R.id.btnOK);

        datePicker.init(1980, 00, 01, null);

        btnOkDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateMonth = "";
                dateDay = "";
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();

                if(month < 10){
                    dateMonth = "0" + String.valueOf(month);
                }
                if(day < 10){
                    dateDay = "0" + String.valueOf(day);
                }
                edtBirthDate.setText(datePicker.getYear()+"/"+dateMonth+"/"+dateDay);
                dialogDatePicker.dismiss();
            }
        });

        btnCancelDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDatePicker.dismiss();
            }
        });

        dialogDatePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogDatePicker.show();
    }

    private void getParseJSONCheckProfile() {
        request = new StringRequest(Request.Method.POST, Survey.url_check_profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject o = jsonArray.getJSONObject(i);
                        String message = o.getString("message");

                        JSONObject details = o.getJSONObject("details");
                        String title = details.getString("title");
                        String email = details.getString("email");
                        String idCustomer = details.getString("id");

                        id = idCustomer;
                        Survey.title = title;

                        String birthday = details.getString("birthday");

                        if (message.equals("success")) {
                            Toast.makeText(EditProfileActivity.this, "Check Successful!", Toast.LENGTH_SHORT).show();
                            if (title.equals("Ms.")) {
                                chckMs.setChecked(true);
                            } else if (title.equals("Mrs.")) {
                                chckMrs.setChecked(true);
                            } else if (title.equals("Mr.")) {
                                chckMr.setChecked(true);
                            } else {
                                chckMs.setChecked(true);
                                chckMrs.setChecked(true);
                                chckMr.setChecked(true);
                            }

                            edtEmail.setText(email);
                            edtBirthDate.setText(birthday);

                            btnConfirmInformation.setVisibility(View.INVISIBLE);
                            btnEditInformation.startAnimation(AnimationUtils.loadAnimation(EditProfileActivity.this, R.anim.downtoup));
                            btnEditInformation.setVisibility(View.VISIBLE);

                        }
                        else if (message.equals("error")){
                            Toast.makeText(EditProfileActivity.this, "Your Account does not Exists!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditProfileActivity.this, "Server Error/Slow Connection, Please try again!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();

                hashMap.put("mobile", Survey.countryCode + " " + edtMobile.getText().toString());
                hashMap.put("firstname", edtFirstName.getText().toString());
                hashMap.put("lastname", edtLastName.getText().toString());

                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    private class RegisterNewCustomer extends AsyncTask<Void, Void, Boolean> {
        String errmsg = "";

        @Override
        protected void onPreExecute() {
            pDialog.setMessage("Connecting...Please wait!!");
            pDialog.setTitle("Message");
            pDialog.setCancelable(false);
            pDialog.setIndeterminate(true);
            pDialog.show();
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
                errmsg = "No internet connection!";
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean bResult) {
            pDialog.dismiss();
            if (!bResult) {
                Toast.makeText(EditProfileActivity.this, errmsg, Toast.LENGTH_SHORT).show();
            }
            else{
                getParseUpdateProfile();
            }
        }
    }

    private void getParseUpdateProfile() {
        request = new StringRequest(Request.Method.POST, Survey.url_update_profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String object = jsonObject.getString("message");
                    if(object.equals("success")){
                        showPopupMessage();

                        ClearEditText();
                    } else {
                        Toast.makeText(EditProfileActivity.this, "Message Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(EditProfileActivity.this, "Server Error/Slow Connection, Please try again!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();

                hashMap.put("id", id);
                hashMap.put("title", Survey.title);
                hashMap.put("firstname",edtFirstName.getText().toString());
                hashMap.put("lastname",edtLastName.getText().toString());
                hashMap.put("email",edtEmail.getText().toString());
                hashMap.put("mobile", Survey.countryCode + " " + edtMobile.getText().toString());
                hashMap.put("birthday",edtBirthDate.getText().toString());

                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    //Show popupMessage when Submit
    private void showPopupMessage() {
        dialogMessage.setContentView(R.layout.message_update);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(800);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    dialogMessage.dismiss();
                }
            }
        };
        timer.start();
        dialogMessage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogMessage.setCanceledOnTouchOutside(false);
        dialogMessage.show();
    }

    //Clear All the EditText Field's
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
}
