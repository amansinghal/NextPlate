package com.nextplate.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.nextplate.R;
import com.nextplate.core.activity.BaseActivity;
import com.nextplate.core.preference.Prefrences;
import com.nextplate.core.rest.FirebaseConstants;
import com.nextplate.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.Bind;
/**
 * Created by AmaN on 3/12/2016.
 */
public class FBLoginActivity extends BaseActivity
{
    @Bind(R.id.login_button)
    LoginButton loginButton;
    private CallbackManager callbackManager;
    private Firebase firebase;

    @Override
    public void onCreateCustom()
    {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        firebase = getFireBase();
        firebase = firebase.child(FirebaseConstants.URL_USERS);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                showProgress();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback()
                {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response)
                    {
                        Log.v("LoginActivity", response.toString());
                        loginOrRegUser(object);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
                // loginOrRegUser(loginResult.getAccessToken());
            }

            @Override
            public void onCancel()
            {

            }

            @Override
            public void onError(FacebookException error)
            {

            }
        });
    }

    @Override
    public int getActivityContentView()
    {
        return R.layout.activity_login_facebook;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void loginOrRegUser(final JSONObject loginResult)
    {
        if(loginResult != null)
        {
            try
            {
                System.out.println(loginResult);
                Query query = firebase.orderByChild("fbId").equalTo(loginResult.getString("id"));
                query.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        System.out.println(dataSnapshot);
                        if(dataSnapshot.getValue() == null)
                        {
                            String[] strings = new String[3];
                            try
                            {
                                strings[0] = loginResult.getString("id");
                                strings[1] = loginResult.getString("name");
                                strings[2] = loginResult.getString("email");
                                addNewUser(strings);
                            }
                            catch(JSONException e)
                            {
                                hideProgress();
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            User user = null;
                            for(DataSnapshot snapshot : dataSnapshot.getChildren())
                            {
                                user = snapshot.getValue(User.class);
                            }
                            saveUserAndProceed(user);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError)
                    {
                        hideProgress();
                        System.out.println(firebaseError);
                    }
                });
            }
            catch(Exception e)
            {
                e.printStackTrace();
                hideProgress();
            }
        }
    }

    private void addNewUser(final String[] data)
    {
        firebase.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                firebase.removeEventListener(this);
                int count = (int) dataSnapshot.getChildrenCount();
                User user = new User();
                user.setFbId(data[0]);
                user.setName(data[1]);
                user.setEmail(data[2]);
                Long tsLong = System.currentTimeMillis() / 1000;
                String ts = tsLong.toString();
                user.setCreatedAt(ts);
                firebase.child("/" + count).setValue(user, new Firebase.CompletionListener()
                {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase)
                    {
                        hideProgress();
                        System.out.println(firebase);
                    }
                });
            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {
                hideProgress();
            }
        });
    }

    private void saveUserAndProceed(User user)
    {
        Prefrences.writeClass(this, user);
        Prefrences.getWriteInstance(this).putBoolean(Prefrences.PREF_IS_LOGGED_IN, true).apply();
    }
}
