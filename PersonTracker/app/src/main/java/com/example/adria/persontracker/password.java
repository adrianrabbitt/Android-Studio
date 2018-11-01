package com.example.adria.persontracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class password extends AppCompatActivity {
    Button change;
    EditText oldpasswd;
    EditText newpasswd;
    public String psswd = "Parent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        OnClickLogin();
    }




    public void OnClickLogin() {
        change = (Button) findViewById(R.id.change);
        oldpasswd = (EditText) findViewById(R.id.oldpassword);
        newpasswd = (EditText) findViewById(R.id.newpsswd);

        change.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (oldpasswd.getText().toString().equals(psswd)) // cheacking if the password is equal to Parent
                {

                    Intent intent = new Intent(password.this, Login.class);
                    setpsswd(newpasswd.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(password.this, "Password is invalid", Toast.LENGTH_SHORT).show(); //Toast created if false.
                }

            }

        });
    }


    public void setpsswd(String s) {

        this.psswd = s;
    }


}
