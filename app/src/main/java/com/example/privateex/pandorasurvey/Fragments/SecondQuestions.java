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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.privateex.pandorasurvey.Adapter.AdsRecycleViewAdapter;
import com.example.privateex.pandorasurvey.Adapter.CategoriesRecyclerViewAdapter;
import com.example.privateex.pandorasurvey.Adapter.ProductsRecycleViewAdapter;
import com.example.privateex.pandorasurvey.Class.AdsClass;
import com.example.privateex.pandorasurvey.Class.CategoriesClass;
import com.example.privateex.pandorasurvey.Class.ProductsClass;
import com.example.privateex.pandorasurvey.EndScreen;
import com.example.privateex.pandorasurvey.FirstSurvey;
import com.example.privateex.pandorasurvey.MySingleton;
import com.example.privateex.pandorasurvey.R;
import com.example.privateex.pandorasurvey.RecycleViewDecoration.RecyclerViewMargin;
import com.example.privateex.pandorasurvey.SecondSurvey;
import com.example.privateex.pandorasurvey.Survey.Survey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SecondQuestions extends Fragment {

    Button btnFinish;
    CheckBox chckYes, chckNo, chckReadAgreement;
    private RequestQueue requestQueue;
    Dialog dialog_agreement;
    Button btnAccept;
    ImageButton btnExit;
    RecyclerView recyclerViewAds;
    ArrayList<AdsClass> adsClasses;
    AdsRecycleViewAdapter adsRecycleViewAdapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_secondquestion, container, false);

        btnFinish = (Button) view.findViewById(R.id.btnFinish);
        chckYes = (CheckBox) view.findViewById(R.id.chckYes);
        chckNo = (CheckBox) view.findViewById(R.id.chckNo);
        chckReadAgreement = (CheckBox) view.findViewById(R.id.chckReadAgreement);
        recyclerViewAds = (RecyclerView) view.findViewById(R.id.recycleViewAds);

        recyclerViewAds.setHasFixedSize(true);
        //recyclerViewAds.setLayoutManager(new LinearLayoutManager(getContext()));
        final RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        recyclerViewAds.setLayoutManager(layoutManager);
        adsClasses = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(getContext());
        dialog_agreement = new Dialog(getContext());

        //Getting All the Ads Categories
        getJSONAllCategories();

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chckReadAgreement.isChecked()){
                    btnFinish.setEnabled(true);
                    Intent intent = new Intent(getContext(), EndScreen.class);
                    startActivity(intent);
                    getActivity().finish();
                   // Log.d("Array: ",Arrays.toString(AnswerSurvey.toArray()));
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
                    Survey.Gifts = "Yes";
                    chckNo.setChecked(false);
                }
            }
        });
        chckNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Survey.Gifts = "No";
                    chckYes.setChecked(false);
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

    //Getting All the Categories
    public void getJSONAllCategories(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Survey.url_fetch_categories, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArrayProducts = jsonObject.getJSONArray("Ads");

                    for (int index = 0; index < jsonArrayProducts.length(); index++) {
                        JSONObject parentObjectAds = jsonArrayProducts.getJSONObject(index);

                        //Getting for the Ads
                        String id = parentObjectAds.getString("id");
                        String categories = parentObjectAds.getString("category");

                        adsClasses.add(new AdsClass(id, categories));
                    }

                    adsRecycleViewAdapter = new AdsRecycleViewAdapter(getContext(), adsClasses);
                    recyclerViewAds.setAdapter(adsRecycleViewAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(getContext()).addToRequestque(stringRequest);
        requestQueue.add(stringRequest);
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
