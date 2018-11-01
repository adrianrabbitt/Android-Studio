package com.example.adrian.firebase;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginEmployee extends AppCompatActivity {
    
    private TextView Parent;
    private Button Client;
    private Button Sup;
    EditText Email;
    TextView Password;
    String email;
    private boolean contain = false;
    String password;
    private FirebaseAuth auth2;
    DatabaseReference rootRef,demoRef;
    SharedPreferences sharedPref;
    final String key = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_employee);
        Email = (EditText) findViewById(R.id.email);
        Sup = (Button) findViewById(R.id.ButtonRegister);
        auth2 = FirebaseAuth.getInstance();
        Password= (EditText) findViewById(R.id.password);
        sharedPref = LoginEmployee.this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        OnClickLoginEmployee();
        OnClickParent();
        OnSignUp();
    }

    //Show input Dialog when called to get email to rest password
    protected void showInputDialog() {


        LayoutInflater layoutInflater = LayoutInflater.from(LoginEmployee.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginEmployee.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        resetUserPassword(editText.getText().toString());

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

    //reset User password  reference tuturial https://stackoverflow.com/questions/42800349/forgot-password-in-firebase-for-android?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
    public void resetUserPassword(String email){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(LoginEmployee.this);
        progressDialog.setMessage("verifying..");
        progressDialog.show();

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Reset password instructions has sent to your email",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),
                                    "Email don't exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

   
    public void OnClickParent() {
        Parent = (TextView) findViewById(R.id.Signup);
        Parent.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                          final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
                                          Parent.startAnimation(animShake);
                                          email = Email.getText().toString().trim();

                                          password =  Password.getText().toString();
                                         /* auth2.signInWithEmailAndPassword(email,password)
                                                  .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                      @Override
                                                      public void onComplete(@NonNull Task<AuthResult> task) {
                                                          if(task.isSuccessful())
                                                          {
                                                              Toast.makeText(LoginEmployee.this, "YES" + Email.getText(), Toast.LENGTH_SHORT).show();
                                                          }
                                                          else
                                                          {
                                                              Toast.makeText(LoginEmployee.this, "No" + Email.getText(), Toast.LENGTH_SHORT).show();
                                                          }
                                                      }
                                                  });
*/
                                         showInputDialog();
                                      }

                                  }
        );
    }
    //Sign up
    public void OnSignUp() {

        Sup.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                          email = Email.getText().toString().trim();
                                          final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
                                          Sup.startAnimation(animShake);
                                          password =  Password.getText().toString();
                                         /* auth2.signInWithEmailAndPassword(email,password)
                                                  .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                      @Override
                                                      public void onComplete(@NonNull Task<AuthResult> task) {
                                                          if(task.isSuccessful())
                                                          {
                                                              Toast.makeText(LoginEmployee.this, "YES" + Email.getText(), Toast.LENGTH_SHORT).show();
                                                          }
                                                          else
                                                          {
                                                              Toast.makeText(LoginEmployee.this, "No" + Email.getText(), Toast.LENGTH_SHORT).show();
                                                          }
                                                      }
                                                  });
*/
                                          Intent intent = new Intent(LoginEmployee.this, Signup.class);
                                          startActivity(intent);
                                      }

                                  }
        );
    }







    //Login Employee
    public void OnClickLoginEmployee() {
        Password= (EditText) findViewById(R.id.password);
        Email = (EditText) findViewById(R.id.email);
        Client = (Button) findViewById(R.id.appCompatButtonLogin);
        Client.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                          final ProgressDialog progressDialog = new ProgressDialog(LoginEmployee.this);
                                          progressDialog.setMessage("verifying..");
                                          final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
                                          Client.startAnimation(animShake);
                                          email = Email.getText().toString().trim();
                                          password =  Password.getText().toString();

                                          if(!email.isEmpty() && !password.isEmpty()) {
                                              progressDialog.show();
                                              auth2.signInWithEmailAndPassword(email, password)
                                                      .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                          @Override
                                                          public void onComplete(@NonNull Task<AuthResult> task) {

                                                              if (task.isSuccessful()) {
                                                               progressDialog.dismiss();
															   
															   //Initialzing shared preference
                                                                  SharedPreferences.Editor editor = sharedPref.edit();

                                                                  editor.putString(key, "Employer");
                                                                  editor.commit();
                                                                  Toast.makeText(LoginEmployee.this, "" +sharedPref.getString(key,"a"), Toast.LENGTH_SHORT).show();


                                                                  FirebaseDatabase.getInstance().getReference().child("Employer").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                      @Override
                                                                      public void onDataChange(DataSnapshot snapshot) {
                                                                          String nemail = email.substring(0,email.length()-10);
                                                                          if (snapshot.hasChild(nemail)) {
                                                                              Intent intent = new Intent(LoginEmployee.this, Employer.class);
                                                                              startActivity(intent);



                                                                          }
                                                                          else
                                                                          {
                                                                              progressDialog.dismiss();
                                                                              Toast.makeText(LoginEmployee.this, "No reg" + Email.getText(), Toast.LENGTH_SHORT).show();
                                                                          }
                                                                      }

                                                                      @Override
                                                                      public void onCancelled(DatabaseError databaseError) {

                                                                      }
                                                                  });


                                                              } else {
                                                                  progressDialog.dismiss();
                                                                  Toast.makeText(LoginEmployee.this, "Cant find User " + Email.getText() + " in database", Toast.LENGTH_SHORT).show();
                                                              }
                                                          }
                                                      });
                                          }
                                          else
                                          {
											  //Making sure all fields are filled in
                                              Toast.makeText(LoginEmployee.this, "Please fill in all Fields" , Toast.LENGTH_SHORT).show();
                                          }





                                      }

                                  }
        );
    }

	
	//When back button is pressed killl application
    @Override
    public void onBackPressed() {


        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}


