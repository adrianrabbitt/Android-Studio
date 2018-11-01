package com.example.adria.persontracker;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;

public class Update extends AppCompatActivity {
    public static  RadioButton one;
    public static  RadioButton five;
    public static  RadioButton ten;
    public static int updateValue;
    public static boolean isupdateValueSet = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ClickOnone();
        ClickOnfive();
        ClickOnten();
    }

    public void ClickOnone()
    {

        one = (RadioButton)findViewById(R.id.onem);
        one.setOnClickListener(new View.OnClickListener()
                               {
                                   public void onClick(View v)
                                   {
                                       isupdateValueSet =true;
                                       updateValue = 5;
                                       Intent intent = new Intent(Update.this,Parent.class);
                                       startActivity(intent);



                                   }



                               }

        );
    }

    public void ClickOnfive()
    {

        five = (RadioButton)findViewById(R.id.fivem);
        five.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        isupdateValueSet = true;
                        updateValue = 10;
                        Intent intent = new Intent(Update.this,Parent.class);
                        startActivity(intent);

                    }



                }

        );
    }

    public void ClickOnten()
    {

        ten = (RadioButton)findViewById(R.id.tenm);
        ten.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        isupdateValueSet = true;
                        updateValue = 15;
                        Intent intent = new Intent(Update.this,Parent.class);
                        startActivity(intent);

                    }



                }

        );
    }


}
