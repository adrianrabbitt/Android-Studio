package com.example.adrian.firebase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Settings extends AppCompatActivity {
    TextView seekBarV;
    TextView seekBarA;
    String userId;
    ImageView back;
    private DatabaseReference myRef;
    int Value;
    int age;
    SharedPreferences sharedPref;
    int cValue;
    SharedPreferences.Editor editor;
    int dValue;
    Switch sw;
    Switch sw2;
    GPSTracker gps;
    String Sex;
    TextView searchLocation;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        myRef = database.getReference();
        getSupportActionBar().hide();
        Sex = "Male";
        // varibles for shared preference 
        String key = FirebaseAuth.getInstance()
                .getCurrentUser().getUid().toString() + "dis";
        final String keyAge = FirebaseAuth.getInstance()
                .getCurrentUser().getUid().toString() + "age";
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String keySexM = FirebaseAuth.getInstance()
                .getCurrentUser().getUid().toString() + "sexM";
        final String keySexF = FirebaseAuth.getInstance()
                .getCurrentUser().getUid().toString() + "sexF";

        searchLocation = (TextView) findViewById(R.id.searchLocation);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        seekBarV = (TextView) findViewById(R.id.value);
        seekBarA = (TextView) findViewById(R.id.age);

        back = (ImageView) findViewById(R.id.back);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        SeekBar seekBar2 = (SeekBar) findViewById(R.id.seekBar2);

        sharedPref = Settings.this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        if (sharedPref.getString(FirebaseAuth.getInstance()
                .getCurrentUser().getUid().toString() + "location", "s") != null) {
            searchLocation.setText(sharedPref.getString(FirebaseAuth.getInstance()
                    .getCurrentUser().getUid().toString() + "location", "s"));
        }
		
		//writing to shared preference
        editor = sharedPref.edit();
        final CardView cardView = (CardView) findViewById(R.id.logout);
        cardView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            FirebaseAuth.getInstance().signOut();
                                            final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
                                            cardView.startAnimation(animShake);
                                            Intent intent = new Intent(Settings.this, LoginEmployee.class);
                                            startActivity(intent);


                                        }

                                    }
        );
        final CardView cardViewLocation = (CardView) findViewById(R.id.location);
        cardViewLocation.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    Intent intent = new Intent(Settings.this, LoginEmployer.class);
                                                    gps = new GPSTracker(getApplicationContext());
                                                    LayoutInflater layoutInflater = LayoutInflater.from(Settings.this);
                                                    View promptView = layoutInflater.inflate(R.layout.location, null);
                                                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Settings.this);
                                                    alertDialogBuilder.setView(promptView);
                                                    final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
                                                    cardViewLocation.startAnimation(animShake);
                                                    final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
                                                    editText.setText(sharedPref.getString(FirebaseAuth.getInstance()
                                                            .getCurrentUser().getUid().toString() + "location", "s"));
                                                    final TextView currLoc = (TextView) promptView.findViewById(R.id.searchLocation);
                                                    // setup a dialog window
                                                    currLoc.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            alertDialogBuilder.setCancelable(false);

                                                            editor.putString(FirebaseAuth.getInstance()
                                                                    .getCurrentUser().getUid().toString() + "location", "My Current Location");
                                                            editor.commit();
                                                            editText.setText(sharedPref.getString(FirebaseAuth.getInstance()
                                                                    .getCurrentUser().getUid().toString() + "location", "s"));


                                                            Toast.makeText(Settings.this, " " + sharedPref.getString(FirebaseAuth.getInstance()
                                                                    .getCurrentUser().getUid().toString() + "location", "s"), Toast.LENGTH_SHORT).show();

                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    dialog.cancel();
                                                                }
                                                            };
                                                        }
                                                    });


                                                    alertDialogBuilder.setCancelable(false)
                                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {


                                                                    LatLng loc = gps.getLocationFromAddress(getApplicationContext(), editText.getText().toString());
                                                                    double lat = 0.0;
                                                                    double lng = 0.0;
                                                                    if (loc != null && !editText.getText().toString().equals("My Current Location")) {
                                                                        lat = loc.latitude;
                                                                        lng = loc.longitude;


                                                                    }


                                                                    editor.putFloat(FirebaseAuth.getInstance()
                                                                            .getCurrentUser().getUid().toString() + "lat", (float) lat);
                                                                    editor.putFloat(FirebaseAuth.getInstance()
                                                                            .getCurrentUser().getUid().toString() + "lng", (float) lng);
                                                                    editor.putString(FirebaseAuth.getInstance()
                                                                            .getCurrentUser().getUid().toString() + "location", editText.getText().toString());
                                                                    editor.commit();

                                                                    if (sharedPref.getString(FirebaseAuth.getInstance()
                                                                            .getCurrentUser().getUid().toString() + "location", "s") != null) {
                                                                        searchLocation.setText(sharedPref.getString(FirebaseAuth.getInstance()
                                                                                .getCurrentUser().getUid().toString() + "location", "s"));
                                                                    }


                                                                }
                                                            })
                                                            .setNegativeButton("Cancel",
                                                                    new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int id) {
                                                                            dialog.cancel();
                                                                        }
                                                                    });

                                                    // create an alert dialog
                                                    AlertDialog alert = alertDialogBuilder.create();
                                                    alert.show();


                                                }

                                            }
        );


        seekBar.setProgress(sharedPref.getInt(key, 0));
        seekBar2.setProgress(sharedPref.getInt(keyAge, 0));
        sw = (Switch) findViewById(R.id.switch1);
        sw2 = (Switch) findViewById(R.id.switch2);

        if (sharedPref.getString(keySexM, "a").equals("Male")) {
            sw.setChecked(true);
        } else {
            sw.setChecked(false);
        }
        if (sharedPref.getString(keySexF, "a").equals("Female")) {
            sw2.setChecked(true);
        } else {
            sw2.setChecked(false);
        }


        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String val = null;
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplication());
                SharedPreferences.Editor e = settings.edit();
                if (sw.isChecked()) {
                    //Do something when Switch button is on/checked
                    Sex = "Male";

                    SharedPreferences.Editor editor = sharedPref.edit();

                    editor.putString(keySexM, Sex);
                    editor.commit();
                    //admin = new Admin(Integer.toString(seekage),dValue,Sex);
                    //myRef.child("Admin").child(userId).child("Info").setValue(admin);
                } else {
                    Sex = "null";
                    //admin = new Admin(Integer.toString(seekage),dValue,Sex);
                    // myRef.child("Admin").child(userId).child("Info").setValue(admin);
                    editor.putString(keySexM, Sex);
                    editor.commit();
                }


//                Student p = new Student(val);
                myRef.child("Users").child(FirebaseAuth.getInstance()
                        .getCurrentUser().getUid().toString()).child("Info").child("sho").setValue(val);
            }
        });

        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String val = null;
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplication());
                SharedPreferences.Editor e = settings.edit();
                if (sw2.isChecked()) {
                    //Do something when Switch button is on/checked
                    Sex = "Female";

                    SharedPreferences.Editor editor = sharedPref.edit();

                    editor.putString(keySexF, Sex);
                    editor.commit();
                } else {
                    Sex = "null";
                    editor.putString(keySexF, Sex);
                    editor.commit();
                }


//                Student p = new Student(val);
                myRef.child("Users").child(FirebaseAuth.getInstance()
                        .getCurrentUser().getUid().toString()).child("Info").child("sho").setValue(val);
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarV.setText(String.valueOf(progress) + "KM");
                Value = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                dValue = Value;
                SharedPreferences.Editor editor = sharedPref.edit();
                String key = FirebaseAuth.getInstance()
                        .getCurrentUser().getUid().toString() + "dis";
                editor.putInt(key, dValue);
                editor.commit();
                //admin = new Admin(Integer.toString(seekage),dValue,Sex);
                // myRef.child("Admin").child(userId).child("Info").setValue(admin);


            }
        });
		//when back button is pressed bring to home page
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, Employer.class);
                startActivity(intent);

            }
        });
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarA.setText(String.valueOf(progress) + "Yrs");
                age = progress;


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                cValue = age;
                //admin = new Admin(cValue,Integer.toString(seekdist),Sex);
                //myRef.child("Admin").child(userId).child("Info").setValue(admin);
                SharedPreferences.Editor editor = sharedPref.edit();
                String key = FirebaseAuth.getInstance()
                        .getCurrentUser().getUid().toString() + "dis";
                editor.putInt(keyAge, cValue);
                editor.commit();


            }
        });


    }
    //Show input dialog
    protected void showInputDialog() {

        // get prompts.xml view
        gps = new GPSTracker(getApplicationContext());
        LayoutInflater layoutInflater = LayoutInflater.from(Settings.this);
        View promptView = layoutInflater.inflate(R.layout.location, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Settings.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        editText.getText().toString();
                        LatLng loc = gps.getLocationFromAddress(getApplicationContext(), editText.getText().toString());
                        double lat = loc.latitude;
                        double lng = loc.longitude;

                        SharedPreferences.Editor editor = sharedPref.edit();

                        editor.putFloat(FirebaseAuth.getInstance()
                                .getCurrentUser().getUid().toString() + "lat", (float) lat);
                        editor.putFloat(FirebaseAuth.getInstance()
                                .getCurrentUser().getUid().toString() + "lng", (float) lng);
                        editor.commit();


                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


}
