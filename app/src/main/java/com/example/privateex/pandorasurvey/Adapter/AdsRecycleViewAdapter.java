package com.example.privateex.pandorasurvey.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.privateex.pandorasurvey.Class.AdsClass;
import com.example.privateex.pandorasurvey.Class.CategoriesClass;
import com.example.privateex.pandorasurvey.R;
import com.example.privateex.pandorasurvey.Survey.Survey;

import java.util.ArrayList;
import java.util.Arrays;

public class AdsRecycleViewAdapter extends RecyclerView.Adapter<AdsRecycleViewAdapter.ViewHolder> {

    private Context mcontext;
    ArrayList<AdsClass> adsClasses;

    public AdsRecycleViewAdapter(Context context, ArrayList<AdsClass> adsclasses){
        mcontext = context;
        adsClasses = adsclasses;
    }

    @Override
    public AdsRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.custom_layoutcategories, parent, false);
        return new AdsRecycleViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AdsRecycleViewAdapter.ViewHolder holder, int position) {
        AdsClass currentData = adsClasses.get(position);

        String categories = currentData.getCategory();
        final String id = currentData.getId();

        holder.checkSocialMedia.setText(categories);

        holder.checkSocialMedia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Survey.AnswerSurveyAds.add(id);
                }
                else {
                    Survey.AnswerSurveyAds.remove(id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return adsClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public CheckBox checkSocialMedia;

        public ViewHolder(final View itemView) {
            super(itemView);

            checkSocialMedia = (CheckBox) itemView.findViewById(R.id.checkSocialMedia);
        }
    }
}
