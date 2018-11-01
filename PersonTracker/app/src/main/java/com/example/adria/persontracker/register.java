package com.example.adria.persontracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        OnClickLogin();
    }





  findLocation f;
    Button register;
    EditText phonenumber;
    String addname;

    public void OnClickLogin()
    {
        register = (Button) findViewById(R.id.reg);


        register.setOnClickListener(new View.OnClickListener()
                                  {
                                      public void onClick(View v)
                                      {

                                       // cheacking if the password is equal to Parent
                                          {
                                              f = new findLocation(register.this);
                                              phonenumber= (EditText)findViewById(R.id.number);
                                              String latitude = new Double(f.getLatitude()).toString()+ " "; // converting lat amd lng values to string to be stored in the database.
                                              String longitude =  new Double(f.getLongitude()).toString() + " ";

                                              addname = phonenumber.getText().toString();
                                              new SendLocationActivity().execute(addname, latitude, longitude);


                                              Toast.makeText(register.this,"User Registered",Toast.LENGTH_SHORT).show();
                                              Intent intent = new Intent(register.this,Parent.class);

                                              startActivity(intent);
                                          }


                                      }

                                  }
        );
    }





}
