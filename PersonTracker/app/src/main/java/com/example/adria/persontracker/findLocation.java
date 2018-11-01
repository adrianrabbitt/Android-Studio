package com.example.adria.persontracker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;


public class findLocation extends Service implements LocationListener { //implementing the LocationListner vairable.

    public Context currentState;  //state identifier.
    double lat;                   //initailizing vairibles.
    double lng;
    Location currentlocation;   //Location vairable for current state.
    LocationManager newLocation; //Location Manager vairable(receving location updates).



    public void getNewLocationManager() { //function to get the new Location
        newLocation = (LocationManager) currentState.getSystemService(LOCATION_SERVICE);
    }



    public void getLocationUpdates() {   //registering for location updates of the location manager
        newLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, this);
    }


    public void getLastLocation() {   //getting the last location recorded and assining it to the current location.
        currentlocation = newLocation.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }



    public findLocation(Context newState) {  // constructor to what class is using the findlocation objects.
        this.currentState = newState;  //the current state the program is running.
        try {

            getNewLocationManager();
            getLocationUpdates();      //calling the location functions.
            getLastLocation();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


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


    @Override
    public void onLocationChanged(Location location) { // overide functions that were updated atomatically.
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
