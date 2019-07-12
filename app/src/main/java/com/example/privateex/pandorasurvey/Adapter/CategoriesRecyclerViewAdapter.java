package com.example.privateex.pandorasurvey.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.privateex.pandorasurvey.Class.CategoriesClass;
import com.example.privateex.pandorasurvey.R;
import com.example.privateex.pandorasurvey.Survey.Survey;

import java.util.ArrayList;

public class CategoriesRecyclerViewAdapter extends RecyclerView.Adapter<CategoriesRecyclerViewAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<CategoriesClass> categoriesList;

    public CategoriesRecyclerViewAdapter(Context context, ArrayList<CategoriesClass> categorieslist){
        mcontext = context;
        categoriesList = categorieslist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.custom_layoutcategories, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CategoriesClass currentData = categoriesList.get(position);

        String categories = currentData.getCategory();
        final String id = currentData.getId();

        holder.checkSocialMedia.setText(categories);

        holder.checkSocialMedia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Survey.AnswerSurvey.add(id);
                }
                else {
                    Survey.AnswerSurvey.remove(id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public CheckBox checkSocialMedia;

        public ViewHolder(final View itemView) {
            super(itemView);

            checkSocialMedia = (CheckBox) itemView.findViewById(R.id.checkSocialMedia);
        }
    }
}
