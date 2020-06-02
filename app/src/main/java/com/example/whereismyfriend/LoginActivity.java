package com.example.whereismyfriend;

import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    TextView textView;
    EditText etEmail, etPassword;
    CheckBox cbRememberMe;

    static final String MY_PREFERENCES = "com.example.whereismyfriend.preferences";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textView = findViewById(R.id.link_login);
        etEmail = findViewById(R.id.input_email);
        etPassword =findViewById(R.id.input_password);
        cbRememberMe = findViewById(R.id.cbRemember);

        SharedPreferences preferences = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        String name = preferences.getString("name","");
        String password = preferences.getString("password", "");
        String cbchecked = preferences.getString("checked","");

        etEmail.setText(name);
        etPassword.setText(password);

        if (cbchecked.equals("1"))
        {
            cbRememberMe.setChecked(true);
        }
    }

    public void Login(View v){
        try {
            String email = etEmail.getText().toString().trim();
            String password1 = etPassword.getText().toString().trim();

            DbHelper helper = new DbHelper(this);
            if (helper.isValidEmail(email)){
                if(helper.LoginSystem(email, password1) == true){
                    if (cbRememberMe.isChecked()){
                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putString("name",email);
                        editor.putString("password",password1);
                        editor.putString("checked","1");
                        editor.commit();
                    }
                    Intent intent = new Intent(this, ProfileScreen.class);
                    intent.putExtra("email2", email);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Pleas enter valid Email", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public  void Register(View v){
        Intent intent =  new Intent(getApplicationContext(),RegisterPage.class);
        startActivity(intent);
    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Are you sure to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginActivity.this.finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
