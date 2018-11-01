package com.example.adria.persontracker;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by adria on 2/28/2017.
 */

public class RecieveService extends Service {
    findLocation currLoc;
    GetLocationActivity coord;
    Parent p;
    public static boolean notify = false;
    int delay = 3000;
    int interval = 3000;
    public  double Distance;
    public  double distance;
    public  double harsine;
    DistanceControl control ;

    @Override
    public void onCreate() {
        super.onCreate();

        setDistance();
        new GetLocationActivity().execute();
      //  Toast.makeText(this, "is created "+ GetLocationActivity.lat, Toast.LENGTH_SHORT).show();
    }

    Timer timer = new Timer();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        currLoc = new findLocation(RecieveService.this);

        // p = new Parent();
        Toast.makeText(this, "Notifications Started", Toast.LENGTH_SHORT).show();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                new GetLocationActivity().execute();

                //new GetLocationActivity().execute();
                if (isOutOfrange(GetLocationActivity.lat, GetLocationActivity.lng) && control.isDistanceSet == true && GetLocationActivity.lat!=0.0) {
                    showNotification();
                    notify = true;
                }
                if (notify == true) {
                    timer.cancel();
                }
            }


        }, delay, interval);




        return super.onStartCommand(intent, flags, startId);

    }


    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    @Override
    public void onDestroy()
    {

        Toast.makeText(this, "Notifications Stopped.", Toast.LENGTH_SHORT).show();
    }



    public void showNotification() // show notification
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.logosplash);
        builder.setContentTitle("Person Tracker");
        builder.setContentText("The User is out of range");
        NotificationManager notificationmanager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationmanager.notify(0, builder.build());

    }

    public void setDistance()      // setting the distance from the seek bar
    {
        control = new DistanceControl();    // called from class Distance Control.
        Distance = control.value;

    }




    public boolean isOutOfrange(double lat, double lng)    // boolean function waiting for parms from server
    {
        currLoc = new findLocation(RecieveService.this);    // currLoc = current location
        harsine =
                (
                        //Harversine formula this will get the distance as the crow flys
                        //Values have to be putinto radians.
                        Math.sin(Math.toRadians(lat - currLoc.getLatitude())) * Math.sin(Math.toRadians(lat - currLoc.getLatitude()))
                                + Math.cos(Math.toRadians(currLoc.getLatitude())) * Math.cos(Math.toRadians(lat)) * (Math.sin(Math.toRadians(lng - currLoc.getLongitude())) * Math.sin(Math.toRadians(lat - currLoc.getLongitude())))

                );

        distance = (2 * 6371) * Math.asin(harsine);
        if (distance < 0) {
            distance = distance * -1;

        }
        if (distance > Distance & currLoc.lat !=0.0) {
            return true;
        } else {
            return false;
        }

    }

}


/**
 * Created by adria on 2/28/2017.
 */








