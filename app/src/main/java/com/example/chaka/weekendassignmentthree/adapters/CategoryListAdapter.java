package com.example.chaka.weekendassignmentthree.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chaka.weekendassignmentthree.R;
import com.example.chaka.weekendassignmentthree.models.Category;

import java.util.List;

/**
 * Created by Chaka on 08/06/2015.
 */
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {



    private List<Category> categoryList;

    private Context mContext;

    public CategoryListAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public CategoryListAdapter(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder productViewHolder,int i) {
        final Category cd = categoryList.get(i);


        productViewHolder.vNameText.setText(cd.getName());


    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView;

        mContext = viewGroup.getContext();

        itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_category_list_item, viewGroup, false);

        return new CategoryViewHolder(itemView);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        protected TextView vNameText;
        protected View vView;


        public CategoryViewHolder(View v) {
            super(v);
            vNameText = (TextView)v.findViewById(R.id.card_category_list_item_name);
            vView = v;




        }
    }
    
    
}
