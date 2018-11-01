package com.example.adrian.firebase;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class Employee extends AppCompatActivity {

    GridLayout mainGrid;
    CardView cardView4;
    ImageView img;
    ImageView info;
    CardView cardView2;
    CardView cardView;
    CardView cardView3;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("JOBDER");
        //Initialize
        SetUp obj = new SetUp(FirebaseInstanceId.getInstance().getToken(), FirebaseAuth.getInstance().getCurrentUser().getEmail());
        myRef.child("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(obj);
        EmployerLayout(mainGrid);
        OnClickSettings();
        OnClickInfo();

    }


    private void EmployerLayout(GridLayout mainGrid) {
        //CV Card View
        cardView = (CardView) mainGrid.getChildAt(0);
        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Employee.this, CV.class);
                final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
                cardView.startAnimation(animShake);
                cardView.setCardBackgroundColor(Color.parseColor("#FF6F00"));
                startActivity(intent);


            }
        });

        //Contacts Card View
        cardView2 = (CardView) mainGrid.getChildAt(1);

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Employee.this, Contacts.class);
                cardView2.setCardBackgroundColor(Color.parseColor("#FF6F00"));
                final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
                cardView2.startAnimation(animShake);
                startActivity(intent);


            }
        });

        //Conversation Card View
        cardView3 = (CardView) mainGrid.getChildAt(2);
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Employee.this, Conversation.class);

                cardView3.setCardBackgroundColor(Color.parseColor("#FF6F00"));
                final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
                cardView3.startAnimation(animShake);
                startActivity(intent);


            }
        });

        //Certificates Card View
        cardView4 = (CardView) mainGrid.getChildAt(3);
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Employee.this, Certificates.class);
                intent.putExtra("info", "This is activity from card item index  ");
                cardView4.setCardBackgroundColor(Color.parseColor("#FF6F00"));
                final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
                cardView4.startAnimation(animShake);
                startActivity(intent);


            }
        });
    }

    //When Clicked on Settings
    public void OnClickSettings() {

        img = (ImageView) findViewById(R.id.settings);
        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        img.startAnimation(animShake);
        img.setOnClickListener(new View.OnClickListener() {
                                   public void onClick(View v) {


                                       Intent intent = new Intent(Employee.this, EmployerSettings.class);
                                       startActivity(intent);
                                       overridePendingTransition(R.anim.slidein, R.anim.slideout);


                                   }

                               }
        );
    }


    //Info Page

    public void OnClickInfo() {

        info = (ImageView) findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {


                                        Intent intent = new Intent(Employee.this, Info.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slidein, R.anim.slideout);


                                    }

                                }
        );
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
