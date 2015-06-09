package com.example.chaka.weekendassignmentthree.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.chaka.weekendassignmentthree.R;
import com.example.chaka.weekendassignmentthree.adapters.ProductListAdapter;
import com.example.chaka.weekendassignmentthree.conf.Constants;
import com.example.chaka.weekendassignmentthree.conf.Keys;
import com.example.chaka.weekendassignmentthree.helpers.AsosApplication;
import com.example.chaka.weekendassignmentthree.interfaces.iProductListItemCallback;
import com.example.chaka.weekendassignmentthree.listeners.RecyclerClickerListener;
import com.example.chaka.weekendassignmentthree.models.Products;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends Fragment {

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private iProductListItemCallback mCallbacks;



    RecyclerView mProductRecyclerView;

    View mRootView;

    GridLayoutManager mGridLayoutManager;

    private ProgressDialog pDialog;

    Products mProducts;

    ProductListAdapter mProductListAdapter;



    /**
     * Static factory method that takes an int parameter,
     * initializes the fragment's arguments, and returns the
     * new fragment to the client.
     */
    public static ProductListFragment newInstance(String productListUrl) {
        ProductListFragment productListFragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putString(Keys.PRODUCT_LIST_URL, productListUrl);
        productListFragment.setArguments(args);



        return productListFragment;
    }

    public ProductListFragment() {
        // Required empty public constructor
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_product_list, container, false);

        mProductRecyclerView = (RecyclerView)mRootView.findViewById(R.id.product_list_fragment_rv_product_list);

        mGridLayoutManager = new GridLayoutManager(getActivity(),2);

        mProductRecyclerView.setLayoutManager(mGridLayoutManager);

        mProductRecyclerView.addOnItemTouchListener(new RecyclerClickerListener(getActivity(),
                new RecyclerClickerListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //TODO swap to product fragement
                mCallbacks.productChosen(mProducts.getListings().get(position).getProductId());


            }
        }));

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        makeProductListRequest(getArguments().getString(Keys.PRODUCT_LIST_URL));

        return mRootView;
    }



    private void makeProductListRequest(String productListUrl) {

        showProgressDialog();



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(

                Constants.PRODUCT_LIST_BASE_URL+productListUrl,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        VolleyLog.d(response.toString());
                        JSONObject jsonObj = response;
                        try {
                            // Getting JSON Array node
                            Gson gson = new Gson();
                            mProducts = gson.fromJson(jsonObj.toString(),Products.class);
                            //contactAdapter = new BirthdayAdapter(contactsList);
                            //mBirthdayList.setAdapter(contactAdapter);


                            mProductListAdapter = new ProductListAdapter(mProducts.getListings());
                            mProductRecyclerView.setAdapter(mProductListAdapter);

                        } catch (Exception e) {
                            e.printStackTrace();

                            hideProgressDialog();
                        }
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                Toast.makeText(getActivity(), "Error" + error.networkResponse.statusCode, Toast.LENGTH_LONG).show();
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
                Keys.PRODUCT_LIST_URL);


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (iProductListItemCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }


}
