package com.nextplate.core.util;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.nextplate.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GPSTracker implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{

    private static final String TAG = "GPSTracker";
    private final Context mContext;

    //flag for GPS Status
    boolean isGPSEnabled = false;

    //flag for network status
    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;

    public Location location;
    double latitude;
    double longitude;
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 5 * 60 * 1000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; //10 metters
    private static final long MIN_TIME_BW_UPDATES = 0; // 1 minute
    protected LocationManager locationManager;
    public ProgressDialog dialog;
    private LocationListener locatioListener;
    protected LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    public GPSTracker(Context context)
    {
        this.mContext = context;
        buildGoogleApiClient();
    }

    public Location getLocation(LocationListener locationListener, boolean showProgress)
    {
        Location gpsLocation = null, networkLocation = null;
        this.locatioListener = locationListener;
        if(showProgress)
        {
            dialog = ProgressDialog.show(mContext, null, "Getting your current location..", false, false);
        }
        if(location != null)
        {
            locationListener.onLocationChanged(networkLocation);
        }
        return location;
    }

    protected synchronized void buildGoogleApiClient()
    {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(mContext).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
        createLocationRequest();
    }

    protected void createLocationRequest()
    {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(Bundle connectionHint)
    {
        Log.i(TAG, "Connected to GoogleApiClient");
        startLocationUpdates();
       /* location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(location != null)
        {
            locatioListener.onLocationChanged(location);
            updateGPSCoordinates();
        }*/
    }

    @Override
    public void onConnectionSuspended(int cause)
    {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result)
    {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    protected void startLocationUpdates()
    {
        if(mGoogleApiClient.isConnected())
        {
            if(ActivityCompat.checkSelfPermission(mContext,
                                                  Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
                    .checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locationListener);
    }

    com.google.android.gms.location.LocationListener locationListener = new com.google.android.gms.location.LocationListener()
    {
        @Override
        public void onLocationChanged(Location location)
        {
            GPSTracker.this.location = location;
            locatioListener.onLocationChanged(location);
            updateGPSCoordinates();
        }
    };

    public void updateGPSCoordinates()
    {
        if(location != null)
        {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            if(dialog != null && dialog.isShowing())
            {
                dialog.dismiss();
            }
            Log.e("Latitude", "" + latitude);
            Log.e("Longitude", "" + longitude);
        }
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */

    public void stopUsingGPS()
    {
        if(locationManager != null)
        {
            if(ActivityCompat.checkSelfPermission(mContext,
                                                  Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
                    .checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(GPSTracker.this.locatioListener);
        }
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected())
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,locationListener);
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Function to get latitude
     */
    public double getLatitude()
    {
        if(location != null)
        {
            latitude = location.getLatitude();
        }

        return latitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude()
    {
        if(location != null)
        {
            longitude = location.getLongitude();
        }

        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     */
    public boolean canGetLocation()
    {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     */
    public void showSettingsAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        //Setting Dialog Title
        alertDialog.setTitle("GPS Alert");

        //Setting Dialog Message
        alertDialog.setMessage("Please turn on the location services.");

        //On Pressing Setting button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        //On pressing cancel button
        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    /**
     * Get list of address by latitude and longitude
     *
     * @return null or List<Address>
     */
    public List<Address> getGeocoderAddress(Context context, Location location)
    {
        if(location != null)
        {
            Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
            try
            {
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                return addresses;
            }
            catch(IOException e)
            {
                //e.printStackTrace();
                Log.e("Error : Geocoder", "Impossible to connect to Geocoder", e);
            }
        }

        return null;
    }

    /**
     * Try to get AddressLine
     *
     * @return null or addressLine
     */
    public String getAddressLine(Context context)
    {
        List<Address> addresses = getGeocoderAddress(context, this.location);
        if(addresses != null && addresses.size() > 0)
        {
            Address address = addresses.get(0);
            String addressLine = address.getAddressLine(0);

            return addressLine;
        }
        else
        {
            return null;
        }
    }

    /**
     * Try to get Locality
     *
     * @return null or locality
     */
    public String getLocality(Context context)
    {
        List<Address> addresses = getGeocoderAddress(context, this.location);
        if(addresses != null && addresses.size() > 0)
        {
            Address address = addresses.get(0);
            String locality = address.getLocality();

            return locality;
        }
        else
        {
            return null;
        }
    }

    /**
     * Try to get Postal Code
     *
     * @return null or postalCode
     */
    public String getPostalCode(Context context)
    {
        List<Address> addresses = getGeocoderAddress(context, this.location);
        if(addresses != null && addresses.size() > 0)
        {
            Address address = addresses.get(0);
            String postalCode = address.getPostalCode();

            return postalCode;
        }
        else
        {
            return null;
        }
    }

    /**
     * Try to get CountryName
     *
     * @return null or postalCode
     */
    public String getCountryName(Context context)
    {
        List<Address> addresses = getGeocoderAddress(context, this.location);
        if(addresses != null && addresses.size() > 0)
        {
            Address address = addresses.get(0);
            String countryName = address.getCountryName();

            return countryName;
        }
        else
        {
            return null;
        }
    }
}
