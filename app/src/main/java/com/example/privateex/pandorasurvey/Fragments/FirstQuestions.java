package com.example.privateex.pandorasurvey.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.privateex.pandorasurvey.Adapter.CategoriesRecyclerViewAdapter;
import com.example.privateex.pandorasurvey.Adapter.ProductsRecycleViewAdapter;
import com.example.privateex.pandorasurvey.Class.CategoriesClass;
import com.example.privateex.pandorasurvey.Class.ProductsClass;
import com.example.privateex.pandorasurvey.MySingleton;
import com.example.privateex.pandorasurvey.R;
import com.example.privateex.pandorasurvey.SecondSurvey;
import com.example.privateex.pandorasurvey.Survey.Survey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FirstQuestions extends Fragment {

    Dialog dialogOthers;
    ImageButton btnExit;
    Button btnNext, btnSubmit;
    SecondSurvey secondSurvey;
    TextView txtPageNumber, txtTitle, txtQuestion2;
    TextView txtOthers;
    TextInputEditText edtOthers;
    CheckBox chckOthers;
    private RequestQueue requestQueue;
    private CategoriesRecyclerViewAdapter categoriesRecyclerViewAdapter;
    private ProductsRecycleViewAdapter productsRecycleViewAdapter;
    private ArrayList<CategoriesClass> categoriesClasses;
    ArrayList<ProductsClass> productsClasses;
    private RecyclerView recyclerViewSocialMedia, recycleProducts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_firstquestion, container, false);

        requestQueue = Volley.newRequestQueue(getContext());

        btnNext = (Button) view.findViewById(R.id.btnNext);

        txtOthers = (TextView) view.findViewById(R.id.txtOthers);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtQuestion2 = (TextView) view.findViewById(R.id.txtQuestion2);
        txtQuestion2 = (TextView) view.findViewById(R.id.txtQuestion2);
        txtPageNumber = (TextView) view.findViewById(R.id.txtPageNumber);
        chckOthers = (CheckBox) view.findViewById(R.id.chckOthers);

        recyclerViewSocialMedia = (RecyclerView) view.findViewById(R.id.recycleSocialMedia);
        recyclerViewSocialMedia.setHasFixedSize(true);
        recyclerViewSocialMedia.setLayoutManager(new LinearLayoutManager(getContext()));
        final RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        recyclerViewSocialMedia.setLayoutManager(layoutManager);
        categoriesClasses = new ArrayList<>();

        recycleProducts = (RecyclerView) view.findViewById(R.id.recycleProducts);
        recycleProducts.setHasFixedSize(true);
        recycleProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        final RecyclerView.LayoutManager layoutManagerProduct = new GridLayoutManager(getContext(), 4);
        recycleProducts.setLayoutManager(layoutManagerProduct);
        productsClasses = new ArrayList<>();

        txtTitle.setVisibility(View.INVISIBLE);
        txtPageNumber.setVisibility(View.INVISIBLE);
        chckOthers.setVisibility(View.INVISIBLE);
        txtOthers.setVisibility(View.INVISIBLE);
        txtQuestion2.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
        recyclerViewSocialMedia.setVisibility(View.INVISIBLE);
        recycleProducts.setVisibility(View.INVISIBLE);

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
                recyclerViewSocialMedia.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
                recyclerViewSocialMedia.setVisibility(View.VISIBLE);
            }
        }, 1500);
        final Handler handlers = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                chckOthers.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
                chckOthers.setVisibility(View.VISIBLE);
                txtOthers.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
                txtOthers.setVisibility(View.VISIBLE);
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
                recycleProducts.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
                recycleProducts.setVisibility(View.VISIBLE);
            }
        }, 1900);
        final Handler handler4 = new Handler();
        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
                btnNext.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
                btnNext.setVisibility(View.VISIBLE);
            }
        }, 2100);

        secondSurvey = new SecondSurvey();
        dialogOthers = new Dialog(getContext());

        //Getting All the Categories
        getJSONAllCategories();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondSurvey.viewPager.setCurrentItem(2);
            }
        });

        chckOthers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    showPopupMessage1();
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
                    JSONArray jsonArray = jsonObject.getJSONArray("Social Media");
                    JSONArray jsonArrayProducts = jsonObject.getJSONArray("Products");

                    for (int index = 0; index < jsonArray.length(); index++) {
                        JSONObject parentObject = jsonArray.getJSONObject(index);
                        JSONObject parentObjectProduct = jsonArrayProducts.getJSONObject(index);

                        //Getting for the Social Media
                        String id = parentObject.getString("id");
                        String categories = parentObject.getString("category");

                        //Getting for the Products
                        String idProduct = parentObjectProduct.getString("id");
                        String categoriesProduct = parentObjectProduct.getString("category");

                        categoriesClasses.add(new CategoriesClass(id, categories));
                        productsClasses.add(new ProductsClass(idProduct, categoriesProduct));
                    }
                    categoriesRecyclerViewAdapter = new CategoriesRecyclerViewAdapter(getContext(), categoriesClasses);
                    recyclerViewSocialMedia.setAdapter(categoriesRecyclerViewAdapter);

                    productsRecycleViewAdapter = new ProductsRecycleViewAdapter(getContext(), productsClasses);
                    recycleProducts.setAdapter(productsRecycleViewAdapter);

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

            }
        });
        dialogOthers.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogOthers.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogOthers.setCanceledOnTouchOutside(false);
        dialogOthers.show();
    }
}
