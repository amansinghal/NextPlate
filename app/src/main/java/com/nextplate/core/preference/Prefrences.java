package com.nextplate.core.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by gspl on 10/5/2015.
 */
public class Prefrences
{

    public static String PREF_KEY = "PREF_KEY";
    public static String PREF_IS_LOGGED_IN = "PREF_IS_LOGGED_IN";
    public static String PREF_UID = "PREF_UID";
    static SharedPreferences pref;
    private static String PREF_TOKEN = "PREF_TOKEN";
    private static boolean needToUpdate = false;


    private Prefrences() {
    }

    public static boolean getNeedToUpdate() {
        if (needToUpdate) {
            needToUpdate = false;
            return true;
        }
        return false;
    }

    public static void setNeedToUpdate() {
        needToUpdate = true;
    }

    public static SharedPreferences.Editor getWriteInstance(Context context) {
        pref = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        return pref.edit();
    }

    public static SharedPreferences getReadInstance(Context context) {
        pref = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        return pref;
    }

    public static void writeClass(Context context, Object classObj) {
        Gson gson = new Gson();
        getWriteInstance(context).putString(classObj.getClass().getSimpleName(), gson.toJson(classObj)).apply();
    }

    public static <T> Object readClass(Context context, Class<T> classType) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = new Gson();
        return gson.fromJson(getReadInstance(context).getString(classType.getSimpleName(), ""), classType);
    }

    public static boolean isUserLoggedIn(Context context)
    {
        return getReadInstance(context).getBoolean(PREF_IS_LOGGED_IN,false);
    }

    public static void setToken(Context context, String token) {
        getWriteInstance(context).putString(PREF_TOKEN, token).apply();
    }

    public static String getToken(Context context) {
        return getReadInstance(context).getString(PREF_TOKEN, "");
    }
}
