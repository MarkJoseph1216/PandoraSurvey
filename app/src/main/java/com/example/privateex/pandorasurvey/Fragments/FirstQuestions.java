package com.example.privateex.pandorasurvey.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.privateex.pandorasurvey.R;
import com.example.privateex.pandorasurvey.SecondSurvey;

public class FirstQuestions extends Fragment {

    Dialog dialogOthers;
    CheckBox chckOthers;
    ImageButton btnExit;
    Button btnNext;
    SecondSurvey secondSurvey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_firstquestion, container, false);

        chckOthers = (CheckBox) view.findViewById(R.id.chckOthers);
        btnNext = (Button) view.findViewById(R.id.btnNext);

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
