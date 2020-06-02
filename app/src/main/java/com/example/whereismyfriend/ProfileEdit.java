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

public class ProfileEdit extends AppCompatActivity{
    private ImageView imageView;
    EditText name, h1, h2, h3, status, emailHider;

    private static final int SELECT_PHOTO = 1;
    private static final int CAPTURE_PHOTO = 2;

    private ProgressDialog progressBar;
    private int progressBarStatus;
    private Handler progressBarHandler = new Handler();
    private boolean hasImageChanged = false;
    Bitmap thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        String email = getIntent().getStringExtra("email44");
        emailHider = findViewById(R.id.emailTutan);
        emailHider.setText(email);

        imageView = findViewById(R.id.imageProfile);

        if (ContextCompat.checkSelfPermission(ProfileEdit.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            imageView.setEnabled(false);
            ActivityCompat.requestPermissions(ProfileEdit.this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        } else {
            imageView.setEnabled(true);
        }

        name = findViewById(R.id.name);
        h1 = findViewById(R.id.h1);
        h2 = findViewById(R.id.h2);
        h3 = findViewById(R.id.h3);
        status = findViewById(R.id.status);

        DbHelper helper = new DbHelper(this);
        name.setText(helper.GetName(email));
        h1.setText(helper.geth1(email));
        h2.setText(helper.geth2(email));
        h3.setText(helper.geth3(email));
        status.setText(helper.getStatus(email));
        imageView.setImageBitmap(helper.getImage(email));
    }

    public void selectImage2(View v2){
        try {
            switch (v2.getId()) {
                case R.id.select_image:
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
                                            imageView.setImageResource(R.drawable.photo_yok);
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

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                imageView.setEnabled(true);
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
                    imageView.setImageBitmap(selectedImage);


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
        imageView.setMaxWidth(200);
        imageView.setImageBitmap(thumbnail);
    }

    public void updateProfile(View v){
        try {
            String email1 = emailHider.getText().toString().trim();
            String name1 = name.getText().toString().trim();
            String ho1 = h1.getText().toString().trim();
            String ho2 = h2.getText().toString().trim();
            String ho3 = h3.getText().toString().trim();
            String status1 = status.getText().toString().trim();

            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            Bitmap bitmap = imageView.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] image = baos.toByteArray();

            DbHelper helper = new DbHelper(this);
            helper.UpdateProfile(email1, name1, ho1, ho2, ho3, status1, image);
            helper.updatePerson(email1, name1);
            Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ProfileScreen.class);
            startActivity(intent);
        }

        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
