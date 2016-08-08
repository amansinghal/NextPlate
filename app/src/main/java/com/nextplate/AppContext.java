package com.nextplate;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.firebase.client.Firebase;
import com.nextplate.core.preference.Prefrences;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Created by gspl on 12/9/2015.
 */
public class AppContext extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        facebookHashKey();
        FacebookSdk.sdkInitialize(getApplicationContext());
        //To move to admin department please put the boolean false
        Prefrences.getWriteInstance(this).putBoolean(Prefrences.IS_CLIENT,true).commit();
    }

    private void facebookHashKey()
    {

        try
        {
            PackageInfo info = getPackageManager().getPackageInfo(getString(R.string.package_name), PackageManager.GET_SIGNATURES);
            for(Signature signature : info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashCode = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                System.out.println("Print the hashKey for Facebook :" + hashCode);
                Log.e("KeyHash:", hashCode);
            }
        }
        catch(PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
    }
}
