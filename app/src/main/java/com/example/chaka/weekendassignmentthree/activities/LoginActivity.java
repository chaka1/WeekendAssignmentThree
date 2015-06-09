package com.example.chaka.weekendassignmentthree.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaka.weekendassignmentthree.R;
import com.example.chaka.weekendassignmentthree.conf.Keys;
import com.example.chaka.weekendassignmentthree.models.AsosUser;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private View mRootView;

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    /**
     * True if the sign-in button was clicked.  When true, we know to resolve all
     * issues preventing sign-in without waiting.
     */
    private boolean mSignInClicked;

    /**
     * True if we are in the process of resolving a ConnectionResult
     */
    private boolean mIntentInProgress;

    private CallbackManager callbackManager;
    private TextView info;
    private LoginButton loginButton;

    private TwitterLoginButton twitterLoginButton;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;


        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);
        mRootView = findViewById(android.R.id.content);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        loginButton = (LoginButton)findViewById(R.id.facebook_login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //AppUser newAppUser = new AppUser();
                //newAppUser.setName(loginResult.getAccessToken().getUserId());

                //Toast.makeText(mContext, "AppUser logged in with facebook", Toast.LENGTH_LONG).show();
                AsosUser asosUser = new AsosUser(loginResult.getAccessToken().getUserId(),loginResult.getAccessToken().getToken());
                Intent resultIntent = new Intent();
                resultIntent.putExtra(Keys.USER,asosUser);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }


            @Override
            public void onCancel() {
                //info.setText("Login attempt cancelled.");
                Toast.makeText(mContext, "Cancelled facebook",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(mContext, "Error with facebook",Toast.LENGTH_LONG).show();

            }
        });

        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {

                AsosUser asosUser = new AsosUser(result.data.getUserName(),result.data.getAuthToken().token);
                Intent resultIntent = new Intent();
                resultIntent.putExtra(Keys.USER,asosUser);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();


            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Snackbar
                        .make(mRootView, R.string.login_failed, Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void getUser() {
        Twitter.getApiClient().getAccountService().verifyCredentials(true, false, new Callback<User>() {
            @Override
            public void success(Result<User> userResult) {

                User user = userResult.data;
                String profileImage= user.profileImageUrl;
                String screenName = user.screenName;





            }

            @Override
            public void failure(TwitterException e) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!mIntentInProgress) {
            if (mSignInClicked && result.hasResolution()) {
                // The user has already clicked 'sign-in' so we attempt to resolve all
                // errors until the user is signed in, or they cancel.
                try {
                    result.startResolutionForResult(this, RC_SIGN_IN);
                    mIntentInProgress = true;
                } catch (IntentSender.SendIntentException e) {
                    // The intent was canceled before it was sent.  Return to the default
                    // state and attempt to connect to get an updated ConnectionResult.
                    mIntentInProgress = false;
                    mGoogleApiClient.connect();
                }
            }
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.sign_in_button && !mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            mGoogleApiClient.connect();
        }
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnected()) {
                mGoogleApiClient.reconnect();
            }
        }

        callbackManager.onActivityResult(requestCode, responseCode, intent);
        twitterLoginButton.onActivityResult(requestCode, responseCode, intent);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        mSignInClicked = false;

        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String personName = currentPerson.getDisplayName();

                //TODO set authentication
                AsosUser asosUser = new AsosUser(personName,"Fake key as we don't authenticate using it ever");
                Intent resultIntent = new Intent();
                resultIntent.putExtra(Keys.USER,asosUser);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }
}
