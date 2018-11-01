package com.example.adrian.firebase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.trncic.library.DottedProgressBar;

// Splash screen is displayed on creation of this activity
// This activity is called first on launch of the application
public class MainActivity extends Activity
{
	//thread for splash screen 
    private Thread splashTread;
    private Animation loadAnimation;
    private LinearLayout linearLayout;
    private ImageView imageView;
    private Intent intent;
    final String key = "id";
    private int waited = 0;
    private int totalWaitTime = 2000;
    private int sleepDuration = 100;
    SharedPreferences sharedPref;

  //Splash Screen
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        sharedPref = MainActivity.this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_main);
        DottedProgressBar progressBar = (DottedProgressBar) findViewById(R.id.progress);
        progressBar.startProgress();

        this.StartAnimations();
    }

    // Runs the splash screen animation
    private void StartAnimations()
    {



        this.loadAnimation = AnimationUtils.loadAnimation(this, R.anim.translate);
        this.loadAnimation.reset();

        this.imageView = (ImageView) findViewById(R.id.splash);


        this.splashTread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    while (waited < totalWaitTime)
                    {
                        sleep(100);
                        waited += 100;
                    }


                    if (FirebaseAuth.getInstance().getCurrentUser() != null ) {

                        if( sharedPref.getString(key,"a").equals("Employer"))
                        {
                            startActivity(new Intent(MainActivity.this, Employer.class));
                            finish();
                        }
                        else if( sharedPref.getString(key,"a").equals("Employee"))
                        {
                            startActivity(new Intent(MainActivity.this, Employee.class));
                            finish();
                        }


                    }
                    else {
                        intent = new Intent(MainActivity.this, Options.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                } catch (InterruptedException e) {}
                finally {
                    MainActivity.this.finish();
                }
            }
        };
        this.splashTread.start();
    }
}
//reference tuturial https://www.youtube.com/watch?v=-cg5jRLP05s&t=169s