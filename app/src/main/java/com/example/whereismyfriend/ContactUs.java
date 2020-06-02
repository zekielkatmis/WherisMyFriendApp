package com.example.whereismyfriend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity {

    EditText etTopic, etDetails, etEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        etTopic = findViewById(R.id.etTopic);
        etDetails = findViewById(R.id.etDetails);
        etEmail = findViewById(R.id.etEmail);
    }

    public void AddContact(View view){
        try{
            DbHelper helper = new DbHelper(this);

            String topic = etTopic.getText().toString().trim();
            String details = etDetails.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if(helper.isValidEmail(email)){
                String result = helper.ContactAdd(topic, details, email);

                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), ProfileScreen.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Please enter valid email.", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage() , Toast.LENGTH_SHORT).show();
        }
    }
}
