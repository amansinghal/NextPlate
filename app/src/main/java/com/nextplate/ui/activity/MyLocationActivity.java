package com.nextplate.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nextplate.R;
import com.nextplate.core.activity.BaseActivity;
import com.nextplate.core.preference.Prefrences;
import com.nextplate.core.util.GPSTracker;
import com.nextplate.core.util.Utility;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
/**
 * Created by aman on 8/8/16.
 */
public class MyLocationActivity extends BaseActivity
{
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 0x01;
    @Bind(R.id.activity_my_location_btn_loc)
    Button btnMyLoc;
    @Bind(R.id.activity_my_location_iv_main)
    ImageView ivMain;
    private String cityName;

    @Override
    public void onCreateCustom()
    {
        setTitle("MyPlate");
        int height = Utility.getDeviceWH(this)[1];
        height = height / 2;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ivMain.getLayoutParams();
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN)
        {
            layoutParams.height = (height + 80);
        }
        else
        {
            layoutParams.height = height;
        }
        ivMain.setLayoutParams(layoutParams);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @OnClick(R.id.activity_my_location_btn_loc)
    public void checkPermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {

            // Should we show an explanation?
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
            else
            {

                // No explanation needed, we can request the permission.

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else
        {
            startLocationService();
        }
    }

    @OnClick(R.id.activity_my_location_btn_next)
    protected void onClickNext()
    {
        if(TextUtils.isEmpty(cityName))
        {
            Toast.makeText(MyLocationActivity.this, "Please select cyour current location.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull
                                           String[] permissions,
                                           @NonNull
                                           int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS_REQUEST_LOCATION)
        {
            for(int i = 0; i < permissions.length; i++)
            {
                String permission = permissions[i];
                int grantResult = grantResults[i];
                if(permission.equals(Manifest.permission.ACCESS_FINE_LOCATION))
                {
                    if(grantResult == PackageManager.PERMISSION_GRANTED)
                    {
                        startLocationService();
                    }
                    else
                    {
                        Utility.showDialog(this, "Without location permission we are not able to locate you please enable.");
                    }
                }
            }
        }
    }

    private void startLocationService()
    {
        final GPSTracker gpsTracker = new GPSTracker(this);
        gpsTracker.getLocation(new LocationListener()
        {
            @Override
            public void onLocationChanged(Location location)
            {
                gpsTracker.stopUsingGPS();
                getCity(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras)
            {

            }

            @Override
            public void onProviderEnabled(String provider)
            {

            }

            @Override
            public void onProviderDisabled(String provider)
            {

            }
        }, false);
    }

    private void getCity(final Location location)
    {
        showProgress();
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Geocoder gcd = new Geocoder(MyLocationActivity.this, Locale.getDefault());
                    List<Address> addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    cityName = addresses.get(0).getLocality();
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            hideProgress();
                            btnMyLoc.setText(cityName);
                            Prefrences.getWriteInstance(MyLocationActivity.this).putString(Prefrences.Key.KEY_CITY, cityName.toLowerCase()).commit();
                        }
                    });
                }
                catch(Exception e)
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            hideProgress();
                            Utility.showDialog(MyLocationActivity.this, "Unable to fetch your location");
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public int getActivityContentView()
    {
        return R.layout.activity_my_location;
    }
}
