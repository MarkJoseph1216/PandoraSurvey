package com.example.privateex.pandorasurvey.Fragments;

import android.app.Dialog;
import android.content.Context;
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
    CheckBox chckOthers, chckFacebook, chckInstagram ,chckTwitter ,chckSnapchat, chckBracelet
            ,chckCharm, chckNecklace, chckRing, chckEarrings;
    ImageButton btnExit;
    Button btnNext, btnSubmit;
    SecondSurvey secondSurvey;
    TextView txtPageNumber, txtTitle, txtQuestion2;
    LinearLayout layout, layout3, layout4;
    TextView txtOthers;
    TextInputEditText edtOthers;
    private RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_firstquestion, container, false);

        chckOthers = (CheckBox) view.findViewById(R.id.chckOthers);
        chckFacebook = (CheckBox) view.findViewById(R.id.chckFacebook);
        chckInstagram = (CheckBox) view.findViewById(R.id.chckInstagram);
        chckTwitter = (CheckBox) view.findViewById(R.id.chckTwitter);
        chckSnapchat = (CheckBox) view.findViewById(R.id.chckSnapchat);
        chckCharm = (CheckBox) view.findViewById(R.id.chckCharm);
        chckNecklace = (CheckBox) view.findViewById(R.id.chckNecklace);
        chckRing = (CheckBox) view.findViewById(R.id.chckRing);
        chckEarrings = (CheckBox) view.findViewById(R.id.chckEarrings);
        chckBracelet = (CheckBox) view.findViewById(R.id.chckBracelet);

        requestQueue = Volley.newRequestQueue(getContext());

        btnNext = (Button) view.findViewById(R.id.btnNext);

        txtOthers = (TextView) view.findViewById(R.id.txtOthers);
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
                    Survey.Facebook = "true";
                }
                else {
                    Survey.Facebook = "false";
                }
            }
        });

        chckInstagram.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chckInstagram.isChecked()){
                    Survey.Instagram = "true";
                }
                else {
                    Survey.Instagram = "false";
                }
            }
        });

        chckTwitter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chckTwitter.isChecked()){
                    Survey.Twitter = "true";
                }
                else {
                    Survey.Twitter = "false";
                }
            }
        });

        chckSnapchat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chckSnapchat.isChecked()){
                    Survey.Snapchat = "true";
                }
                else {
                    Survey.Snapchat = "false";
                }
            }
        });

        chckBracelet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chckBracelet.isChecked()){
                    Survey.Bracelet = "true";
                }
                else {
                    Survey.Bracelet = "false";
                }
            }
        });

        chckCharm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chckCharm.isChecked()){
                    Survey.Charm = "true";
                }
                else {
                    Survey.Charm = "false";
                }
            }
        });
        chckNecklace.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chckNecklace.isChecked()){
                    Survey.Necklace = "true";
                }
                else {
                    Survey.Necklace = "false";
                }
            }
        });
        chckRing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chckRing.isChecked()){
                    Survey.Ring = "true";
                }
                else {
                    Survey.Ring = "false";
                }
            }
        });
        chckEarrings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chckEarrings.isChecked()){
                    Survey.Earrings = "true";
                }
                else {
                    Survey.Earrings = "false";
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondSurvey.viewPager.setCurrentItem(2);
            }
        });
        return view;
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
                Survey.Others = edtothers;
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
        dialogOthers.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogOthers.setCanceledOnTouchOutside(false);
        dialogOthers.show();
    }
}
