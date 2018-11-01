package com.example.adrian.firebase;



//references
//https://developer.android.com/reference/android/text/SpannableStringBuilder
//https://firebase.google.com/docs/storage/android/upload-files
//https://www.tutorialspoint.com/android/android_spelling_checker.htm
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class CV extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SpellCheckerSession.SpellCheckerSessionListener {
    ImageView image;
    ImageView button;
    int PICK_IMAGE_REQUEST = 111;
    private Student student;
    private DatabaseReference myRef;
    Button submit;
    EditText skills;
    Uri filePath;
    EditText date;
    DatePickerDialog datePickerDialog;
    EditText fname;
    EditText sname;
    EditText email;
    EditText mobile;
    EditText phone;
    TextView name1;
    EditText address1;
    EditText address2;
    EditText info;
    EditText ptitle;
    EditText pdescription;
    EditText current;
    String Sex;
    String Job;
    int day1;
    int month1;
    int year1;
    Spinner J;
    Spinner S;
    Spinner T;
    ArrayAdapter<CharSequence> adapter2;
    ArrayAdapter<CharSequence> adapter;


    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = android.Manifest.permission.ACCESS_FINE_LOCATION;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    final FirebaseDatabase database;
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    ;
    StorageReference storageRef = storage.getReferenceFromUrl("gs://fir-e6d19.appspot.com").child(userId).child("Profile.jpg");
    StorageReference storageRef2 = storage.getReference();

    private static final int Pick_Image = 100;
    private Context currentState;
    private ImageView back;
    private SpellCheckerSession mScs;
    private ArrayAdapter<CharSequence> adapter3;
    String jobr;
    String sexr;
    String titler;

    public CV() {
        database = FirebaseDatabase.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv);
        back = (ImageView) findViewById(R.id.back);
        myRef = database.getReference();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("CV");
        S = (Spinner) findViewById(R.id.sex);
        J = (Spinner) findViewById(R.id.jobs);
        T = (Spinner) findViewById(R.id.title);
        name1 = (TextView) findViewById(R.id.name1);

        fname = (EditText) findViewById(R.id.firstName);
        sname = (EditText) findViewById(R.id.secondName);
        email = (EditText) findViewById(R.id.email);
        mobile = (EditText) findViewById(R.id.mobile);

        phone = (EditText) findViewById(R.id.phone);
        address1 = (EditText) findViewById(R.id.Address1);
        address2 = (EditText) findViewById(R.id.Address2);
        info = (EditText) findViewById(R.id.Info);
        ptitle = (EditText) findViewById(R.id.ptitle);
        pdescription = (EditText) findViewById(R.id.pdescription);
        skills = (EditText) findViewById(R.id.skills);
        adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2 = ArrayAdapter.createFromResource(this, R.array.jobsarray, android.R.layout.simple_spinner_item);
        adapter3 = ArrayAdapter.createFromResource(this, R.array.title, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        S.setAdapter(adapter);

        J.setAdapter(adapter2);

        T.setAdapter(adapter3);

        date = (EditText) findViewById(R.id.date);
        S.setOnItemSelectedListener(this);
        J.setOnItemSelectedListener(this);
        T.setOnItemSelectedListener(this);

        toolbar.setTitle("");

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.e("Count", "" + dataSnapshot.getChildrenCount());
                Student s = dataSnapshot.child("Users").child(userId).child("Info").getValue(Student.class);
                ;
                if (s != null) {
                    name1.setText(s.getfName() + " " + s.getsName());
                    fname.setText(s.getfName());
                    sname.setText(s.getsName());
                    jobr = s.getJob();
                    sexr = s.getSex();
                    titler = s.getTitle1();
                    mobile.setText(s.getMobile());
                    phone.setText(s.getPhone());
                    address1.setText(s.getIAddress1());
                    address2.setText(s.getIAddress2());
                    pdescription.setText(s.getPdes());
                    info.setText(s.getInfo());
                    ptitle.setText(s.getPtitle());
                    pdescription.setText(s.getPdes());
                    date.setText(s.getDay() + "/" + s.getMonth() + "/" + s.getYear());
                    J.setSelection(adapter2.getPosition(jobr));
                    //String skil = "\u25CF" + s.getSkill().replace("\n", "\n" + "\u25CF");
                    //String skil = "\u25CF" + s.getSkill().replace("\n", "\n" + "\u25CF");
                    S.setSelection(adapter.getPosition(sexr));
                    T.setSelection(adapter3.getPosition(titler));
                    skills.setText(s.getSkill());
                    // Age.setText(s.getYear());

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();


                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(CV.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                day1 = dayOfMonth;
                                month1 = monthOfYear;
                                year1 = year;
                                date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        ActivityCompat.requestPermissions(this, new String[]{mPermission},
                REQUEST_CODE_PERMISSION);

        // If any permission above not allowed by user, this condition will
        image = (ImageView) findViewById(R.id.image);

        try {
            final File localFile = File.createTempFile("image", "jpg");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {


                    Glide.with(getApplicationContext()).load(localFile).asBitmap().centerCrop().into(new BitmapImageViewTarget(image) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);

                            circularBitmapDrawable.setCircular(true);
                            image.setImageDrawable(circularBitmapDrawable);
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });
        } catch (IOException e) {
        }


        final TextView title = (TextView) findViewById(R.id.fname);

        final String emailh = "First Name";

        final SpannableStringBuilder builder2 = new SpannableStringBuilder();
        String colored = "*";
        builder2.append(emailh);
        int start2 = builder2.length();
        builder2.append(colored);
        int end2 = builder2.length();

        builder2.setSpan(new ForegroundColorSpan(Color.RED), start2, end2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        title.setText(builder2);

        final TextView secondName = (TextView) findViewById(R.id.sname);

        final String secondN = "Second Name";

        final SpannableStringBuilder builder3 = new SpannableStringBuilder();

        builder3.append(secondN);
        int start3 = builder3.length();
        builder3.append(colored);
        int end3 = builder3.length();

        //Setting String Builders if Users Data is not correctly entered.
        builder3.setSpan(new ForegroundColorSpan(Color.RED), start3, end3,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        secondName.setText(builder3);

        final TextView Adress = (TextView) findViewById(R.id.address);

        final String address = "Address";

        final SpannableStringBuilder builder4 = new SpannableStringBuilder();

        builder4.append(address);
        int start4 = builder4.length();
        builder4.append(colored);
        int end4 = builder4.length();

        builder4.setSpan(new ForegroundColorSpan(Color.RED), start4, end4,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Adress.setText(builder4);


        final TextView personal = (TextView) findViewById(R.id.per);

        final String perD = "Personal Description";

        final SpannableStringBuilder builder5 = new SpannableStringBuilder();

        builder5.append(perD);
        int start5 = builder5.length();
        builder5.append(colored);
        int end5 = builder5.length();

        builder5.setSpan(new ForegroundColorSpan(Color.RED), start5, end5,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        personal.setText(builder5);

        final TextView job = (TextView) findViewById(R.id.job);

        final String jobd = "Previous Employment";

        final SpannableStringBuilder builder6 = new SpannableStringBuilder();

        builder6.append(jobd);
        int start6 = builder6.length();
        builder6.append(colored);
        int end6 = builder6.length();

        builder6.setSpan(new ForegroundColorSpan(Color.RED), start6, end6,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        job.setText(builder6);


        builder5.setSpan(new ForegroundColorSpan(Color.RED), start5, end5,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        personal.setText(builder5);

        final TextView skill = (TextView) findViewById(R.id.skill);

        final String skilld = "Skills";

        final SpannableStringBuilder builder7 = new SpannableStringBuilder();

        builder7.append(skilld);
        int start7 = builder7.length();
        builder7.append(colored);
        int end7 = builder7.length();

        builder7.setSpan(new ForegroundColorSpan(Color.RED), start7, end7,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        skill.setText(builder7);

        //Text Listener for change in First Name.
        fname.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() == 0)
                    title.setText(builder2);
                else {
                    title.setText(emailh);
                }
            }
        });

        //Text Listener for change in Second Name.
        sname.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() == 0)
                    secondName.setText(builder3);
                else {
                    secondName.setText(secondN);
                }
            }
        });

        //Text Listener for change in Address1.
        address1.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() == 0)
                    Adress.setText(builder4);
                else {
                    Adress.setText(address);
                }
            }
        });

        //Text Listener for change in Information Page.
        info.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (!s.equals(null)) {
                    //  mScs.getSentenceSuggestions(new TextInfo[]{new TextInfo("he")}, 3);

                }
                if (s.length() == 0)
                    personal.setText(builder5);
                else {
                    personal.setText(perD);
                }
            }
        });


        //Text Listener for change in Personal Title.
        ptitle.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() == 0)
                    job.setText(builder6);
                else {
                    job.setText(jobd);
                }
            }
        });

        //Text Listener for change in Skills.
        skills.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() == 0)
                    skill.setText(builder7);
                else {
                    skill.setText(skilld);
                }
            }
        });


        button = (ImageView) findViewById(R.id.pic);
        button.setOnClickListener(new View.OnClickListener() {
                                      @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                      @Override
                                      public void onClick(View v) {

                                          Intent intent = new Intent();
                                          intent.setType("image/*");
                                          intent.setAction(Intent.ACTION_PICK);
                                          startActivityForResult(Intent.createChooser(intent, " "), PICK_IMAGE_REQUEST);


                                      }

                                  }
        );


        OnClickSubmit();


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            if (filePath != null) {


                StorageReference childRef = storageRef2.child(userId).child("Profile.jpg");


                //uploading the image
                childRef.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Toast.makeText(CV.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                try {
                                    final File localFile = File.createTempFile("image", "jpg");
                                    storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {


                                            Glide.with(getApplicationContext()).load(localFile).asBitmap().centerCrop().into(new BitmapImageViewTarget(image) {
                                                @Override
                                                protected void setResource(Bitmap resource) {
                                                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);

                                                    circularBitmapDrawable.setCircular(true);
                                                    image.setImageDrawable(circularBitmapDrawable);
                                                }
                                            });


                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            Toast.makeText(getApplicationContext(), "Please Complete Sections", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                } catch (IOException e) {
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(CV.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                        .getTotalByteCount());

                            }
                        });


            } else {
                Toast.makeText(CV.this, "Select an image", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void OnClickSubmit() {
        submit = (Button) findViewById(R.id.submit);
        // showing current location on map.


        submit.setOnClickListener(new View.OnClickListener() {


                                      public String pTitle;

                                      public void onClick(View v) {

                                          //For CV Tests That were carried Out

                                          if (!(fname.getText().equals(null) & sname.getText().equals(null) & address1.getText().equals(null))) {

                                              GPSTracker gps = new GPSTracker(CV.this);
                                              double latitude = gps.getLatitude();
                                              double longitude = gps.getLongitude();

                                              Sex = S.getSelectedItem().toString();
                                              Job = J.getSelectedItem().toString();
                                              pTitle = T.getSelectedItem().toString();

                                              String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


                                              student = new Student(pTitle, fname.getText().toString(), sname.getText().toString(), Sex, Job, day1, month1, year1, email.getText().toString(), mobile.getText().toString(), phone.getText().toString(), address1.getText().toString(), address2.getText().toString(), info.getText().toString(), ptitle.getText().toString(), pdescription.getText().toString(), skills.getText().toString(), latitude, longitude, "test", "test", "true");
                                              myRef.child("Users").child(userId).child("Info").setValue(student);
                                              Toast.makeText(getApplicationContext(), "Your Details Are Saved", Toast.LENGTH_LONG).show();

                                          } else {
                                              Toast.makeText(getApplicationContext(), "Please Complete All Fields", Toast.LENGTH_LONG).show();
                                          }


                                      }


                                  }

        );


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String sSelected = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onGetSuggestions(SuggestionsInfo[] results) {
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < results.length; ++i) {

            final int length = results[i].getSuggestionsCount();
            sb.append('\n');

            for (int j = 0; j < length; ++j) {
                sb.append("," + results[i].getSuggestionAt(j));
            }

            sb.append(" (" + length + ")");
        }

        runOnUiThread(new Runnable() {
            public void run() {
                info.append(sb.toString());
            }
        });

    }

    @Override
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] results) {

    }
}
