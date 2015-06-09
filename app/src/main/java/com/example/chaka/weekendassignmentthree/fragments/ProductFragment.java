package com.example.chaka.weekendassignmentthree.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.chaka.weekendassignmentthree.R;
import com.example.chaka.weekendassignmentthree.conf.Constants;
import com.example.chaka.weekendassignmentthree.conf.Keys;
import com.example.chaka.weekendassignmentthree.helpers.AsosApplication;
import com.example.chaka.weekendassignmentthree.interfaces.iBasketCallback;
import com.example.chaka.weekendassignmentthree.models.Product;
import com.google.android.gms.plus.PlusOneButton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;


/**
 * A fragment with a Google +1 button.
 */
public class ProductFragment extends RoboFragment {

    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";

    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;

    private PlusOneButton mPlusOneButton;

    private iBasketCallback mBasket;

    private ProgressDialog pDialog;

    private Product mProduct;

    @InjectView(R.id.product_fragment_tv_item_name)
    TextView mNameText;

    @InjectView(R.id.product_fragment_tv_item_price)
    TextView mPriceText;

    @InjectView(R.id.product_fragment_tv_item_description)
    TextView mDescriptionText;


    @InjectView(R.id.product_fragment_fab_add_to_basket)
    FloatingActionButton mFloatingActionButton;

    ViewPager mImagesViewPager;

    @InjectView(R.id.product_fragment_view_flipper_images)
    ViewFlipper mViewFlipper;


    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment newInstance(String productUrl) {
        ProductFragment productFragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(Keys.PRODUCT_URL, productUrl);
        productFragment.setArguments(args);
        return productFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);


        //Find the +1 button
        mPlusOneButton = (PlusOneButton) view.findViewById(R.id.plus_one_button);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

      /*  mNameText = (TextView)view.findViewById(R.id.product_fragment_tv_item_name);
        mPriceText = (TextView)view.findViewById(R.id.product_fragment_tv_item_price);
        mDescriptionText = (TextView)view.findViewById(R.id.product_fragment_tv_item_description);

       // mImagesViewPager = (ViewPager)view.findViewById(R.id.product_fragment_view_pager_images);
        mViewFlipper = (ViewFlipper)view.findViewById(R.id.product_fragment_view_flipper_images);*/
        makeProductRequest(getArguments().getString(Keys.PRODUCT_LIST_URL));

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBasket.addProduct(mProduct);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh the state of the +1 button each time the activity receives focus.
        mPlusOneButton.initialize(PLUS_ONE_URL, PLUS_ONE_REQUEST_CODE);
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    private void makeProductRequest(String productUrl) {

        showProgressDialog();



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(

                Constants.PRODUCT_BASE_URL+productUrl,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //VolleyLog.d(response.toString());
                        JSONObject jsonObj = response;
                        try {
                            // Getting JSON Array node
                            Gson gson = new Gson();
                            mProduct = gson.fromJson(jsonObj.toString(),Product.class);
                            //contactAdapter = new BirthdayAdapter(contactsList);
                            //mBirthdayList.setAdapter(contactAdapter);

                            mNameText.setText(mProduct.getTitle());
                            mPriceText.setText(mProduct.getCurrentPrice());
                            mDescriptionText.setText(mProduct.getDescription());

                            for(String url:mProduct.getProductImageUrls()){
                                ImageView image = new ImageView(getActivity());
                                Picasso.with(getActivity()).load(url).into(image);
                                mViewFlipper.addView(image);
                            }
                            mViewFlipper.setFlipInterval(1500);
                            mViewFlipper.startFlipping();


                        } catch (Exception e) {
                            e.printStackTrace();


                        }
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                Toast.makeText(getActivity(),"Error" + error.networkResponse.statusCode, Toast.LENGTH_LONG).show();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

        };

        AsosApplication.getInstance().addToRequestQueue(jsonObjReq,
                Keys.PRODUCT_URL);


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mBasket = (iBasketCallback)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mBasket = null;
    }
}
