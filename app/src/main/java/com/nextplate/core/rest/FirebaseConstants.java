package com.nextplate.core.rest;

import com.facebook.AccessToken;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
/**
 * Created by gspl on 12/9/2015.
 */
public class FirebaseConstants
{
    public static String FIREBASE_URL = "https://padhaarosaa.firebaseio.com/";
    static Firebase ref = new Firebase("https://padhaarosaa.firebaseio.com/");

    public static void onFacebookAccessTokenChange(AccessToken token, Firebase.AuthResultHandler authResultHandler)
    {
        if(token != null)
        {
            ref.authWithOAuthToken("facebook", token.getToken(), authResultHandler);
        }
        else
        {
        /* Logged out of Facebook so do a logout from the Firebase app */
            ref.unauth();
        }
    }

    public static void getData(ValueEventListener valueEventListener)
    {
        ref.addValueEventListener(valueEventListener);
    }

    public static Firebase getReference()
    {
        return ref;
    }
}
