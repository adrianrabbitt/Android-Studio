package com.example.adria.persontracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class AddUser extends AppCompatActivity {
    Button adduser;
    Button getDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ClickOnAddUser();
        ClickUserDetails();
    }

    public void ClickOnAddUser() {
        adduser = (Button) findViewById(R.id.adduser);
        adduser.setOnClickListener(new View.OnClickListener() {
                                       public void onClick(View v) {
                                           Intent intent = new Intent(AddUser.this, register.class);
                                           startActivity(intent);
                                       }

                                   }

        );
    }

    public void ClickUserDetails() {
        getDetails = (Button) findViewById(R.id.button2);
        getDetails.setOnClickListener(new View.OnClickListener() {
                                          public void onClick(View v) {
                                              Intent intent = new Intent(AddUser.this, GetPersonal.class);
                                              startActivity(intent);
                                          }

                                      }

        );
    }

}