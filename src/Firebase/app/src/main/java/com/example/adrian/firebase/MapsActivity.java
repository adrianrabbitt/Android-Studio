package com.example.adrian.firebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker Syd;

    GPSTracker gps;
    double harsine;
    double distance;
    double lat;
    double lng;
    Vairables var;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    Marker marker;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    List<Student> studentList;
    List<String> uniqueID;
    List<LatLng> li;
    Handler handler;
    Map<String, String> mMarkerMap;
    Map<Marker, String> PositionMap;
    LatLng location;
    String key;
    double lat1;
    BitmapDrawable bitmapdraw;
    double lng1;
    String boolMale;
    String boolFemale;
    String refJob;
    boolean isEnabled;
    SharedPreferences sharedPref;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = android.Manifest.permission.ACCESS_FINE_LOCATION;

    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        var = new Vairables();
        key = FirebaseAuth.getInstance()
                .getCurrentUser().getUid().toString() + "dis";
        final String keyAge = FirebaseAuth.getInstance()
                .getCurrentUser().getUid().toString() + "age";

        final String keySexM = FirebaseAuth.getInstance()
                .getCurrentUser().getUid().toString() + "sexM";
        final String keySexF = FirebaseAuth.getInstance()
                .getCurrentUser().getUid().toString() + "sexF";

        // initisialsing shared preference 
        sharedPref = MapsActivity.this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        //retrieve values from shared preferences 
        boolMale = sharedPref.getString(keySexM, "a");
        boolFemale = sharedPref.getString(keySexF, "a");


        var.name = new String("hello");
        studentList = new ArrayList<>();
        li = new ArrayList<>();
        uniqueID = new ArrayList<>();
        sharedPref = MapsActivity.this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        handler = new Handler();
        mMarkerMap = new HashMap<>();

        Bundle extras = getIntent().getExtras();
        refJob = extras.getString(Intent.EXTRA_TEXT);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        GPSTracker gps = new GPSTracker(MapsActivity.this);

        double gpslat = gps.getLatitude();
        double gpslng = gps.getLongitude();
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, Employer.class);
                startActivity(intent);

            }
        });
        double preflat = (double) sharedPref.getFloat(FirebaseAuth.getInstance()
                .getCurrentUser().getUid().toString() + "lat", 1);
        double prelng = (double) sharedPref.getFloat(FirebaseAuth.getInstance()
                .getCurrentUser().getUid().toString() + "lng", 1);
        double lat;
        double lng;
        if (preflat != 0.0) {
            lat = preflat;
            lng = prelng;
        } else {
            lat = gpslat;
            lng = gpslng;
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 10.0f));
        myRef = database.getReference().child("Users");
		
		//Setting Event Listner to Employees data

        myRef.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    Student st = s.child("Info").getValue(Student.class);
                    //ShowProfile p = s.child("Profile").getValue(ShowProfile.class);
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplication());
                    SharedPreferences.Editor e = settings.edit();

                    //Filtering through Employees based on settings
                    if (boolMale.equals("Male") && boolFemale.equals("Female") || boolFemale == null || boolMale == null) {
                        if (st.getJob().equals(refJob) && getdistance(st.getlat(), st.getlng()) < sharedPref.getInt(key, 0) && s.child("Info").child("sho").getValue().equals("true")) {

                            studentList.add(st);
                            uniqueID.add(s.getKey());

                            mMarkerMap.put(st.getfName(), s.getKey());
                        }
                    } else if (boolMale.equals("Male") && boolFemale.equals("null")) {
                        if (st.getJob().equals(refJob) && getdistance(st.getlat(), st.getlng()) < sharedPref.getInt(key, 0) && s.child("Info").child("sho").getValue().equals("true") && st.getSex().equals("Male")) {

                            studentList.add(st);
                            uniqueID.add(s.getKey());

                            mMarkerMap.put(st.getfName(), s.getKey());
                        }
                    } else if (boolMale.equals("null") && boolFemale.equals("Female")) {
                        if (st.getJob().equals(refJob) && getdistance(st.getlat(), st.getlng()) < sharedPref.getInt(key, 0) && s.child("Info").child("sho").getValue().equals("true") && st.getSex().equals("Female")) {

                            studentList.add(st);
                            uniqueID.add(s.getKey());

                            mMarkerMap.put(st.getfName(), s.getKey());
                        }
                    }


                }

                 //assigning emogies to male / female employees
                if (studentList.size() != 0) {
                    for (int i = 0; i < studentList.size(); i++) {
                        StorageReference storageRef = storage.getReferenceFromUrl("gs://fir-e6d19.appspot.com").child(mMarkerMap.get(studentList.get(i).getfName())).child("Profile.jpg");


                        lat1 = studentList.get(i).getlat();
                        lng1 = studentList.get(i).getlng();
                        location = new LatLng(lat1, lng1);

                        int height = 150;
                        int width = 150;
                        int boy[] = {R.drawable.boy, R.drawable.man, R.drawable.boy2};
                        int girl[] = {R.drawable.girl, R.drawable.girl2, R.drawable.girl3};
                        Random rand = new Random();
                        int index = rand.nextInt((boy.length - 1) - 0 + 1) + 0;
                        int index2 = rand.nextInt((girl.length - 1) - 0 + 1) + 0;


                        if (studentList.get(i).getSex().toString().equals("Male")) {
                            bitmapdraw = (BitmapDrawable) getResources().getDrawable(boy[index]);
                        } else {
                            bitmapdraw = (BitmapDrawable) getResources().getDrawable(girl[index2]);
                        }
                        Bitmap b = bitmapdraw.getBitmap();
                        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                        mMap.addMarker(new MarkerOptions().position(location).title(studentList.get(i).getfName()).snippet(uniqueID.get(i))).setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));


                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Jobs available in this Area", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mMap.setOnMarkerClickListener(this);


        // Add a marker in Sydney and move the camera


    }
        //If marker is clicked grab details from marker and send to next activity
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        String venueID = mMarkerMap.get(marker.getTitle());
        String unique = marker.getSnippet();
        Intent intent = new Intent(MapsActivity.this, Main2Activity.class);
        //  intent.putExtra(intent.EXTRA_TEXT, venueName);

        intent.putExtra(intent.EXTRA_TEXT, unique);
        startActivity(intent);



        return false;
    }


    //Haversine Formula
    public double getdistance(double lat, double lng)    
    {
        gps = new GPSTracker(MapsActivity.this);    
        harsine =
                (
                        //Harversine formula this will get the distance as the crow flys
                        //Values have to be putinto radians.
                        Math.sin(Math.toRadians(lat - gps.getLatitude())) * Math.sin(Math.toRadians(lat - gps.getLatitude()))
                                + Math.cos(Math.toRadians(gps.getLatitude())) * Math.cos(Math.toRadians(lat)) *
                                (Math.sin(Math.toRadians(lng - gps.getLongitude())) * Math.sin(Math.toRadians(lat - gps.getLongitude())))

                );

        distance = Math.abs((2 * 6371) * Math.asin(harsine));
        distance = distance * 10;

        return distance;
    }
}


