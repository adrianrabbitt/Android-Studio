package com.example.adria.persontracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        OnClickLogin();
    }
    Button Login;
    EditText parentPassword;
   findLocation currloc;
    password pswd = new password();

    public void OnClickLogin()
    {
        Login = (Button) findViewById(R.id.button3);
        parentPassword= (EditText)findViewById(R.id.editText2);
        parentPassword.setText("");
        Login.setOnClickListener(new View.OnClickListener()
        {
                    public void onClick(View v)
                    {
                        currloc = new findLocation(Login.this);
                        if(parentPassword.getText().toString().equals(pswd.psswd)) // cheacking if the password is equal to Parent
                        {
                            Toast.makeText(Login.this,"Password is correct", Toast.LENGTH_SHORT).show();//Toast  created if true
                            Intent intent = new Intent(Login.this,Parent.class);
                            startActivity(intent);
                        }

                        else
                        {
                            Toast.makeText(Login.this,"Password is invalid",Toast.LENGTH_SHORT).show();//Toast created if false.
                        }

                    }

        }
        );
    }
}
