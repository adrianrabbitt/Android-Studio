package com.example.adria.persontracker;
import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.mock.MockPackageManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;
import java.util.Locale;





public class Parent extends AppCompatActivity {
    Button getLocation; // Initalizing buttons
    Button getAdress;
    Button getHistory;
    findLocation currLoc;  //Instance of findLocation class.
    DistanceControl control;
    double Distance;
    double distance;
    double harsine;
    private static final int REQUEST_CODE_PERMISSION = 2;   // permissions for location
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);   // setting permissions on phone
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {
                // setting the location permission
                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        control = new DistanceControl();   // creating instance of DistanceControl to get value from the seek bar.
        ClickOnShowLocation();  // calling the buttons
        ClickOnGetAddress();
        ClickOnAdmin();
        ClickOnHistory();
        setDistance();                   // calling the functions



    }



    /*
      Here are all the functions used for the main method, override functions are already supplied
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Intent intent = new Intent(Parent.this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_changePassword) {
            Intent intent = new Intent(Parent.this, password.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_changedistance) {
            Intent intent = new Intent(Parent.this, DistanceControl.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_background) {
            Intent i = new Intent(Parent.this,RecieveService.class);
            startService(i);
            return true;
        }
        if (id == R.id.action_backgroundf) {
            Intent i = new Intent(Parent.this,RecieveService.class);
            stopService(i);
            return true;
        }

        if (id == R.id.action_update) {
            Intent intent = new Intent(Parent.this, Update.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

// above is dealing with toolbar.


    public void ClickOnShowLocation() {
        getLocation = (Button) findViewById(R.id.getlocation);
        getLocation.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View v) {
                                               showcurrentLocationMap();
                                           }

                                       }

        );
    }


    public void ClickOnAdmin() {
        getLocation = (Button) findViewById(R.id.admin);
        getLocation.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View v) {


                                               Intent intent = new Intent(Parent.this, AddUser.class);
                                               startActivity(intent);
                                           }

                                       }

        );
    }

    public void ClickOnHistory() {
        getHistory = (Button) findViewById(R.id.history);
        getHistory.setOnClickListener(new View.OnClickListener() {
                                          public void onClick(View v) {

                                              Intent intent = new Intent(Parent.this, History.class);
                                              startActivity(intent);
                                          }

                                      }

        );
    }


    String add;
    GetLocationActivity coord;

    public void ClickOnGetAddress() {

        getAdress = (Button) findViewById(R.id.getAdress);
        getAdress.setOnClickListener(new View.OnClickListener() {
                                         public void onClick(View v) {
                                             coord = new GetLocationActivity();
                                             coord.execute();


                                             if (coord.lat == 0) // was returning 0 if the permissions for internet in menifast were not set.
                                             {
                                                 Toast.makeText(getApplicationContext(), "check permissions", Toast.LENGTH_LONG).show();
                                             } else {
                                                 if (isOutOfrange(coord.lat, coord.lng) && control.isDistanceSet == true) {    // hardcoded values waiting on coordinates from the server
                                                     showNotification();  // dispaly notification to phone.
                                                 }

                                                 add = getadress(coord.lat, coord.lng);
                                                 Toast.makeText(getApplicationContext(), add, Toast.LENGTH_LONG).show();

                                             }
                                         }


                                     }

        );

    }


// functions for getting current adresses.

    public void showcurrentLocationMap() {
        Uri.Builder parm = new Uri.Builder();   // to connect to google maps to display location.
        parm.scheme("geo").path("0,0");
        Uri a = parm.build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(a);

        startActivity(intent);

    }


    String address, county, country;
    String str;
    Geocoder geoAddr;

    public String getadress(double lat, double lng) {
        geoAddr = new Geocoder(this, Locale.getDefault());
        try {

            List<Address> addresses = geoAddr.getFromLocation(lat, lng, 5); // getting the list of aadresses from longitude and latuide coordinates.
            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            county = addresses.get(0).getLocality(); // getting the county e.g dublin
            country = addresses.get(0).getAdminArea();
            str = "Adress: \n" + address + "\n" + county + "\n" + country; // concatanating into string.

        } catch (IOException e) {

            e.printStackTrace();

        }
        return str;
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
        currLoc = new findLocation(Parent.this);    // currLoc = current location
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
        if (distance > Distance) {
            return true;
        } else {
            return false;
        }

    }


}
