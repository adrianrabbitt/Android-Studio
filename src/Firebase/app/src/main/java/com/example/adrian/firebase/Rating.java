package com.example.adrian.firebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Rating extends AppCompatActivity {
    ImageView image;String Name;
    ImageView image2;
    String newString;
    EditText input;
    private ChildEventListener eventListener;
    ListView listView;
    TextView title;
    Float ratingvalue;

    RatingsAdapter adapter;
    private TextView best_deals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        best_deals = (TextView) findViewById(R.id.message_userC);
        best_deals.setBackground(getResources().getDrawable(
                R.drawable.cir));
        FirebaseStorage storage = FirebaseStorage.getInstance();
        newString= extras.getString("U");
        Name = extras.getString("F");
        listView = (ListView)findViewById(R.id.list_of_ratings);
        List<Ratingc> rating = new ArrayList<>();


        title = (TextView) findViewById(R.id.toolbarT);


        adapter = new RatingsAdapter(this, R.layout.rate, rating);
        listView.setAdapter(adapter);
        attachDatabaseReadListener();

        input = (EditText)findViewById(R.id.edittext);
        final RatingBar mBar = (RatingBar) findViewById(R.id.rate);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        input = (EditText)findViewById(R.id.edittext);
        StorageReference storageRef = storage.getReferenceFromUrl("gs://fir-e6d19.appspot.com").child(newString).child("Profile.jpg");


        mBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                Float ratingVal = (Float) rating;
              ratingvalue = (Float) mBar.getRating();
                Toast.makeText(getApplicationContext(), " Ratings : " + String.valueOf(ratingVal) + "", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), " Ratings1 : " + ratingvalue + "", Toast.LENGTH_SHORT).show();
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                Toast.makeText(Rating.this, "hell0" + ratingvalue, Toast.LENGTH_SHORT).show();




                if(!input.getText().toString().isEmpty()) {
                    FirebaseDatabase.getInstance().getReference().child("Ratings").child(newString)
                            .push()
                            .setValue(new Ratingc(input.getText().toString(),input.getText().toString(), FirebaseAuth.getInstance()
                                    .getCurrentUser().getEmail(),ratingvalue)
                            );


                }







            }
        });


    }

    private void attachDatabaseReadListener(){
        if(eventListener == null){
            eventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Ratingc message = dataSnapshot.getValue(Ratingc.class);
                    adapter.add(message);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            FirebaseDatabase.getInstance().getReference().child("Ratings").child(newString).addChildEventListener(eventListener);




        }
    }



}
