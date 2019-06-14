package com.example.privateex.pandorasurvey.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
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


import com.example.privateex.pandorasurvey.MainActivity;
import com.example.privateex.pandorasurvey.R;
import com.example.privateex.pandorasurvey.SecondSurvey;

public class FirstQuestions extends Fragment {

    Dialog dialogOthers;
    CheckBox chckOthers;
    ImageButton btnExit;
    Button btnNext;
    SecondSurvey secondSurvey;
    TextView txtQuestion, txtTitle, txtQuestion2;
    LinearLayout layout, layout3, layout4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_firstquestion, container, false);

        chckOthers = (CheckBox) view.findViewById(R.id.chckOthers);
        btnNext = (Button) view.findViewById(R.id.btnNext);

        txtQuestion = (TextView) view.findViewById(R.id.txtQuestion);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtQuestion2 = (TextView) view.findViewById(R.id.txtQuestion2);

        layout = (LinearLayout) view.findViewById(R.id.layout);
        layout3 = (LinearLayout) view.findViewById(R.id.layout3);
        layout4 = (LinearLayout) view.findViewById(R.id.layout4);

        txtQuestion.setVisibility(View.INVISIBLE);
        txtTitle.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.INVISIBLE);
        txtQuestion2.setVisibility(View.INVISIBLE);
        layout3.setVisibility(View.INVISIBLE);
        layout4.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);

        txtQuestion.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.splash));
        txtQuestion.setVisibility(View.VISIBLE);
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
