package com.example.adrian.firebase;

/**
 * Created by Adrian on 31/01/2018.
 */

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;



public class GetLocation extends Service implements LocationListener {


    public Context currentState;   //state identifier.
    double lat;                    //initialising vairibles.
    double lng;
    Location currentlocation;     //Location variable for current state.
    LocationManager newLocation;  //Location Manager variable(receiving location updates).



    public void getNewLocationManager() {
        newLocation = (LocationManager) currentState.getSystemService(LOCATION_SERVICE);
    }


    public void getLocationUpdates() {   //registering for location updates of the location manager
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        newLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, this);
    }


    public void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        currentlocation = newLocation.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }


    public GetLocation(Context newState) {  // constructor to what class is using the findlocation objects.
        this.currentState = newState;        //the current state the program is running.
        try {

            getNewLocationManager();
            getLocationUpdates();      //calling the location functions.
            getLastLocation();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /*
        getLatitude will return the current latitude and longitude values, pulled from the current location
        The functions will first cheack if the location is null and if other wise it will assign the double values.
     */
    public double getLatitude() {
        if (currentlocation != null)
            lat = currentlocation.getLatitude(); // functions that are accessed by classes.

        return lat;
    }


    public double getLongitude() {
        if (currentlocation != null)
            lng = currentlocation.getLongitude(); //functions that are accessed by classes.
        return lng;
    }

    // overide functions that were updated automatically.
    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
