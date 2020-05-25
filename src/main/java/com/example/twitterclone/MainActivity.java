package com.example.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.LogOutCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    TextView signUpTextView;
    TextView usernameTextView;
    TextView passswordEditText;
    Button loginButton;


    public void logOut(View view)
    {
        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                Toast.makeText(MainActivity.this, "Successfully logged out user " + ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_SHORT).show();
            }
        });
    }

        public void login2(View view)
        {
            login();
        }
    public void login()
    {
        ParseUser.logInInBackground(usernameTextView.getText().toString(), passswordEditText.getText().toString(), new LogInCallback() {
        @Override
        public void done(ParseUser user, ParseException e) {
            if(e==null) {
                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();


            }

        }
    });
       /* if(ParseUser.getCurrentUser()!=null)
        {
            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signUpTextView=findViewById(R.id.signUpTextView);
        passswordEditText=findViewById(R.id.passwordEditText);
        usernameTextView=findViewById(R.id.usernameEditText);
        if(ParseUser.getCurrentUser()!=null)
        {
            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
        }
        loginButton=findViewById(R.id.loginButton);
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SignUpPage.class);
                startActivity(intent);

            }
        });




    }
    /*public void signUp()
    {
        ParseUser user=new ParseUser();
        user.setUsername(usernameTextView.getText().toString());
        user.setPassword(passswordEditText.getText().toString());
      //  user.setEmail(emailEditText.getText().toString());
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e)
            {
                if(e==null)
                {
                    Toast.makeText(MainActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }*/
}
