package com.example.whereismyfriend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class RegisterPage extends AppCompatActivity {
    EditText etName,etAddress,etEmail,etNumber,etPassword,etRePassword;

    private ImageView profileImageView;

    private static final int SELECT_PHOTO = 1;
    private static final int CAPTURE_PHOTO = 2;

    private ProgressDialog progressBar;
    private int progressBarStatus;
    private Handler progressBarHandler = new Handler();
    //private boolean hasImageChanged = false;
    Bitmap thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        profileImageView = findViewById(R.id.profileImageView);
        etName =findViewById(R.id.input_name);
        etAddress=findViewById(R.id.input_address);
        etEmail=findViewById(R.id.input_email);
        etNumber=findViewById(R.id.input_mobile);
        etPassword=findViewById(R.id.input_password);
        etRePassword=findViewById(R.id.input_reEnterPassword);

        if (ContextCompat.checkSelfPermission(RegisterPage.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            profileImageView.setEnabled(false);
            ActivityCompat.requestPermissions(RegisterPage.this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        } else {
            profileImageView.setEnabled(true);
        }
    }

    public void Register(View v){
        try {
            DbHelper helper = new DbHelper(this);

            profileImageView.setDrawingCacheEnabled(true);
            profileImageView.buildDrawingCache();
            Bitmap bitmap = profileImageView.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] image = baos.toByteArray();

            String name = etName.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String number = etNumber.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String repassword = etRePassword.getText().toString().trim();

                if (helper.isValidEmail(email) && helper.isValidPassword(password)) {
                    if (helper.chkEmail(email) == true) {
                        if (password.equals(repassword)){
                            String result = helper.PersonAdd(name, address, email, number, password);
                            helper.UserCreateProfile(email, name, image, address);

                            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(this, "This mail is already exists please enter another email.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this, "Pleas enter valid Email and password.", Toast.LENGTH_SHORT).show();
                }
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void selectImage1(View v2){
        try {
            switch (v2.getId()) {
                case R.id.pick_image:
                    new MaterialDialog.Builder(this)
                            .title("Set Your Image")
                            .items(R.array.uploadImages)
                            .itemsIds(R.array.itemIds)
                            .itemsCallback(new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                    switch (which) {
                                        case 0:
                                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                            photoPickerIntent.setType("image/*");
                                            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                                            break;
                                        case 1:
                                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            startActivityForResult(intent, CAPTURE_PHOTO);
                                            break;
                                        case 2:
                                            profileImageView.setImageResource(R.drawable.ic_person_black_24dp);
                                            break;
                                    }
                                }
                            }).show();
                    break;
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                profileImageView.setEnabled(true);
            }
        }
    }

    public void setProgressBar(){
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Please wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        progressBarStatus = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressBarStatus < 100){
                    progressBarStatus += 30;

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    progressBarHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                }
                if (progressBarStatus >= 100) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.dismiss();
                }

            }
        }).start();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SELECT_PHOTO){
            if(resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    //set Progress Bar
                    setProgressBar();
                    //set profile picture form gallery
                    profileImageView.setImageBitmap(selectedImage);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(requestCode == CAPTURE_PHOTO){
            if(resultCode == RESULT_OK) {
                onCaptureImageResult(data);
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");

        //set Progress Bar
        setProgressBar();
        //set profile picture form camera
        profileImageView.setMaxWidth(200);
        profileImageView.setImageBitmap(thumbnail);
    }
}
