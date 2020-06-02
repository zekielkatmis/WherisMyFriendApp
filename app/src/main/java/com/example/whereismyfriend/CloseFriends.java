package com.example.whereismyfriend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CloseFriends extends AppCompatActivity {

    RecyclerView showCloseFriends;
    EditText editText;

    ArrayList<String> user_name, user_hobbies1, user_hobbies2, user_hobbies3, user_email;
    FriendsAdapter friendsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_friends);

        String email = getIntent().getStringExtra("email47");

        showCloseFriends = findViewById(R.id.showCloseFriends);

        editText = findViewById(R.id.email1);
        editText.setText(email);

        user_name = new ArrayList<>();
        user_hobbies1 = new ArrayList<>();
        user_hobbies2 = new ArrayList<>();
        user_hobbies3 = new ArrayList<>();
        user_email = new ArrayList<>();
        displayFriends();

        friendsAdapter = new FriendsAdapter(this, user_name, user_hobbies1, user_hobbies2,
                user_hobbies3, user_email);
        showCloseFriends.setAdapter(friendsAdapter);
        showCloseFriends.setLayoutManager(new LinearLayoutManager(this));
    }

    void displayFriends(){
        DbHelper helper = new DbHelper(this);
        String email = editText.getText().toString().trim();
        //String address = helper.getAddress(email);

        Cursor cursor = helper.getFriends();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                user_name.add(cursor.getString(1));
                user_hobbies1.add(cursor.getString(3));
                user_hobbies2.add(cursor.getString(4));
                user_hobbies3.add(cursor.getString(5));
                user_email.add(cursor.getString(0));
            }
        }
    }
}
