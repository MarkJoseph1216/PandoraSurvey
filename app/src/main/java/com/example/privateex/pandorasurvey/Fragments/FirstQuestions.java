package com.example.privateex.pandorasurvey.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.privateex.pandorasurvey.MainActivity;
import com.example.privateex.pandorasurvey.MySingleton;
import com.example.privateex.pandorasurvey.R;
import com.example.privateex.pandorasurvey.SecondSurvey;
import com.example.privateex.pandorasurvey.Survey.Survey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FirstQuestions extends Fragment {

    Dialog dialogOthers;
    CheckBox chckOthers, chckFacebook, chckInstagram ,chckTwitter ,chckSnapchat;
    ImageButton btnExit;
    Button btnNext, btnSubmit;
    SecondSurvey secondSurvey;
    TextView txtPageNumber, txtTitle, txtQuestion2;
    LinearLayout layout, layout3, layout4;
    TextView txtOthers;
    TextInputEditText edtOthers;
    String Facebook = "";
    String Instagram = "";
    String Twitter = "";
    String Snapchat = "";
    private RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_firstquestion, container, false);

        chckOthers = (CheckBox) view.findViewById(R.id.chckOthers);
        chckFacebook = (CheckBox) view.findViewById(R.id.chckFacebook);
        chckInstagram = (CheckBox) view.findViewById(R.id.chckInstagram);
        chckTwitter = (CheckBox) view.findViewById(R.id.chckTwitter);
        chckSnapchat = (CheckBox) view.findViewById(R.id.chckSnapchat);

        requestQueue = Volley.newRequestQueue(getContext());

        btnNext = (Button) view.findViewById(R.id.btnNext);

        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtQuestion2 = (TextView) view.findViewById(R.id.txtQuestion2);
        txtQuestion2 = (TextView) view.findViewById(R.id.txtQuestion2);
        txtPageNumber = (TextView) view.findViewById(R.id.txtPageNumber);

        layout = (LinearLayout) view.findViewById(R.id.layout);
        layout3 = (LinearLayout) view.findViewById(R.id.layout3);
        layout4 = (LinearLayout) view.findViewById(R.id.layout4);

        txtTitle.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.INVISIBLE);
        txtPageNumber.setVisibility(View.INVISIBLE);
        txtQuestion2.setVisibility(View.INVISIBLE);
        layout3.setVisibility(View.INVISIBLE);
        layout4.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);

        txtPageNumber.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.splash));
        txtPageNumber.setVisibility(View.VISIBLE);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                txtTitle.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
                txtTitle.setVisibility(View.VISIBLE);
                    }
        }, 1000);
        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
                layout.setVisibility(View.VISIBLE);
            }
        }, 1500);
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                txtQuestion2.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
                txtQuestion2.setVisibility(View.VISIBLE);
            }
        }, 1700);
        final Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout3.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
                layout3.setVisibility(View.VISIBLE);
            }
        }, 1900);
        final Handler handler4 = new Handler();
        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout4.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
                layout4.setVisibility(View.VISIBLE);
            }
        }, 2100);
        final Handler handler5= new Handler();
        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {
                btnNext.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.downtoup));
                btnNext.setVisibility(View.VISIBLE);
            }
        }, 2300);

        secondSurvey = new SecondSurvey();
        dialogOthers = new Dialog(getContext());

        chckOthers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chckOthers.isChecked()){
                    showPopupMessage1();
                }
                else {

                }
            }
        });

        chckFacebook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chckFacebook.isChecked()){
                    Facebook = "true";
                }
                else {
                    Facebook = "false";
                }
            }
        });
        chckInstagram.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chckInstagram.isChecked()){
                    Instagram = "true";
                }
                else {
                    Instagram = "false";
                }
            }
        });
        chckTwitter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chckTwitter.isChecked()){
                    Twitter = "true";
                }
                else {
                    Twitter = "false";
                }
            }
        });
        chckSnapchat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chckSnapchat.isChecked()){
                    Snapchat = "true";
                }
                else {
                    Snapchat = "false";
                }
            }
        });
//            Survey.hashMap.put("Facebook", facebook);
//            Survey.hashMap.put("Instagram", Instagram);
//            Survey.hashMap.put("Twitter", Twitter);
//            Survey.hashMap.put("Snapchat", Snapchat);
        //}

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendingJSON();
                secondSurvey.viewPager.setCurrentItem(2);
            }
        });
        return view;
    }

    public void SendingJSON(){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Survey.url_survey, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject o = jsonArray.getJSONObject(i);

                            String message = o.getString("message");
                            if(message.equals("success")){
                                Toast.makeText(getContext(), "Registered Successful!", Toast.LENGTH_SHORT).show();
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
                    params.put("facebook", Facebook);

                    return params;
                }
            };
            MySingleton.getInstance(getContext()).addToRequestque(stringRequest);
            requestQueue.add(stringRequest);
    }

    public void showPopupMessage1() {
        dialogOthers.setContentView(R.layout.dialog_others);
        btnExit = (ImageButton) dialogOthers.findViewById(R.id.btnExit);
        btnSubmit = (Button) dialogOthers.findViewById(R.id.btnSubmitReason);
        edtOthers = (TextInputEditText) dialogOthers.findViewById(R.id.edtOthers);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edtothers = edtOthers.getText().toString();
                txtOthers.setText(edtothers);
                dialogOthers.dismiss();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOthers.dismiss();
                chckOthers.setChecked(false);

            }
        });
        dialogOthers.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogOthers.setCanceledOnTouchOutside(false);
        dialogOthers.show();
    }
}
