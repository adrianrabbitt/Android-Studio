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

public class GetPersonal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_personal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ClickOnAddUser();
    }

    public Button getInfo;
    public EditText getname;
    public String username;

   GetPersonalInfo i;
    public void ClickOnAddUser()
    {

        getInfo = (Button) findViewById(R.id.getInfo);
        getname = (EditText)findViewById(R.id.nameu);
        getInfo.setOnClickListener(new View.OnClickListener()
                                   {
                                       public void onClick(View v)
                                       {

                                           i = new GetPersonalInfo();
                                           username = getname.getText().toString()+ ".";
                                           i.execute(username);
                                           Intent intent = new Intent(GetPersonal.this,Parent.class);
                                           startActivity(intent);
                                           Toast.makeText(GetPersonal.this,i.numberP + "\n" + i.addressP , Toast.LENGTH_SHORT).show();

                                       }

                                   }

        );
    }

}
