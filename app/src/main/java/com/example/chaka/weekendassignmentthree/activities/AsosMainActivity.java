package com.example.chaka.weekendassignmentthree.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.chaka.weekendassignmentthree.R;
import com.example.chaka.weekendassignmentthree.conf.Constants;
import com.example.chaka.weekendassignmentthree.conf.Keys;
import com.example.chaka.weekendassignmentthree.fragments.NavigationDrawerFragment;
import com.example.chaka.weekendassignmentthree.fragments.ProductFragment;
import com.example.chaka.weekendassignmentthree.fragments.ProductListFragment;
import com.example.chaka.weekendassignmentthree.helpers.AsosApplication;
import com.example.chaka.weekendassignmentthree.interfaces.NavigationDrawerCallbacks;
import com.example.chaka.weekendassignmentthree.interfaces.iBasketCallback;
import com.example.chaka.weekendassignmentthree.interfaces.iProductListItemCallback;
import com.example.chaka.weekendassignmentthree.models.AsosUser;
import com.example.chaka.weekendassignmentthree.models.Categories;
import com.example.chaka.weekendassignmentthree.models.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.fabric.sdk.android.Fabric;
import roboguice.activity.RoboActionBarActivity;
import roboguice.util.Ln;


public class AsosMainActivity extends RoboActionBarActivity
        implements NavigationDrawerCallbacks, iProductListItemCallback, iBasketCallback {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;

    private View mRootView;

    private AsosUser currentUser;

    private ProgressDialog pDialog;

    private Categories mMensCategories;
    private Categories mWomensCategories;

    private static List<Product> basket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asos_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        //mToolbar.setTitle(null);
        getSupportActionBar().setTitle(null);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);

        mMensCategories = null;
        mWomensCategories = null;

        pDialog = new ProgressDialog(this);
        Ln.d("Test");
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        mRootView = findViewById(android.R.id.content);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(Constants.TWITTER_KEY, Constants.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        //loadDrawerCatergories();

        basket = new ArrayList<Product>();

        checkSharedPrefs();
    }



    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }


    private void loadDrawerCatergories() {

        showProgressDialog();



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(

                Constants.MEN_CATEGORY_URL,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        VolleyLog.d(response.toString());
                        JSONObject jsonObj = response;
                        try {
                            // Getting JSON Array node
                            Gson gson = new Gson();
                            mMensCategories = gson.fromJson(jsonObj.toString(),Categories.class);
                            //contactAdapter = new BirthdayAdapter(contactsList);
                            //mBirthdayList.setAdapter(contactAdapter);
                            checkLoadingComplete();


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
                Constants.WOMEN_CATEGORY_URL);

        JsonObjectRequest jsonObjReq2 = new JsonObjectRequest(

                Constants.WOMEN_CATEGORY_URL,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        JSONObject jsonObj = response;
                        try {
                            // Getting JSON Array node
                            Gson gson = new Gson();
                            mWomensCategories = gson.fromJson(jsonObj.toString(),Categories.class);
                            //contactAdapter = new BirthdayAdapter(contactsList);
                            //mBirthdayList.setAdapter(contactAdapter);
                            checkLoadingComplete();

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

        AsosApplication.getInstance().addToRequestQueue(jsonObjReq2,
                Constants.WOMEN_CATEGORY_URL);
    }

    private void checkLoadingComplete() {

        if(mMensCategories!=null&&mWomensCategories!=null){
            // Set up the drawer.
            mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int postition, String categoryUrl) {
        // update the main content by replacing fragments
        //Toast.makeText(this, "Menu item selected -> " + postition, Toast.LENGTH_SHORT).show();
        if(categoryUrl!=null) {
            // update the main content by replacing fragments
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, ProductListFragment.newInstance(categoryUrl))
                    .addToBackStack(null)
            .commit();
        }
    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else if(getSupportFragmentManager().popBackStackImmediate()){

        }else
            super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.asos_main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            onNavigationDrawerItemSelected(0, "catalog01_1001_5813");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void checkSharedPrefs() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_TAG, MODE_PRIVATE);
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            if(!sharedPreferences.contains(Keys.LAST_RUN_VERSION)){
                //TODO show tutorial
            }else if(!sharedPreferences.getString(Keys.LAST_RUN_VERSION,null).equals(version)){
                //TODO show new features
            }




        }
        /**
         * This method simply throws an Exception if the incoming parameter a is not a positive number, just for fun.
         *
         * @param e Whether or not to throw an exception
         * @throws android.content.pm.PackageManager.NameNotFoundException
         */
        catch(PackageManager.NameNotFoundException e){
            Log.e(Constants.LOG, "Name not found", e);
        }

        String userToken = sharedPreferences.getString(Keys.USER_TOKEN, null);

        if(userToken==null){
            launchLoginActivity();
        }else{
            if(!checkUserToken(userToken)){
                launchLoginActivity();
            }
        }

    }

    /**
     * Method that creates menu
     *
     * @param userToken The first integer to add
     * @return The resulting sum of a and b
     */
    private boolean checkUserToken(String userToken) {

        //TODO check token is still active
        //For now we assume it is
        return true;
    }

    private void launchLoginActivity() {

        Intent intent = new Intent(this,LoginActivity.class);
        startActivityForResult(intent,Constants.LOGIN_REQUEST_CODE);

    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constants.LOGIN_REQUEST_CODE:
                switch (resultCode){
                    case RESULT_OK:
                        AsosUser asosUser = data.getParcelableExtra(Keys.USER);

                        Snackbar
                                .make(mRootView, R.string.snackbar_login_text + asosUser.getName(), Snackbar.LENGTH_SHORT)
                                .show();

                        break;
                    case RESULT_CANCELED:
                        //If the user cancelled the action continue as guess
                        Snackbar
                                .make(mRootView, R.string.snackbar_login_cancelled_text, Snackbar.LENGTH_LONG)
                                .setAction(R.string.snackbar_login_action, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        launchLoginActivity();
                                    }
                                })
                                .show();
                        currentUser = new AsosUser(Constants.GUEST,Constants.GUEST);

                        break;
                    default:
                        break;
                }
        }
    }


    @Override
    public void productChosen(String chosenProductUrl) {
        // update the main content by replacing fragments
        if(chosenProductUrl!=null) {
            // update the main content by replacing fragments
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, ProductFragment.newInstance(chosenProductUrl))
                    .addToBackStack(null)
            .commit();
        }
    }

    @Override
    public void addProduct(Product product) {
        basket.add(product);
    }

    @Override
    public List<Product> getBasket() {
        return basket;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Gson gson = new Gson();
        String basketString = gson.toJson(basket);
        outState.putString(Keys.BASKET,basketString);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState.containsKey(Keys.BASKET)){

            String basketString = savedInstanceState.getString(Keys.BASKET);

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Product>>(){}.getType();
            basket = gson.fromJson(basketString, listType);
        }




    }
}
