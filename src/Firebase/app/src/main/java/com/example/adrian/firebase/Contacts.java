package com.example.adrian.firebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Contacts extends AppCompatActivity {
    private ChildEventListener eventListener;
    private ContactsAdapter adapter;
    private ListView listView;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        getSupportActionBar().hide();
        listView = (ListView) findViewById(R.id.list_of_messagees);
        List<Contact> chatApplications = new ArrayList<>();
        adapter = new ContactsAdapter(this, R.layout.contact, chatApplications);
        listView.setAdapter(adapter);
        SearchView sv = (SearchView) findViewById(R.id.inputSearch);
        attachDatabaseReadListener();
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
		
		
        //Setting search bar
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String arg0) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // TODO Auto-generated method stub

                adapter.getFilter().filter(query);

                return false;
            }
        });





    }

	//ataching database Reader on database.
    private void attachDatabaseReadListener() {
        if (eventListener == null) {
            eventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Contact message = dataSnapshot.getValue(Contact.class);
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

            FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance()
                    .getCurrentUser().getUid().toString()).addChildEventListener(eventListener);


        }
    }


}
