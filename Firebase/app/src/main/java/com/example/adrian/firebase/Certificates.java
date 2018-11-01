package com.example.adrian.firebase;

import android.app.AlertDialog;
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


//This class is for handling the Employees Files, uploading and retrieving from Firebase Storage
//References
//Here is the link to the tutorial I followed to download the images https://www.youtube.com/watch?v=tWlnAAZclFE
//Link that helped me choose an image from camera roll https://demonuts.com/pick-image-gallery-camera-android/
//https://stackoverflow.com/questions/10903754/input-text-dialog-android?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa

public class Certificates extends AppCompatActivity {
    ImageView image_btn;
    int PICK_IMAGE_REQUEST = 111;
    Uri file_path;
    String userId;
    StorageReference storageRef;
    private DatabaseReference databaseReference;
    private List<ImageUpload> imgList;
    private ListView lv;
    String CertName = null;
    private ChildEventListener eventListener;
    private ImageListAdapter adapter;
    private ImageView back_btn;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        getSupportActionBar().hide();
        handler = new Handler();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        storageRef = storage.getReferenceFromUrl("gs://fir-e6d19.appspot.com").child(userId).child("certs").child(System.currentTimeMillis() + ".jpg");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("certs").child(userId);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificates);

        image_btn = (ImageView) findViewById(R.id.upload);
        imgList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listViewImage);
        attachDatabaseReadListener();
        back_btn = (ImageView) findViewById(R.id.back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        chooseName();

        //When there is a change in the data field(New Image uploaded add to the arrayList)
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

    //Uploading selected image from device in background.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            file_path = data.getData();

            if (file_path != null) {


                //uploading the image
                storageRef.putFile(file_path)
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

    // Watching the database checking if any node's are added or removed.
    private void attachDatabaseReadListener() {

        eventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

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

    //Show pop up dialog to get the Files Name, once received retrieve image from the device.
    protected void showInputDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(Certificates.this);
        View promptView = layoutInflater.inflate(R.layout.fileupload, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Certificates.this);
        alertDialogBuilder.setView(promptView);
        final Animation animShakeP = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pulse);
        final ImageView loc = (ImageView) promptView.findViewById(R.id.file);
        loc.startAnimation(animShakeP);
        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // Setup a dialog window
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

        // Create an alert dialog
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
        image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();


            }
        });
    }
}
