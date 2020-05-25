package com.example.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpPage extends AppCompatActivity {
    EditText nameEditText,passwordEditText,emailEditText,usernameEditText;
Button signUpButton;


public void signUp(View view)
{
        ParseUser user=new ParseUser();
        user.setUsername(usernameEditText.getText().toString());
        user.setPassword(passwordEditText.getText().toString());
        user.setEmail(emailEditText.getText().toString());
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e)
            {
                if(e==null)
                {
                    Toast.makeText(SignUpPage.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(SignUpPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        if(ParseUser.getCurrentUser()!=null)
        {
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        nameEditText=findViewById(R.id.nameEditText);
        passwordEditText=findViewById(R.id.passwordEditText);
        emailEditText=findViewById(R.id.emailEditText);
        usernameEditText=findViewById(R.id.usernameEditText);
        //signUp();
        signUpButton=findViewById(R.id.signUpButton);
    }
}
