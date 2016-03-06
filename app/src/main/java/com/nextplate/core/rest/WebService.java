package com.nextplate.core.rest;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by AmaN on 10/2/2015.
 */
public class WebService {

    public static String VERIFY_CONTACT_KEY_EMAIL ="105";
    public static String VERIFY_CONTACT_KEY_PHONE="104";
    public static String WRONG_CREDENTIAL_KEY="102";
    public static String ALREADY_SIGNED_KEY="101";

    static WebService webService;
    private static String ENDPOINT = "http://mindgrove.pro/medi/web-services";
    private RestAdapter restAdapter;

    private WebService() {

    }

    public static APIInterface getServicesMethods() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(5, TimeUnit.MINUTES);
        mOkHttpClient.setReadTimeout(5, TimeUnit.MINUTES);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).setClient(new OkClient(mOkHttpClient)).build();
        restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        APIInterface apiInterface;apiInterface = restAdapter.create(APIInterface.class);
        return apiInterface;
    }
}
