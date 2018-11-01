package com.example.adria.persontracker;
import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by adria on 2/28/2017.
 */

public class SendService extends Service {
    findLocation currLoc;
    int delay = 30000000;
    int interval = 30000000;
    public String address, county, country;
    public String str;
    Geocoder geoAddr;

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "is created", Toast.LENGTH_SHORT).show();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        currLoc = new findLocation(SendService.this);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                String mobile = "08612345678";
                String lat = new Double(currLoc.getLatitude()).toString() + "  "; // converting lat amd lng values to string to be stored in the database.
                String lng = new Double(currLoc.getLongitude()).toString();
                str = getadress(currLoc.getLatitude(),currLoc.getLongitude())+ ".";
                new SendLocationActivity().execute(mobile, lat, lng);
                if(str!=null)
                new SendAddress().execute(str);

            }
        }, delay, interval);


        return super.onStartCommand(intent,flags,startId);

    }




    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }




    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }


    public String getadress(double lat, double lng)
    {
        geoAddr = new Geocoder(this, Locale.getDefault()); //creating an instance of the Geocoder class.
        try {

            List<Address> addresses = geoAddr.getFromLocation(lat, lng, 5); //calling getFromLocation which returns a list of type addresss.
            address = addresses.get(0).getAddressLine(0); //returns the address at index 0;
            county = addresses.get(0).getLocality();     //returns the locaility.
            country = addresses.get(0).getAdminArea();   //returnns the admin area.
            str = "Adress: \n" + address + "\n" + county + "\n" + country; //appending results into 1 string.

        }
        catch (IOException e)
        {

            e.printStackTrace();

        }
        return str;
    }


}






/**
 * Created by adria on 2/28/2017.
 */








