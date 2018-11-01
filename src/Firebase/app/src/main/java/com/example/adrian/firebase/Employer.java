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
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class Employer extends AppCompatActivity {
    private Button Client;
    private GridLayout mainGrid;
    String[] test;
    int te;
    ImageView img;
    ImageView info;
    ImageView messages;
    String job;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        messages = (ImageView) findViewById(R.id.messages);
        setSupportActionBar(toolbar);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        OnClickSettings();
        OnClickInfo();
        OnClickMessages();
        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        SetUp obj = new SetUp(FirebaseInstanceId.getInstance().getToken().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail());
        myRef.child("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(obj);
        EmployerLayout(mainGrid);
        // OnClickClient();
    }

    //Establishing Grid View, Reference following this tuturial: https://www.youtube.com/watch?v=VUPM387qyrw&t=1s
    private void EmployerLayout(GridLayout mainGrid) {
        test = getResources().getStringArray(R.array.jobsarray);
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            final String t = test[i];
            //When clicked on any of the card views Bring to Activity along with Reference to the Job by Getting result from String Array
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {

                        cardView.setCardBackgroundColor(Color.parseColor("#FF6F00"));


                    } else {

                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

                    }

                    Intent intent = new Intent(Employer.this, MapsActivity.class);
                    intent.putExtra(intent.EXTRA_TEXT, t);
                    startActivity(intent);


                }
            });
        }
    }

	//When clicked on settings bring User to settings activity
    public void OnClickSettings() {

        img = (ImageView) findViewById(R.id.settings);
        img.setOnClickListener(new View.OnClickListener() {
                                   public void onClick(View v) {
                                       final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
                                       img.startAnimation(animShake);

                                       Intent intent = new Intent(Employer.this, Settings.class);
                                       startActivity(intent);
                                       overridePendingTransition(R.anim.slidein, R.anim.slideout);


                                   }

                               }
        );

    }

	//When Info is clicked bring to Information Activity(Containg information about application).
    public void OnClickInfo() {

        info = (ImageView) findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
                                        info.startAnimation(animShake);

                                        Intent intent = new Intent(Employer.this, Info.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slidein, R.anim.slideout);


                                    }

                                }
        );
    }

   //Bring the User to conversations When clicked.
    public void OnClickMessages() {


        messages.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
                                            messages.startAnimation(animShake);

                                            Intent intent = new Intent(Employer.this, Conversation.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.slidein, R.anim.slideout);


                                        }

                                    }
        );
    }
	
	
  //Disable Back button
    @Override
    public void onBackPressed() {

    }

}
