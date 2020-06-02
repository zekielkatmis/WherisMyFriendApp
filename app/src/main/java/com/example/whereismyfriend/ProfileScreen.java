package com.example.whereismyfriend;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class ProfileScreen extends AppCompatActivity{
    TextView tvUserName, tvHobbies1, tvHobbies2, tvHobbies3, bio;
    EditText emailTutucu;
    ImageView imageView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);
        String email = getIntent().getStringExtra("email2");

        DbHelper helper = new DbHelper(this);

        tvUserName = findViewById(R.id.user_profile_name);
        tvUserName.setText(helper.GetName(email));

        emailTutucu = findViewById(R.id.emailTutucu);
        emailTutucu.setText(email);

        imageView = findViewById(R.id.user_profile_photo);
        imageView.setImageBitmap(helper.getImage(email));

        tvHobbies1 = findViewById(R.id.hobbies1);
        tvHobbies1.setText(helper.geth1(email));

        tvHobbies2 = findViewById(R.id.hobbies2);
        tvHobbies2.setText(helper.geth2(email));

        tvHobbies3 = findViewById(R.id.hobbies3);
        tvHobbies3.setText(helper.geth3(email));

        bio = findViewById(R.id.user_profile_short_bio);
        bio.setText(helper.getStatus(email));
    }

    public void AboutUs(View v){
        Intent intent = new Intent(getApplicationContext(), AboutUs.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                String moveToNext = emailTutucu.getText().toString().trim();
                Intent intent = new Intent(this, SettingsScreen.class);
                intent.putExtra("email34", moveToNext);
                startActivity(intent);
                break;

            case R.id.addfriend:
                String moveToNext1 = emailTutucu.getText().toString().trim();
                Intent i = new Intent(this, CloseFriends.class);
                i.putExtra("email47",moveToNext1);
                startActivity(i);
                break;
            case R.id.logout:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Are you sure to Logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ProfileScreen.this.finish();
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
