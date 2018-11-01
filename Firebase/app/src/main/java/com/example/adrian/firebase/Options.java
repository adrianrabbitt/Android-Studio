package com.example.adrian.firebase;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class Options extends AppCompatActivity {
    /*
        This is the main page when the program is executed giving choice for the Parent Login and the user Login.
     */
    private ImageView Parent;
    private ImageView Client;
    private ImageView loc;
    private ImageView wifi;
    private ImageView Logo;
    private Animation loadAnimation;
    private Animation loadAnimation2;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private TextView T1;
    private TextView T;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Parent = (ImageView) findViewById(R.id.Employer);
        Client = (ImageView) findViewById(R.id.Employee);
        Logo = (ImageView) findViewById(R.id.imageView);
        T1 = (TextView) findViewById(R.id.em);
        T = (TextView) findViewById(R.id.e);
        wifi = (ImageView) findViewById(R.id.wifi);
        loc = (ImageView) findViewById(R.id.location);

        int permissionCheck = ContextCompat.checkSelfPermission(Options.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // ask permissions here using below code
            ActivityCompat.requestPermissions(Options.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},3310);
        }
        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideout);
        Logo.startAnimation(animShake);
        final Animation animShakeP = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pulse);
        loc.startAnimation(animShakeP);

        wifi.startAnimation(animShakeP);
        this.loadAnimation = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        this.loadAnimation.reset();

        this.loadAnimation2 = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        this.loadAnimation2.reset();
        this.T1.clearAnimation();
        this.T1.startAnimation(this.loadAnimation);


        this.Parent.clearAnimation();
        this.Parent.startAnimation(this.loadAnimation);
        this.T.clearAnimation();
        this.T.startAnimation(this.loadAnimation);
        this.Client.clearAnimation();
        this.Client.startAnimation(this.loadAnimation);



        OnClickParent();
        OnClickClient();
    }


    public void OnClickParent() {

        Parent.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                          final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
                                          Parent.startAnimation(animShake);
                                          Intent intent = new Intent(Options.this, LoginEmployer.class);
                                          startActivity(intent);
                                      }

                                  }
        );
    }



    public void OnClickClient() {

        Client.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                          final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
                                          Client.startAnimation(animShake);
                                          Intent intent = new Intent(Options.this, LoginEmployee.class);
                                          startActivity(intent);
                                      }

                                  }
        );
    }

}
