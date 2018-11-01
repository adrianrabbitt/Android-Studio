package com.example.adrian.firebase;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmployerSettings extends AppCompatActivity {
    TextView seekBarV;
    TextView seekBarA;
    String userId;
    ImageView back;
    private DatabaseReference myRef;
    int Value;
    int age;
    SharedPreferences sharedPref;

    int dValue;
    private String seekdist;
    private String seekage;
    Switch sw;
    Switch sw2;
    String Sex;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Admin admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_settings);
        myRef = database.getReference();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        getSupportActionBar().hide();
        sw = (Switch) findViewById(R.id.show);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor e = settings.edit();
        Sex = "Male";

        //Creating Keys for Shared Preference's

        String key = FirebaseAuth.getInstance()
                .getCurrentUser().getUid().toString() + "dis";
        final String keyAge = FirebaseAuth.getInstance()
                .getCurrentUser().getUid().toString() + "age";

        //getting User Ids
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final String keySex = FirebaseAuth.getInstance()
                .getCurrentUser().getUid().toString() + "sex";
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        seekBarV = (TextView) findViewById(R.id.value);
        seekBarA = (TextView) findViewById(R.id.age);
        back = (ImageView) findViewById(R.id.back);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);


        sharedPref = EmployerSettings.this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        e.putString(FirebaseAuth.getInstance()
                .getCurrentUser().getUid().toString() + "switchshow", "true");
        e.commit();


        //Setting the Current Value For Switches via shared preference's

        if (settings.getString(FirebaseAuth.getInstance()
                .getCurrentUser().getUid().toString() + "switchshow", "a") == "true") {
            sw.setChecked(true);

        } else {
            sw.setChecked(false);
        }

        //Listining for a change in the switch.
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String val = null;
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplication());
                SharedPreferences.Editor e = settings.edit();
                if (sw.isChecked()) {

                    val = "true";
                    e.putString(FirebaseAuth.getInstance()
                            .getCurrentUser().getUid().toString() + "switchshow", val);
                    e.commit();
                } else {

                    val = "false";
                    e.putString(FirebaseAuth.getInstance()
                            .getCurrentUser().getUid().toString() + "switchshow", val);
                    e.commit();
                }



                myRef.child("Users").child(FirebaseAuth.getInstance()
                        .getCurrentUser().getUid().toString()).child("Info").child("sho").setValue(val);
            }
        });


        //Logging Out of Application when clicking on Card View .
        final CardView cardView = (CardView) findViewById(R.id.logout);
        cardView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            FirebaseAuth.getInstance().signOut();
                                            Intent intent = new Intent(EmployerSettings.this, LoginEmployer.class);
                                            final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
                                            cardView.startAnimation(animShake);
                                            startActivity(intent);


                                        }

                                    }
        );

        final CardView web = (CardView) findViewById(R.id.web);
        web.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                                            try {
                                                intent.setData(Uri.parse("http://www.student.computing.dcu.ie/~rabbita3/Jobder/"));
                                                startActivity(intent);
                                            } catch (ActivityNotFoundException exception) {

                                            }


                                        }

                                    }
        );





        seekBar.setProgress(sharedPref.getInt(key, 0));


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



            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployerSettings.this, Employee.class);
                startActivity(intent);

            }
        });


    }

}
