package com.example.privateex.pandorasurvey.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.privateex.pandorasurvey.EndScreen;
import com.example.privateex.pandorasurvey.FirstSurvey;
import com.example.privateex.pandorasurvey.MySingleton;
import com.example.privateex.pandorasurvey.R;
import com.example.privateex.pandorasurvey.SecondSurvey;
import com.example.privateex.pandorasurvey.Survey.Survey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SecondQuestions extends Fragment {

    Button btnFinish;
    CheckBox chckYes, chckNo, chckNewspaper, chckMagaxine, chckSocialMedia
            , chckBillboard, chckFriendsFamily, chckStoreVisit, chckReadAgreement;
    private RequestQueue requestQueue;
    Dialog dialog_agreement;
    Button btnAccept;
    ImageButton btnExit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_secondquestion, container, false);

//        String facebook = "";
//        String twitter = "";
//        String snapchat = "";
//        String instagram = "";
//
//        Set keys = Survey.hashMap.keySet();
//
//        Survey.hashMap.get("Facebook");
//        Survey.hashMap.get("Facebook");
//        Survey.hashMap.get("Facebook");
//        Survey.hashMap.get("Facebook");


        btnFinish = (Button) view.findViewById(R.id.btnFinish);
        chckYes = (CheckBox) view.findViewById(R.id.chckYes);
        chckNo = (CheckBox) view.findViewById(R.id.chckNo);
        chckNewspaper = (CheckBox) view.findViewById(R.id.chckNewsPaper);
        chckMagaxine = (CheckBox) view.findViewById(R.id.chckMagazine);
        chckSocialMedia = (CheckBox) view.findViewById(R.id.chckSocialMedia);
        chckBillboard = (CheckBox) view.findViewById(R.id.chckBillboard);
        chckFriendsFamily = (CheckBox) view.findViewById(R.id.chckFriendFamily);
        chckStoreVisit = (CheckBox) view.findViewById(R.id.chckStoreVisit);
        chckReadAgreement = (CheckBox) view.findViewById(R.id.chckReadAgreement);

        requestQueue = Volley.newRequestQueue(getContext());
        dialog_agreement = new Dialog(getContext());

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chckReadAgreement.isChecked()){
                    btnFinish.setEnabled(true);
                    Intent intent = new Intent(getContext(), EndScreen.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                else {
                    Toast.makeText(getContext(), "Please Read and Accept the terms and Agreement First!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        chckYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Survey.Gifts = "true";
                    chckNo.setChecked(false);
                }
            }
        });
        chckNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Survey.Gifts = "false";
                    chckYes.setChecked(false);
                }
            }
        });

        chckNewspaper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Survey.Newspaper = "true";
                }
                else {
                    Survey.Newspaper = "false";
                }
            }
        });

        chckMagaxine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Survey.Magazine = "true";
                } else {
                    Survey.Magazine = "false";
                }
            }
        });

        chckSocialMedia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Survey.SocialMedia = "true";
                } else {
                    Survey.SocialMedia = "false";
                }
            }
        });
        chckBillboard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Survey.Billboard = "true";
                } else {
                    Survey.Billboard = "false";
                }
            }
        });
        chckFriendsFamily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Survey.FriendFamily = "true";
                } else {
                    Survey.FriendFamily = "false";
                }
            }
        });
        chckStoreVisit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Survey.StoreVisit = "true";
                } else {
                    Survey.StoreVisit = "false";
                }
            }
        });

        chckReadAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chckReadAgreement.isChecked()){
                    showPopupPrivacyAgreement();
                }
            }
        });

        return view;
    }

    //Show Privacy Agreement
    public void showPopupPrivacyAgreement() {
        dialog_agreement.setContentView(R.layout.agreement_privacy);
        btnAccept = (Button) dialog_agreement.findViewById(R.id.btnAccept);
        btnExit = (ImageButton) dialog_agreement.findViewById(R.id.btnExit);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Accept Successfully!", Toast.LENGTH_SHORT).show();
                btnFinish.setEnabled(true);
                chckReadAgreement.setChecked(true);
                dialog_agreement.dismiss();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chckReadAgreement.setChecked(false);
                dialog_agreement.dismiss();
            }
        });
        dialog_agreement.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_agreement.setCanceledOnTouchOutside(false);
        dialog_agreement.show();
    }
}
