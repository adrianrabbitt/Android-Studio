package com.example.adrian.firebase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Main2Activity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    String newString;
    ImageView image;
    double harsine;
    GPSTracker gps;
    double distance;
    TextView Name;
    private TextView dis;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();
        image = (ImageView) findViewById(R.id.img2);
        Name = (TextView) findViewById(R.id.name);
        dis = (TextView) findViewById(R.id.distance);
        Bundle extras = getIntent().getExtras();
        getSupportActionBar().hide();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, MapsActivity.class);
                finish();

            }
        });

        newString = extras.getString(Intent.EXTRA_TEXT);

       //Establish reference to firebase storage
        StorageReference storageRef = storage.getReferenceFromUrl("gs://fir-e6d19.appspot.com").child(newString).child("Profile.jpg");
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor e = settings.edit();
        e.putString("ProfileUser", newString);
        e.commit();

 
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        //Setting Up View Pager.
        setupViewPager(viewPager);
        viewPager.setCurrentItem(1);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        myRef = database.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.e("Count", "" + dataSnapshot.getChildrenCount());
                Student s = dataSnapshot.child("Users").child(newString).child("Info").getValue(Student.class);
                ;

                double d = getdistance(s.getlat(), s.getlng());
                double rounded = Math.round((d) * 100.0) / 100.0;
                dis.setText("less than" + " " + rounded + " km away");
                // Name.setText(" " + s.getfName());
                Name.setText(s.getfName() + "," + getAge(s.getYear(), s.getMonth(), s.getDay()));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Getting Image From Database.

        try {
            final File localFile = File.createTempFile("image", "jpg");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {


                    Glide.with(getApplicationContext()).load(localFile).asBitmap().centerCrop().into(new BitmapImageViewTarget(image) {
                        @Override
                        protected void setResource(Bitmap resource) {


                            image.setImageBitmap(resource);
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });
        } catch (IOException s) {
        }
    }

   //Set up page viewer for tabs
    private void setupViewPager(ViewPager viewPager) {
        Bundle bundle = new Bundle();
        bundle.putString("r", newString);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //add each tab to the adapter
        adapter.addFragment(new Tab1Fragment(), "Certs");
        adapter.addFragment(new Tab2Fragment(), "Profile");
        adapter.addFragment(new Tab3Fragment(), "Ratings");

        viewPager.setAdapter(adapter);
    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

	
	//calculate age by date of birth
    private int getAge(int year, int month, int day) {
        //calculating age from dob
        Calendar cal = Calendar.getInstance();
        Calendar current = Calendar.getInstance();
        cal.set(year, month, day);
        int age = current.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
        if (current.get(Calendar.DAY_OF_YEAR) < cal.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }


    //HaverSine Formula
    public double getdistance(double lat, double lng)    
    {
        gps = new GPSTracker(Main2Activity.this);    
        harsine =
                (

                        Math.sin(Math.toRadians(lat - gps.getLatitude())) * Math.sin(Math.toRadians(lat - gps.getLatitude()))
                                + Math.cos(Math.toRadians(gps.getLatitude())) * Math.cos(Math.toRadians(lat)) * (Math.sin(Math.toRadians(lng - gps.getLongitude())) * Math.sin(Math.toRadians(lat - gps.getLongitude())))

                );

        distance = Math.abs((2 * 6371) * Math.asin(harsine));
        distance = distance * 10;

        return distance;
    }

}