package com.example.whereismyfriend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LogOut extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout_permission);
    }
    public void exit(View v){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
    public void back(View v){
        Intent intent = new Intent(getApplicationContext(), SettingsScreen.class);
        startActivity(intent);
    }
}

