package com.example.adrian.firebase;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    Button SignUp;
    EditText Name;
    EditText Email;
    EditText Password;
    String email;
    String password;
    ArrayAdapter<CharSequence> adapter2;
    private FirebaseAuth auth;
    private Spinner J;
    String type;
    private EditText RenterPassword;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        final TextView text = (TextView)findViewById(R.id.name);
        SignUp = (Button) findViewById(R.id.signup);
        Email = (EditText) findViewById(R.id.Email);
        SignUp = (Button) findViewById(R.id.signup);
        Password= (EditText) findViewById(R.id.Password);
        RenterPassword= (EditText) findViewById(R.id.re);
        Name= (EditText) findViewById(R.id.Name);
        final String simple = "Name ";
        String colored = "*";
        String coloredEmail = "*(Must be Gmail  Account)";
        String coloredPassword = "* (Minimum of 8 Characters)";
        final SpannableStringBuilder builder = new SpannableStringBuilder();

        builder.append(simple);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();

        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(builder);
        final TextView email = (TextView)findViewById(R.id.email);

        final String emailh = "Email ";

        final SpannableStringBuilder builder2 = new SpannableStringBuilder();

        builder2.append(emailh);
        int start2 = builder2.length();
        builder2.append(coloredEmail);
        int end2 = builder2.length();

        builder2.setSpan(new ForegroundColorSpan(Color.RED), start2, end2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        email.setText(builder2);



        final TextView password = (TextView)findViewById(R.id.password);

        final String passwordh = "Password ";

        final SpannableStringBuilder builder3 = new SpannableStringBuilder();

        builder3.append(passwordh);
        int start3 = builder3.length();
        builder3.append(coloredPassword);
        int end3 = builder3.length();

        builder3.setSpan(new ForegroundColorSpan(Color.RED), start3, end3,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        password.setText(builder3);
		
		//Password Text listner
        Password.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() <= 7)
                    password.setText(builder3);
                else
                {
                    password.setText(passwordh);
                }
            }
        });
         //Name Text Listner
        Name.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() ==0)
                    text.setText(builder);
                else
                {
                    text.setText(simple);
                }
            }
        });


        //Email Text Listner
        Email.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.toString().contains("gmail.com"))
                    email.setText(emailh);

                else
                {
                    email.setText(builder2);
                }
            }
        });


        final TextView passwordr = (TextView)findViewById(R.id.r);

        final String passwordhr = "Re-Enter Password ";

        final SpannableStringBuilder builder4 = new SpannableStringBuilder();

        builder4.append(passwordhr);
        int start4 = builder4.length();
        builder4.append(colored);
        int end4 = builder4.length();

        builder4.setSpan(new ForegroundColorSpan(Color.RED), start4, end4,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        passwordr.setText(builder4);
        RenterPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!s.toString().equals(Password.getText().toString()))
                    passwordr.setText(builder4);
                else
                {
                    passwordr.setText(passwordhr);
                }
            }
        });



        TextView user = (TextView)findViewById(R.id.user);

        String users = "User ";

        SpannableStringBuilder builder5 = new SpannableStringBuilder();

        builder5.append(users);
        int start5 = builder5.length();
        builder5.append(colored);
        int end5 = builder5.length();

        builder5.setSpan(new ForegroundColorSpan(Color.RED), start5, end5,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        user.setText(builder5);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Regestration");
        //Job Spinner
        J = (Spinner) findViewById(R.id.type);
        adapter2 = ArrayAdapter.createFromResource(this,R.array.typearray, android.R.layout.simple_spinner_item);
        J.setAdapter(adapter2);
        J.setSelection(1);
        J.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                type =J.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SignUp = (Button) findViewById(R.id.signup);
        Email = (EditText) findViewById(R.id.Email);
        SignUp = (Button) findViewById(R.id.signup);
        Password= (EditText) findViewById(R.id.Password);



        auth = FirebaseAuth.getInstance();
        OnClickLogin();







    }
	
	//Sign User Up
    public void OnClickLogin() {
        SignUp = (Button) findViewById(R.id.signup);
        Email = (EditText) findViewById(R.id.Email);
        auth = FirebaseAuth.getInstance();
        SignUp.setOnClickListener(new View.OnClickListener() {
                                     public void onClick(View v) {
                                         SignUp = (Button) findViewById(R.id.signup);
                                         Email = (EditText) findViewById(R.id.Email);
                                         email = Email.getText().toString();
                                         password =  Password.getText().toString();

                                         type = J.getSelectedItem().toString();

                                         if((!email.isEmpty() || !password.isEmpty()) && password.length() >=7 && password.equals(RenterPassword.getText().toString())) {
                                             auth.createUserWithEmailAndPassword(email, password)
                                                     .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                         @Override
                                                         public void onComplete(@NonNull Task<AuthResult> task) {
                                                             if (task.isSuccessful()) {
                                                                 Toast.makeText(Signup.this, "You Are Now A Member" , Toast.LENGTH_SHORT).show();

                                                                 FirebaseDatabase.getInstance().getReference().child(type).child(email.substring(0,email.length()-10)).setValue(new Contact("hello","hello"));

                                                                 if(J.getSelectedItem().toString().equals("Employee"))
                                                                 {
                                                                     Intent intent = new Intent(Signup.this,LoginEmployer.class);
                                                                     startActivity(intent);
                                                                 }
                                                                 else
                                                                 {
                                                                     Intent intent = new Intent(Signup.this,LoginEmployee.class);
                                                                     startActivity(intent);
                                                                 }


                                                             } else {
                                                                 Toast.makeText(Signup.this, "Please use correct Email for Registeration " , Toast.LENGTH_SHORT).show();
                                                             }

                                                         }
                                                     });
                                         }
                                         else
                                         {
                                             Toast.makeText(Signup.this, "Please Complete Sign Up Correctly", Toast.LENGTH_SHORT).show();
                                         }



                                     }

                                 }
        );
    }




}
