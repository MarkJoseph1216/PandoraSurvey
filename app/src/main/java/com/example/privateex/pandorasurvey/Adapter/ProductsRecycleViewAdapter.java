package com.example.privateex.pandorasurvey.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.privateex.pandorasurvey.Class.CategoriesClass;
import com.example.privateex.pandorasurvey.Class.ProductsClass;
import com.example.privateex.pandorasurvey.R;
import com.example.privateex.pandorasurvey.Survey.Survey;

import java.util.ArrayList;
import java.util.Arrays;

public class ProductsRecycleViewAdapter extends RecyclerView.Adapter<ProductsRecycleViewAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<ProductsClass> productsList;

    public ProductsRecycleViewAdapter(Context context, ArrayList<ProductsClass> productsclasses) {
        mcontext = context;
        productsList = productsclasses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.custom_layoutcategories, parent, false);
        return new ProductsRecycleViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ProductsRecycleViewAdapter.ViewHolder holder, int position) {
        ProductsClass currentData = productsList.get(position);

        String products = currentData.getCategory();
        final String id = currentData.getId();

        holder.checkSocialMedia.setText(products);

        holder.checkSocialMedia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Survey.AnswerSurveyProducts.add(id);
                 }
                else {
                    Survey.AnswerSurveyProducts.remove(id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CheckBox checkSocialMedia;

        public ViewHolder(final View itemView) {
            super(itemView);

            checkSocialMedia = (CheckBox) itemView.findViewById(R.id.checkSocialMedia);
        }
    }
}
