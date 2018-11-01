package com.example.adrian.firebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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
import java.util.Calendar;

public class Profile extends AppCompatActivity {
Button Parent;
TextView NewText;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private TextView Name;
    private TextView Email;
    private TextView Mobile;
    private TextView Address;
    private TextView Info;
    private TextView previous;
    private TextView Age;
    private TextView des;
    private TextView covername;
    private  TextView skill;
    private  TextView dis;
    Vairables var;
    public String ID;
    String newString;
    double harsine;
    GPSTracker gps;

    private ScrollView Scroll;
    double distance;
    ImageView image;
    private TextView age;
    ImageView mesage;
   String first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
                       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                image = (ImageView) findViewById(R.id.image);
                OnMessage();
                FirebaseStorage storage = FirebaseStorage.getInstance();
                Bundle extras = getIntent().getExtras();
                newString= extras.getString(Intent.EXTRA_TEXT);
                Toast.makeText(getApplicationContext(), " "+ newString , Toast.LENGTH_LONG).show();
                FloatingActionButton floatingActionButton=(FloatingActionButton) findViewById(R.id.fab);
                floatingActionButton.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view){
                        Intent intent = new Intent(Profile.this, ChatApplication.class);
                        Bundle extras = new Bundle();
                        extras.putString("U", newString);
                        extras.putString("F",first);
                        intent.putExtras(extras);
                        startActivity(intent);
                    }
                });




                StorageReference storageRef = storage.getReferenceFromUrl("gs://fir-e6d19.appspot.com").child(newString).child("Profile.jpg");
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                OnMessage();
                try {
                    final File localFile = File.createTempFile("image", "jpg");
                    storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            //Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            //  image.setImageBitmap(bitmap);
                            Toast.makeText(getApplicationContext(), "Please Complete Sections" , Toast.LENGTH_LONG).show();
                            Glide.with(Profile.this).load(localFile).asBitmap().centerCrop().into(new BitmapImageViewTarget(image) {
                                @Override
                                protected void setResource(Bitmap resource) {



                                    image.setImageBitmap(resource);
                                }
                            });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getApplicationContext(), "Please Complete Sections" , Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (IOException e ) {}

                //Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                //  mImageView.setImageBitmap(bitmap);

                myRef = database.getReference();
                Name = (TextView)findViewById(R.id.name);
                //covername = (TextView)findViewById(R.id.name2);
                // NewText = (TextView)findViewById(R.id.button);
                Email = (TextView)findViewById(R.id.email);
                //  Address = (TextView)findViewById(R.id.button);
                Mobile = (TextView)findViewById(R.id.number);
                Address=  (TextView)findViewById(R.id.address);
                Info = (TextView)findViewById(R.id.personal);
                Info.setMovementMethod(new ScrollingMovementMethod());
                previous = (TextView)findViewById(R.id.previous);
                skill = (TextView)findViewById(R.id.skills);
                Scroll = (ScrollView)findViewById(R.id.scroll);
                dis = (TextView)findViewById(R.id.distance);
                // age = (TextView)findViewById(R.id.age);
                previous.setMovementMethod(new ScrollingMovementMethod());


                Scroll.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                Info.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });


        previous.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

               previous.getParent().requestDisallowInterceptTouchEvent(false);

                return false;
            }
        });



        Info.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

              Info.getParent().requestDisallowInterceptTouchEvent(false);

                return false;
            }
        });


        //    var = new Vairables();









        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.e("Count",""+dataSnapshot.getChildrenCount());
                Student s = dataSnapshot.child("Users").child(newString).child("Info").getValue(Student.class);;

               double d = getdistance(s.getlat(),s.getlng());
               double rounded = Math.round((d) * 100.0) / 100.0;
             dis.setText("less than" +rounded+" km away");
            // Name.setText(" " + s.getfName());
             Name.setText(s.getfName()+","+getAge(s.getYear(),s.getMonth(),s.getDay()));
             Email.setText(s.getEmailID());
             Mobile.setText(s.getMobile());
             Address.setText(s.getIAddress1() + " " + s.getIAddress2());
             Info.setText(s.getInfo());
             previous.setText(s.getPtitle() + "\n"+"\n"+ "\u25CF" + s.getPdes());

                String skil = "\u25CF" + s.getSkill().replace("\n", "\n"+"\u25CF");
             skill.setText(skil);
            // Age.setText(s.getYear());
        first = s.getfName();

                //Address.setText(s.getjob());

                //Toast.makeText(getApplicationContext(), "Please Complete Sections" + s.getlat() + " "+ s.getlng(), Toast.LENGTH_LONG).show();

            }

            @Override
                public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public void OnClickParent() {
       /* Parent = (Button) findViewById(R.id.sign_out);
        Parent.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {

                                          Intent intent = new Intent(Profile.this, ChatApplication.class);
                                          startActivity(intent);
                                      }

                                  }
        );
        */
    }
    public void OnMessage() {
        mesage= (ImageView) findViewById(R.id.star);
        mesage.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {

                                          Intent intent = new Intent(Profile.this,Rating.class);
                                          Bundle extras = new Bundle();
                                          extras.putString("U", newString);
                                          extras.putString("F",first);
                                          intent.putExtras(extras);
                                          startActivity(intent);
                                      }

                                  }
        );

    }

    public double getdistance(double lat, double lng)    // boolean function waiting for parms from server
    {
        gps = new GPSTracker(Profile.this);    // currLoc = current location
        harsine =
                (
                        //Harversine formula this will get the distance as the crow flys
                        //Values have to be putinto radians.
                        Math.sin(Math.toRadians(lat - gps.getLatitude())) * Math.sin(Math.toRadians(lat - gps.getLatitude()))
                                + Math.cos(Math.toRadians(gps.getLatitude())) * Math.cos(Math.toRadians(lat)) * (Math.sin(Math.toRadians(lng - gps.getLongitude())) * Math.sin(Math.toRadians(lat - gps.getLongitude())))

                );

        distance = Math.abs((2 * 6371) * Math.asin(harsine));
       distance = distance *10;

        return distance;
    }

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


}
