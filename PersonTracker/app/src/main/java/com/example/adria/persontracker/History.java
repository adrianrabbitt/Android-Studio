package com.example.adria.persontracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class History extends AppCompatActivity {
    GetAddressActivity add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         add = new GetAddressActivity();
        add.execute();
        TextView addressHistory1;
        TextView addressHistory2;
        TextView addressHistory3;
        TextView addressHistory4;
        TextView addressHistory5;
        TextView addressHistory6;
        TextView addressHistory7;
        TextView addressHistory8;

        addressHistory1 = (TextView)findViewById(R.id.history1);
        addressHistory2 = (TextView)findViewById(R.id.history2);
        addressHistory3 = (TextView)findViewById(R.id.history3);
        addressHistory4 = (TextView)findViewById(R.id.history4);
        addressHistory5 = (TextView)findViewById(R.id.history5);
        addressHistory6 = (TextView)findViewById(R.id.history6);
        addressHistory7 = (TextView)findViewById(R.id.history7);
        addressHistory8 = (TextView)findViewById(R.id.history8);



        addressHistory1.setText(" " + add.oneh);
        addressHistory2.setText(" " + add.twoh);
        addressHistory3.setText(" " + add.threeh);
        addressHistory4.setText(" " + add.fourh);
        addressHistory5.setText(" " + add.fiveh);
        addressHistory6.setText(" " + add.sixh);
        addressHistory7.setText(" " + add.sevenh);
        addressHistory8.setText(" " + add.eighth);

       //Toast.makeText(History.this, " " + add.one, Toast.LENGTH_SHORT).show();

    }


}
