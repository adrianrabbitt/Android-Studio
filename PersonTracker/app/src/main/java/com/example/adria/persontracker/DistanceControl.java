package com.example.adria.persontracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class DistanceControl extends AppCompatActivity {

    private static SeekBar distancebar;
    private static TextView textview;
    public static RadioButton M;                   //initailizing vairables
    public static RadioButton km;
    public static boolean isDistanceSet = false;
    public static double value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_control);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        seekbar();                                 //calling functions.
        ClickOnM();
        ClickOnKm();
    }


    public void ClickOnM() {
        isDistanceSet = true;
        M = (RadioButton) findViewById(R.id.m);    //identifying radio buttons.
        M.setOnClickListener(new View.OnClickListener() {

                                 public void onClick(View v) {
                                     value = value / 1000;   //dividing by 1000 to convert to meters.
                                     Intent intent = new Intent(DistanceControl.this, Parent.class);
                                     startActivity(intent);


                                 }


                             }

        );
    }


    public void ClickOnKm() {                     //setting the distance to km
        isDistanceSet = true;
        km = (RadioButton) findViewById(R.id.km);
        km.setOnClickListener(new View.OnClickListener() {

                                  public void onClick(View v) {

                                      Intent intent = new Intent(DistanceControl.this, Parent.class);
                                      startActivity(intent);  //starting new activity

                                  }


                              }
        );
    }




    public void seekbar() {                 //seekbar function controls the distance.
        distancebar = (SeekBar) findViewById(R.id.distancebar);
        textview = (TextView) findViewById(R.id.distance);
        int x = distancebar.getProgress();
        textview.setText(" " + x);


        distancebar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int distancesvalue;

                    @Override


                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        distancesvalue = progress;                 // changing the progress value each time the seekbar changes.
                        textview.setText(" " + progress);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {


                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textview.setText(" " + distancesvalue);       //setting the default distance value, when the seekbar stops
                        value = (double) distancesvalue;
                    }

                }
        );

    }


}
