package com.example.adria.persontracker;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Client extends Activity {
    findLocation currLoc;
    Button getAdress;
    Button Personaldata;         // initializing varibles.
    Button getLocation;
    Button findAdress;
    String address, county, country;
    String str;
    Geocoder geoAddr;
    String add;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        OnClickLocation();
        OnClickAdress();         // calling button methods
        OnClickPersonal();
        OnClickSendCoordinates();


    }


    public void OnClickLocation() {
        getLocation = (Button) findViewById(R.id.getlocation2); //identifying buttons
        getAdress = (Button) findViewById(R.id.getAdress2);
        getLocation.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        ShowCurrentLocationMap();   //calling function to show current location on map.
                    }


                }

        );
    }



    public void OnClickAdress()
    {
        getAdress = (Button) findViewById(R.id.getAdress2);  //identifying buttons
        getAdress.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                currLoc = new findLocation(Client.this); //creating an instance of findLocation class of context Client.
                double lat = currLoc.getLatitude();   //getting the current latitude and longitude coordinates for Client.
                double lng = currLoc.getLongitude();
                add = getadress(lat, lng) + ".";
                if (lat == 0)  // if permissions for internet are not set latitude and longitude values will return 0.
                {
                    Toast.makeText(getApplicationContext(), " cheack permissions ", Toast.LENGTH_LONG).show();
                }
                else
                {

                    Toast.makeText(getApplicationContext(), add, Toast.LENGTH_LONG).show();//creating a toast too show the address
                    new SendAddress().execute(add); // executing the AsyncTask of send address with parms.
                }
            }


        }
        );
    }



    public void OnClickPersonal()
    {
        Personaldata = (Button) findViewById(R.id.personaldata); //indentifying button
        Personaldata.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(Client.this, personalinfo.class);  //creating an intent.
                        startActivity(intent); //starting the new activity

                    }


                }

        );
    }



    public void OnClickSendCoordinates()
    {
        findAdress = (Button) findViewById(R.id.findadress);  //indifying button
        findAdress.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currLoc = new findLocation(Client.this);
                String mobileNo = "08612345678";
                String latitude = new Double(currLoc.getLatitude()).toString() + "  "; // converting lat amd lng values to string to be stored in the database.
                String longitude = new Double(currLoc.getLongitude()).toString();

                Toast.makeText(getApplicationContext(), "Sending Location...", Toast.LENGTH_SHORT).show(); //creating a toast to indicate that the coordinates were sent.

                new SendLocationActivity().execute(mobileNo, latitude, longitude); //executing AsyncTask of SendLocationActivity
                Toast.makeText(getApplicationContext(), "Location sent ", Toast.LENGTH_LONG).show();//creating toast indicating success.
            }

        });
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




    public void ShowCurrentLocationMap()
    {
        Intent i = new Intent(Client.this,SendService.class);
        stopService(i);

        Uri.Builder parm = new Uri.Builder();  // using a Uri builder to manipulate the rui references.
        parm.scheme("geo").path("0,0");       // by setting the scheme to geo and path to coordinates 0 0 will get the current location.
       Uri a = parm.build();

        Intent intent = new Intent(Intent.ACTION_VIEW); //setting the action.
        intent.setData(a);
        startActivity(intent);



    }


}
