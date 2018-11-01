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

public class LoginEmployer extends AppCompatActivity {


 
    private TextView Parent;
    private Button Client;
    EditText Email;
    EditText Password;
    private Button Sup;
    String email;
    String password;
    private FirebaseAuth auth2;
    SharedPreferences sharedPref;
    final String key = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_employer);
        Email = (EditText) findViewById(R.id.email);
        Sup = (Button) findViewById(R.id.ButtonRegister);
        auth2 = FirebaseAuth.getInstance();
        Password= (EditText) findViewById(R.id.password);
        sharedPref = LoginEmployer.this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        OnClickLogin();
        OnClickSignUp();
        OnSignUp();

    }



  //Sign up
    public void OnClickSignUp() {
        Parent = (TextView) findViewById(R.id.Signup);
        Parent.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                          final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
                                          Parent.startAnimation(animShake);
                                          email = Email.getText().toString();

                                          password =  Password.getText().toString();


                                          showInputDialog();
                                      }

                                  }
        );
    }


    protected void showInputDialog() {


        LayoutInflater layoutInflater = LayoutInflater.from(LoginEmployer.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginEmployer.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);

        if (!editText.toString().isEmpty())
        {
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

    }

	
	//Reset User password, Dublication of code will remove in future
    public void resetUserPassword(String email){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(LoginEmployer.this);
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

            }
        });
    }


    public void OnSignUp() {

        Sup.setOnClickListener(new View.OnClickListener() {
                                   public void onClick(View v) {
                                       email = Email.getText().toString();
                                       final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
                                       Sup.startAnimation(animShake);
                                       password =  Password.getText().toString();
                                       Intent intent = new Intent(LoginEmployer.this, Signup.class);
                                       startActivity(intent);
                                   }

                               }
        );
    }

	
	//When called log user in
    public void OnClickLogin() {
        Password= (EditText) findViewById(R.id.password);
        Email = (EditText) findViewById(R.id.email);
        Client = (Button) findViewById(R.id.appCompatButtonLogin);
        Client.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                          final ProgressDialog progressDialog = new ProgressDialog(LoginEmployer.this);
                                          progressDialog.setMessage("Logging in");
                                          final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
                                          Client.startAnimation(animShake);
                                          email = Email.getText().toString();
                                          password =  Password.getText().toString();

                                     if(!email.isEmpty()&& !password.isEmpty()) {
                                         progressDialog.show();
                                              auth2.signInWithEmailAndPassword(email.trim(), password.trim())
                                                      .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                          @Override
                                                          public void onComplete(@NonNull Task<AuthResult> task) {

                                                              if (task.isSuccessful()) {
                                                                  SharedPreferences.Editor editor = sharedPref.edit();
                                                               progressDialog.dismiss();
                                                                  editor.putString(key, "Employee");
                                                                  editor.commit();
                                                                  Toast.makeText(LoginEmployer.this, "" +sharedPref.getString(key,"a"), Toast.LENGTH_SHORT).show();
                                                                  Intent intent = new Intent(LoginEmployer.this, Employee.class);
                                                                  startActivity(intent);

                                                              } else {
                                                                  progressDialog.dismiss();
                                                                  Toast.makeText(LoginEmployer.this, "No Account in the database matches " + Email.getText(), Toast.LENGTH_SHORT).show();
                                                              }
                                                          }
                                                      });
                                          }
                                          else
                                          {
                                              progressDialog.dismiss();
                                              Toast.makeText(LoginEmployer.this, "Please fill in all Fields" , Toast.LENGTH_SHORT).show();
                                          }




                                      }

                                  }
        );
    }

	
	//When back button is called kill application

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
