package com.example.chaka.weekendassignmentthree.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chaka.weekendassignmentthree.R;
import com.example.chaka.weekendassignmentthree.conf.Constants;
import com.example.chaka.weekendassignmentthree.models.Listing;
import com.example.chaka.weekendassignmentthree.util.Log;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Chaka on 07/06/2015.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {



    private List<Listing> productList;

    private Context mContext;

    public ProductListAdapter(List<Listing> productList) {
        this.productList = productList;
    }

    public ProductListAdapter(List<Listing> productList, Context context) {
        this.productList = productList;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder productViewHolder,int i) {
        final Listing cd = productList.get(i);

        try {
            Picasso.with(mContext).load(cd.getProductImageUrl().get(0)).into(productViewHolder.vProductPhoto);
        }catch (NullPointerException e){
            Log.e(Constants.LOG,"onBindViewHolder NullpointerException",e);
        }catch (Exception e){
            Log.e(Constants.LOG,"onBindViewHolder Exception",e);
        }

        productViewHolder.vPriceText.setText(cd.getCurrentPrice());


    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView;

        mContext = viewGroup.getContext();

        itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_product_list_item, viewGroup, false);

        return new ProductViewHolder(itemView);
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        protected ImageView vProductPhoto;
        protected TextView vPriceText;
        protected View vView;


        public ProductViewHolder(View v) {
            super(v);
            vProductPhoto = (ImageView)v.findViewById(R.id.card_product_list_item_iv_product_image);
            vPriceText = (TextView)v.findViewById(R.id.card_product_list_tv_price);
            vView = v;




        }
    }

}

