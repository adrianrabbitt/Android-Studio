package com.example.adrian.firebase;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//import com.firebase.ui.database.FirebaseListAdapter;
//References
//https://code.tutsplus.com/tutorials/how-to-create-an-android-chat-app-using-firebase--cms-27397 instant messaging
//https://developer.android.com/guide/topics/ui/ui-events
//https://developer.android.com/reference/java/text/SimpleDateFormat

public class ChatApplication extends AppCompatActivity {
    ImageView send_message_btn;
    String newString;
    String chat_room1;
    EditText input;
    String chat_room2;
    private ListView listView;
    private ChildEventListener eventListener;
    private MessageAdapter adapter;
    private String Name;
    private ImageView image;
    private TextView title;
    private TextView time;
    private String reciever;
    private String sender;
    DatabaseReference myRef;
    private ImageView back_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_application);
        Bundle extras = getIntent().getExtras();
        //referencing Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        //Passing Values to Activity
        newString = extras.getString("U");
        Name = extras.getString("F");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.toolbarT);
        getSupportActionBar().hide();
        title.setText(Name);
        //Initlizing Objects
        image = (ImageView) findViewById(R.id.pic);
        back_btn = (ImageView) findViewById(R.id.back);
        send_message_btn = (ImageView) findViewById(R.id.img);
        time = (TextView) findViewById(R.id.time);
        Calendar c = Calendar.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        //Reference To Tokens For Push Notifications
        myRef.child("Tokens").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                reciever = (String) snapshot.child(newString).child("token").getValue();
                sender = (String) snapshot.child(newString).child("email").getValue();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String date = df.format(c.getTime());
        time.setText(date);
        input = (EditText) findViewById(R.id.edittext);
        StorageReference storageRef = storage.getReferenceFromUrl("gs://fir-e6d19.appspot.com").child(newString).child("Profile.jpg");

        //Downloading Image From Firebase Storage.
        try {
            final File localFile = File.createTempFile("image", "jpg");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    if (taskSnapshot != null) {
                        Glide.with(getApplicationContext()).load(localFile).asBitmap().centerCrop().into(new BitmapImageViewTarget(image) {
                            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);

                                circularBitmapDrawable.setCircular(true);
                                image.setImageDrawable(circularBitmapDrawable);
                            }
                        });
                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });
        } catch (IOException e) {
        }


        listView = (ListView) findViewById(R.id.list_of_messages);
        List<ChatMessage> chatApplications = new ArrayList<>();
        adapter = new MessageAdapter(this, R.layout.message, chatApplications);
        listView.setAdapter(adapter);
        //Creating Chat Rooms for instant messaging.
        chat_room1 = FirebaseAuth.getInstance().getCurrentUser().getUid() + "_" + newString;
        chat_room2 = newString + "_" + FirebaseAuth.getInstance().getCurrentUser().getUid();
        attachDatabaseReadListener();

        send_message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseDatabase.getInstance().getReference().child("users").child(newString).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        FirebaseDatabase.getInstance().getReference().child("users").child(newString).child(FirebaseAuth.getInstance()
                                .getCurrentUser().getUid()).setValue(new Contact(FirebaseAuth.getInstance()
                                .getCurrentUser().getEmail(), FirebaseAuth.getInstance()
                                .getCurrentUser().getUid())
                        );
                        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance()
                                .getCurrentUser().getUid()).child(newString).setValue(new Contact(sender, newString)
                        );
                        // run some code

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                if (reciever == null) {
                    reciever = "error";
                }
                if (!input.getText().toString().isEmpty()) {
                    FirebaseDatabase.getInstance().getReference().child("chats").child(chat_room1)
                            .push()
                            .setValue(new ChatMessage(input.getText().toString(), FirebaseAuth.getInstance()
                                    .getCurrentUser().getEmail(), reciever)
                            );

                    FirebaseDatabase.getInstance().getReference().child("chats").child(chat_room2)
                            .push()
                            .setValue(new ChatMessage(input.getText().toString(), FirebaseAuth.getInstance()
                                    .getCurrentUser().getEmail(), reciever)
                            );
                    FirebaseDatabase.getInstance().getReference().child("Notifications")
                            .setValue(new ChatMessage(input.getText().toString(), FirebaseAuth.getInstance()
                                    .getCurrentUser().getEmail(), reciever)
                            );
                    input.getText().clear();

                }

            }
        });


    }

    //Attach DatabaseReadListner() watches the database for certain changes.

    private void attachDatabaseReadListener() {
        if (eventListener == null) {
            eventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChatMessage message = dataSnapshot.getValue(ChatMessage.class);
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

            FirebaseDatabase.getInstance().getReference().child("chats").child(chat_room1).addChildEventListener(eventListener);


        }
    }

}
