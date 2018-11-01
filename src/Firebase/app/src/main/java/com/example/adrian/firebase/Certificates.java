package com.example.adrian.firebase;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class Certificates extends AppCompatActivity {
    ImageView image2;
    private static final int Pick_Image = 100;
    int PICK_IMAGE_REQUEST = 111;
    Uri filePath;
    String userId;
    StorageReference storageRef;
    private DatabaseReference databaseReference;
    private List<ImageUpload> imgList;
    private ListView lv;
    String CertName = null;
    private ChildEventListener eventListener;
    private ImageListAdapter adapter;
    private ProgressDialog progressDialog;
    private ImageView back;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        getSupportActionBar().hide();
        //Initlizing Varibles
        handler = new Handler();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        storageRef = storage.getReferenceFromUrl("gs://fir-e6d19.appspot.com").child(userId).child("certs").child(System.currentTimeMillis() + ".jpg");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("certs").child(userId);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificates);
        image2 = (ImageView) findViewById(R.id.upload);
        imgList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listViewImage);
        attachDatabaseReadListener();
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        chooseName();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                //Fetch image data from firebase database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //ImageUpload class require default constructor
                    ImageUpload img = snapshot.getValue(ImageUpload.class);
                    imgList.add(img);
                }


                //Initlizing Adapter
                adapter = new ImageListAdapter(Certificates.this, R.layout.image_item, imgList);
                //Set adapter for listview
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            if (filePath != null) {


                //uploading the image
                storageRef.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                ImageUpload imageUpload = new ImageUpload(CertName, taskSnapshot.getDownloadUrl().toString());
                                databaseReference.push().setValue(imageUpload);
                                recreate();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                ;
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                            }
                        });

            } else {
                Toast.makeText(Certificates.this, "Select an image", Toast.LENGTH_SHORT).show();
            }
        }
    }

	//Set Listner on database
    private void attachDatabaseReadListener() {

        eventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Contact message = dataSnapshot.getValue(Contact.class);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Contact message = dataSnapshot.getValue(Contact.class);


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

        databaseReference.addChildEventListener(eventListener);


    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(Certificates.this);
        View promptView = layoutInflater.inflate(R.layout.fileupload, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Certificates.this);
        alertDialogBuilder.setView(promptView);
        final Animation animShakeP = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pulse);
        final ImageView loc = (ImageView) promptView.findViewById(R.id.file);
        loc.startAnimation(animShakeP);
        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CertName = editText.getText().toString();
                        chooseImage();

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


    public void chooseImage() {


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);


        startActivityForResult(Intent.createChooser(intent, " "), PICK_IMAGE_REQUEST);


    }

    public void chooseName() {
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();


            }
        });
    }
}
