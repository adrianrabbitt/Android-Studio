package com.example.adrian.firebase;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class Conversation extends AppCompatActivity {
    private ChildEventListener eventListener;
    private ConversationAdapter adapter;
    private ListView listView;
    TextView t;
    SearchView sv;
    List<Contact> chatApplications;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        listView = (ListView) findViewById(R.id.list_of_messagees);
        sv = (SearchView) findViewById(R.id.inputSearch);
        chatApplications = new ArrayList<>();
        adapter = new ConversationAdapter(this, R.layout.conversation, chatApplications);
        listView.setAdapter(adapter);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                attachDatabaseReadListener();
            }
        });
        getSupportActionBar().hide();
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        final Handler handler = new Handler();
        listView.setFocusable(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setItemsCanFocus(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                listView.setSelected(true);
                View parentRow = (View) view.getParent();
                ListView listView = (ListView) parentRow.getParent();
                position = listView.getPositionForView(parentRow);

                // TODO Auto-generated method stub
                Toast.makeText(Conversation.this, "hell0" + position, Toast.LENGTH_SHORT).show();
            }
        });


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
    //Listining to the database for Changes

    private void attachDatabaseReadListener() {

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
                Contact message = dataSnapshot.getValue(Contact.class);
                Toast.makeText(Conversation.this, " " + message, Toast.LENGTH_SHORT).show();
                adapter.remove(message);
                chatApplications.remove(message);

                adapter.notifyDataSetChanged();
                recreate();

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
