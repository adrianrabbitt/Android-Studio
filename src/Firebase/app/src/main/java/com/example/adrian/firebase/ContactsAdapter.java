package com.example.adrian.firebase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 12/02/2018.
 */

public class ContactsAdapter extends ArrayAdapter<Contact> {

    //Adapter For Contacts Activity
    private String parm;
    private ChildEventListener eventListener;
    List<Contact> filterList;
    List<Contact> newList;
    ContactsAdapter.CustomFilter2 filter;

    public ContactsAdapter(Context context, int resource, List<Contact> objects) {
        super(context, resource, objects);
        attachDatabaseReadListener();
        filterList = objects;
        newList = objects;

    }
    //Setting arraylist of members and users to reference array adapter
    ArrayList<String> members = new ArrayList<String>();
    ArrayList<String> users = new ArrayList<String>();

    //attach listner to the database.
    private void attachDatabaseReadListener() {
        if (eventListener == null) {
            eventListener = new ChildEventListener() {


                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
					//get the current contact and add to the arrayLists
                    Contact message = dataSnapshot.getValue(Contact.class);
                    members.add(message.getOuid());
                    users.add(message.getMessageUser());
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

            FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addChildEventListener(eventListener);


        }
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        attachDatabaseReadListener();
        Contact message = getItem(position);


        //Setting the formats of the Contact
        if (getContext().getClass().getSimpleName().toString().contains("Contacts")) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.contact, parent, false);

            TextView text1 = (TextView) convertView.findViewById(R.id.message_userC);
            TextView text3 = (TextView) convertView.findViewById(R.id.message_EmailC);
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String date = df.format(message.getMessagetime());
            text1.setText(message.getMessageUser().substring(0, 1));
            text3.setText(message.getMessageUser());
        }

        //When  the Contact is clicked start mess
        if (members.size() != 0) {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("OpenDbListAdapter ", "List View Clicked");

                    Intent intent = new Intent(getContext(), ChatApplication.class);
                    Bundle extras = new Bundle();
                    extras.putString("U", members.get(position));
                    extras.putString("F", users.get(position));
                    intent.putExtras(extras);
                    getContext().startActivity(intent);
                }
            });

        }

        return convertView;
    }

    @Override
    public Filter getFilter() {
        // TODO Auto-generated method stub

        filter = new ContactsAdapter.CustomFilter2();


        return filter;
    }


	
	//Custom Filter for search bar.
	//reference: https://stackoverflow.com/questions/24769257/custom-listview-adapter-with-filter-android?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
    class CustomFilter2 extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub

            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                //CONSTARINT TO UPPER
                constraint = constraint.toString();

                ArrayList<Contact> filters = new ArrayList<Contact>();

                //get specific items
                for (int i = 0; i < filterList.size(); i++) {
                    if (filterList.get(i).getMessageUser().contains(constraint)) {
                        Contact p = filterList.get(i);

                        filters.add(p);
                    }
                }

                results.count = filters.size();
                results.values = filters;
                Toast.makeText(getContext(), " " + results.values + constraint, Toast.LENGTH_SHORT).show();

            } else {
                results.count = filterList.size();
                results.values = filterList;


            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // TODO Auto-generated method stub

            newList = (ArrayList<Contact>) results.values;
            notifyDataSetChanged();

        }

    }

}
