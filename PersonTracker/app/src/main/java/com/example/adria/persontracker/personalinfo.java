package com.example.adria.persontracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class personalinfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinfo);
        OnClickLocation();




    }
    EditText name;
    EditText address;
    EditText number;
    Button submit;
    String Pname;
    String Paddress;
    String Pnumber;
    public void OnClickLocation()
    {
        submit = (Button) findViewById(R.id.button5);
                 // showing current location on map.
        submit.setOnClickListener(new View.OnClickListener()
                                       {

                                           public void onClick(View v)
                                           {
                                               name = (EditText)findViewById(R.id.name);
                                               address = (EditText)findViewById(R.id.address);
                                               number = (EditText)findViewById(R.id.number);

                                               Pname = name.getText().toString()+ ".";
                                               Paddress = address.getText().toString()+ ".";
                                               Pnumber =  number.getText().toString()+ ".";
                                               Toast.makeText(getApplicationContext(),Pname+  " "+Paddress + " "+Pnumber, Toast.LENGTH_LONG).show();
                                               new SendPersonal().execute(Pname,Paddress,Pnumber);
                                               Intent intent = new Intent(personalinfo.this, Client.class);  // starting new activity
                                               startActivity(intent);


                                           }


                                       }

        );
    }

}
