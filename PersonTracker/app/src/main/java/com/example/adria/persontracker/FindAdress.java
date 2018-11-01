package com.example.adria.persontracker;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FindAdress extends AppCompatActivity {
    private Button FindAdress;
    private static EditText adress;
    String strvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_adress);
        ClickFindAdress();
    }



    public void ClickFindAdress() {
        FindAdress = (Button) findViewById(R.id.findadress);
        adress = (EditText) findViewById(R.id.adress);
        FindAdress.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        strvalue = adress.getText().toString();
                        showAdress(strvalue);


                    }

                }
        );
    }



    public void showAdress(String x) {
        Uri.Builder parm = new Uri.Builder();
        parm.scheme("geo").path("0,0").query(x);
        Uri a = parm.build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(a);
        startActivity(intent);

    }
}
