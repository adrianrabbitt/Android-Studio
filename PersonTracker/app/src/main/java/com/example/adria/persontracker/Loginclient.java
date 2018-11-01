package com.example.adria.persontracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Loginclient extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginclient);
        OnClickLogin();
    }
    private Button Client;
    private static EditText clientPassword;


    public void OnClickLogin()
    {
        Client = (Button) findViewById(R.id.button4);
        clientPassword= (EditText)findViewById(R.id.editText3);
        clientPassword.setText("");
        Client.setOnClickListener(new View.OnClickListener()
        {
               public void onClick(View v)
               {
                        if(clientPassword.getText().toString().equals("Client"))
                        {
                            Intent i = new Intent(Loginclient.this,SendService.class);
                            startService(i);
                            Toast.makeText(Loginclient.this,"Password is correct", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Loginclient.this,Client.class);
                            startActivity(intent);
                        }

                        else
                        {
                            Toast.makeText(Loginclient.this,"Password is invalid", Toast.LENGTH_SHORT).show();
                        }


               }

        }
        );
    }
}
