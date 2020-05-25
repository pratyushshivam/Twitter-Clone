package com.example.twitterclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    EditText tweetEditText;
    String maintweet;
    private ArrayList<String> users;
    ArrayAdapter arrayAdapter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menus, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.logout:
                ParseUser.logOut();
                finish();
                return true;
            case R.id.tweet:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Send a Tweet");
                tweetEditText = new EditText(this);
                builder.setView(tweetEditText);
                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // Toast.makeText(HomeActivity.this, "Tweet is " + tweetEditText.getText().toString(), Toast.LENGTH_SHORT).show();
                        ParseObject tweet = new ParseObject("Tweet");
                        tweet.put("tweet", tweetEditText.getText().toString());
                        tweet.put("username", ParseUser.getCurrentUser().getUsername());
                        tweet.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e==null)
                                {
                                    Toast.makeText(HomeActivity.this, "Tweet was successfully uploaded", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(HomeActivity.this, "Tweet failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(HomeActivity.this, "Tweet not done", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                builder.show();
                maintweet= tweetEditText.getText().toString();
                return true;
            case R.id.viewFeed:
               Intent intent22 = new Intent(getApplicationContext(),Tweets.class);
               startActivity(intent22);
                return true;
            default:
                return false;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Users List");
        users = new ArrayList<String>();


        final ListView listView = findViewById(R.id.listView);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, users);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView checkedTextView = (CheckedTextView) view; // we need to know which texrView is checked kyuiki sasura whi next page me tweeets khulenge
                if (checkedTextView.isChecked()) {
                    Toast.makeText(HomeActivity.this, "Followed " + users.get(position) + "!", Toast.LENGTH_SHORT).show();
                    // means now we will add the details about the followed user
                    ParseUser.getCurrentUser().add("isFollowing", users.get(position));
                } else {
                    Toast.makeText(HomeActivity.this, "Unfollowed " + users.get(position) + "!", Toast.LENGTH_SHORT).show();
                    // now we have to remove
                    ParseUser.getCurrentUser().getList("isFollowing").remove(users.get(position));
                    List tempUsers = ParseUser.getCurrentUser().getList("isFollowing");
                    ParseUser.getCurrentUser().remove("isFollowing");
                    ParseUser.getCurrentUser().put("isFollowing", tempUsers);
                }
                ParseUser.getCurrentUser().saveInBackground();
            }


        });

        if (ParseUser.getCurrentUser() != null && ParseUser.getCurrentUser().getUsername()!=null) {
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername().toString());
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null && objects!=null) {
                        for (ParseUser user : objects) {
                            users.add(user.getUsername());
                        }
                        arrayAdapter.notifyDataSetChanged();
                        /*
                        for (String username : users) {

                            if (ParseUser.getCurrentUser().getList("isFollowing").contains(username)) {
                                listView.setItemChecked(users.indexOf(username), true);
                            }
                        }*/
                    }

                }
            });
        }
    }
}
