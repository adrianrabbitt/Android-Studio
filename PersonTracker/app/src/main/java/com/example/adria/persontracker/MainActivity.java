package com.example.adria.persontracker;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView Parent;
    private ImageView Client;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OnClickParent();
        OnClickClient();
    }

    public void OnClickParent()
    {
        Parent = (ImageView) findViewById(R.id.admin);
        Parent.setOnClickListener(new View.OnClickListener()
        {
                    public void onClick(View v)
                    {

                        Intent intent = new Intent(MainActivity.this,Login.class);
                        startActivity(intent);
                    }

        }
        );
    }

    public void OnClickClient()
    {
       Client = (ImageView) findViewById(R.id.user);
       Client.setOnClickListener(new View.OnClickListener()
               {
                   public void onClick(View v)
                   {
                       Intent intent = new Intent(MainActivity.this,Loginclient.class);
                       startActivity(intent);
                   }

               }
        );
   }

}
