package com.example.chaka.weekendassignmentthree.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.chaka.weekendassignmentthree.R;
import com.example.chaka.weekendassignmentthree.adapters.CategoryListAdapter;
import com.example.chaka.weekendassignmentthree.conf.Constants;
import com.example.chaka.weekendassignmentthree.conf.Keys;
import com.example.chaka.weekendassignmentthree.helpers.AsosApplication;
import com.example.chaka.weekendassignmentthree.interfaces.NavigationDrawerCallbacks;
import com.example.chaka.weekendassignmentthree.listeners.RecyclerClickerListener;
import com.example.chaka.weekendassignmentthree.models.Categories;
import com.example.chaka.weekendassignmentthree.models.Category;
import com.example.chaka.weekendassignmentthree.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roboguice.util.Ln;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment implements NavigationDrawerCallbacks {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private RecyclerView mDrawerList;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;


    private ProgressDialog pDialog;

    private Categories mMensCategories;
    private Categories mWomensCategories;

    private CategoryListAdapter mMensCategoryListAdapter;
    private CategoryListAdapter mWomensCategoryListAdapter;

    private TextView mMensText;
    private TextView mWomensText;

    private Boolean mMensActive;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asos_navigation_drawer, container, false);
        mDrawerList = (RecyclerView) view.findViewById(R.id.asos_navigation_drawer_fragment_rv_categories_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDrawerList.setLayoutManager(layoutManager);
        mDrawerList.setHasFixedSize(true);

        //mMensCategoryListAdapter = new CategoryListAdapter();
        //mDrawerList.setAdapter(mMensCategoryListAdapter);

        mMensCategoryListAdapter = new CategoryListAdapter(new ArrayList<Category>());
        mDrawerList.setAdapter(mMensCategoryListAdapter);

        mDrawerList.addOnItemTouchListener(new RecyclerClickerListener(getActivity(),
                new RecyclerClickerListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mCurrentSelectedPosition = position;
                if (mDrawerLayout != null) {
                    mDrawerLayout.closeDrawer(mFragmentContainerView);
                }
                if (mCallbacks != null) {
                    if(mMensActive){

                        mCallbacks.onNavigationDrawerItemSelected(position, mMensCategories.getListing().get(position).getCategoryId());
                    }else{

                        mCallbacks.onNavigationDrawerItemSelected(position, mWomensCategories.getListing().get(position).getCategoryId());
                    }
                }
                //((NavigationDrawerAdapter) mDrawerList.getAdapter()).selectPosition(position);


            }
        }));

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        Ln.d("Oncreateview nav drawer fragment");

        mMensText = (TextView)view.findViewById(R.id.asos_navigation_drawer_fragment_tv_mens_text);
        mWomensText = (TextView)view.findViewById(R.id.asos_navigation_drawer_fragment_tv_womens_text);

        loadCategories();

        return view;
    }

    private void loadCategories() {

        showProgressDialog();
        Ln.d("load catergories nav drawer fragment");
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
                            Type listType = new TypeToken<List<Category>>() {}.getType();
                            String json = jsonObj.getJSONArray(Keys.LISTING).toString();
                            List<Category> list = gson.fromJson(json, listType);
                            mMensCategories.setListing(list);

                            mMensCategoryListAdapter = new CategoryListAdapter(mMensCategories.getListing());

                            Log.d(Constants.LOG, "Category list" + mMensCategories.getListing().toString());
                            mDrawerList.setAdapter(mMensCategoryListAdapter);
                            mMensActive = true;
                            mMensText.setClickable(true);
                            mMensText.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDrawerList.setAdapter(mMensCategoryListAdapter);
                                    mMensActive = true;
                                }
                            });

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
                            Type listType = new TypeToken<List<Category>>() {}.getType();
                            String json = jsonObj.getJSONArray(Keys.LISTING).toString();
                            List<Category> list = gson.fromJson(json, listType);
                            mWomensCategories.setListing(list);


                            //contactAdapter = new BirthdayAdapter(contactsList);
                            //mBirthdayList.setAdapter(contactAdapter);

                            mWomensCategoryListAdapter = new CategoryListAdapter(mWomensCategories.getListing());
                            Log.d(Constants.LOG,"Womens list" + mWomensCategories.getListing().toString());
                            mWomensText.setClickable(true);
                            mWomensText.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDrawerList.setAdapter(mWomensCategoryListAdapter);
                                    mMensActive = false;

                                }
                            });

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

        AsosApplication.getInstance().addToRequestQueue(jsonObjReq2,
                Constants.WOMEN_CATEGORY_URL);


    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public ActionBarDrawerToggle getActionBarDrawerToggle() {
        return mActionBarDrawerToggle;
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    @Override
    public void onNavigationDrawerItemSelected(int postition, String categoryUrl) {
        selectItem(postition);


    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     * @param toolbar      The Toolbar of the activity.
     */
    public void setup(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.myPrimaryDarkColor));

        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) return;

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) return;
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }
                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position, mMensCategories.getListing().get(position).getCategoryId());
        }
        //((NavigationDrawerAdapter) mDrawerList.getAdapter()).selectPosition(position);
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

}
