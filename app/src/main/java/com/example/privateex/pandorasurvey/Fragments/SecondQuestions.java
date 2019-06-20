package com.example.privateex.pandorasurvey.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.privateex.pandorasurvey.EndScreen;
import com.example.privateex.pandorasurvey.R;

public class SecondQuestions extends Fragment {

    Button btnFinish;
    CheckBox chckYes, chckNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_secondquestion, container, false);

        btnFinish = (Button) view.findViewById(R.id.btnFinish);
        chckYes = (CheckBox) view.findViewById(R.id.chckYes);
        chckNo = (CheckBox) view.findViewById(R.id.chckNo);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EndScreen.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        chckYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    chckNo.setChecked(false);
                }
            }
        });
        chckNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    chckYes.setChecked(false);
                }
            }
        });

        return view;
    }
}
