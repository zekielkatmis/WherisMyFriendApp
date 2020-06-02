package com.example.whereismyfriend;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsScreen extends AppCompatActivity {
    EditText editText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);

        String email = getIntent().getStringExtra("email34");

        editText = findViewById(R.id.tasiyici);
        editText.setText(email);

    }
    public void logOut(View v){
        Intent intent = new Intent(getApplicationContext(), LogOut.class);
        startActivity(intent);
    }
    public void profileEdit(View v){
        Intent intent = new Intent(this, ProfileEdit.class);
        String moveToNext2 = editText.getText().toString().trim();
        intent.putExtra("email44", moveToNext2);
        startActivity(intent);
    }
    public void contactUs(View v){
        Intent intent = new Intent(getApplicationContext(), ContactUs.class);
        startActivity(intent);
    }
    public void googleMaps(View v){
        Intent intent = new Intent(getApplicationContext(), GoogleMaps.class);
        startActivity(intent);
    }
    public void currentLocation(View v){
        Intent intent = new Intent(this, ActivityLocation.class);
        startActivity(intent);
    }
}
