package com.example.adrian.firebase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Adrian on 12/02/2018.
 */

//Adapter For Conversations Adapter
public class ConversationAdapter extends ArrayAdapter<Contact> {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;

    private String parm;
    List<Contact> filterList;
    List<Contact> newList;
    CustomFilter filter;
    private ChildEventListener eventListener;

    public ConversationAdapter(Context context, int resource, List<Contact> objects) {

        super(context, resource, objects);
        attachDatabaseReadListener();
        filterList = objects;
        newList = objects;

    }

    ArrayList<String> members = new ArrayList<String>();
    ArrayList<String> users = new ArrayList<String>();

    //attach to the database and listen for changes.
    private void attachDatabaseReadListener() {
        if (eventListener == null) {
            eventListener = new ChildEventListener() {


                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Contact message = dataSnapshot.getValue(Contact.class);
                    members.add(message.getOuid());
                    users.add(message.getMessageUser());
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Contact message = dataSnapshot.getValue(Contact.class);


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

    //set the current view of the Conversation Object.
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Contact message = getItem(position);
        convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.conversation, parent, false);
        ImageView img = (ImageView) convertView.findViewById(R.id.img);
        TextView text1 = (TextView) convertView.findViewById(R.id.message_userCo);
        TextView text3 = (TextView) convertView.findViewById(R.id.message_EmailCo);
        final TextView text4 = (TextView) convertView.findViewById(R.id.time);
        final TextView text2 = (TextView) convertView.findViewById(R.id.message_messCo);
        //TextView text3 = (TextView) convertView.findViewById(R.id.message_time);
        int boy[] = {R.drawable.cir, R.drawable.cir2, R.drawable.cir3, R.drawable.cir4};
        TextView best = (TextView) convertView.findViewById(R.id.message_userC);
		//Create Random object.
        Random rand = new Random();
        int index = rand.nextInt((boy.length - 1) - 0 + 1) + 0;
        text1.setBackground(getContext().getResources().getDrawable(boy[index]));
        myRef = database.getReference();
        if (members != null) {
            Query lastQuery = myRef.child("chats").child(FirebaseAuth.getInstance()
                    .getCurrentUser().getUid().toString() + "_" + members.get(position).toString()).orderByKey().limitToLast(1);
            lastQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                        long date = (long) dataSnapshot1.child("messageTime").getValue();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
                        String dateString = formatter.format(new Date(date));
                        text4.setText(getFormattedDateFromTimestamp(date));
                        text2.setText(dataSnapshot1.child("messageText").getValue().toString());

                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


        //Setting date
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String date = df.format(message.getMessagetime());
        text1.setText(message.getMessageUser().substring(0, 1));
        text3.setText(message.getMessageUser());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             


                Intent intent = new Intent(getContext(), ChatApplication.class);
                Bundle extras = new Bundle();
                extras.putString("U", members.get(position));
                extras.putString("F", users.get(position));
                intent.putExtras(extras);
                getContext().startActivity(intent);
            }
        });

      
	  
	    //If clicked remove node from Firebase, remove Conversation node.
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View promptView = layoutInflater.inflate(R.layout.delete, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setView(promptView);
                final Animation animShakeP = AnimationUtils.loadAnimation(getContext(), R.anim.pulse);
                final ImageView loc = (ImageView) promptView.findViewById(R.id.xbutton);
                loc.startAnimation(animShakeP);
                final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
                // setup a dialog window
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myRef = database.getReference();
                                myRef.child("users").child(FirebaseAuth.getInstance()
                                        .getCurrentUser().getUid().toString()).child(members.get(position).toString()).removeValue();

                                myRef.child("chats").child(FirebaseAuth.getInstance()
                                        .getCurrentUser().getUid().toString() + "_" + members.get(position).toString()).removeValue();

                                myRef.child("chats").child(members.get(position).toString() + "_" + FirebaseAuth.getInstance()
                                        .getCurrentUser().getUid().toString()).removeValue();


                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();

            }
        });


        return convertView;
    }

	
	//Show input dialog when called.
    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.delete, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myRef = database.getReference();


                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public Filter getFilter() {
        // TODO Auto-generated method stub
        if (filter == null) {
            filter = new CustomFilter();
        }

        return filter;
    }

    //setting custom filter for searching through custom Adapter 
    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub

            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
               
                constraint = constraint.toString().toUpperCase();

                ArrayList<Contact> filters = new ArrayList<Contact>();

                
                for (int i = 0; i < filterList.size(); i++) {
                    if (filterList.get(i).getMessageUser().contains(constraint)) {
                        Contact p = filterList.get(i);

                        filters.add(p);
                    }
                }

                results.count = filters.size();
                results.values = filters;

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
    //Format date from Miliseconds to "MMM d, yyyy"
    public static String getFormattedDateFromTimestamp(long timestampInMilliSeconds) {
        Date date = new Date();
        date.setTime(timestampInMilliSeconds);
        String formattedDate = new SimpleDateFormat("MMM d, yyyy").format(date);
        return formattedDate;

    }


}
