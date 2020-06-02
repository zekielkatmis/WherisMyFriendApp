package com.example.whereismyfriend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendProfiles extends AppCompatActivity {

    TextView tvFriendName, tvFriendHobbies1, tvFriendHobbies2, tvFriendHobbies3, friendBio;
    ImageView friendImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profiles);
        String email = getIntent().getStringExtra("email89");

        DbHelper helper = new DbHelper(this);

        tvFriendName = findViewById(R.id.friend_profile_name);
        tvFriendHobbies1 = findViewById(R.id.friend_hobbies1);
        tvFriendHobbies2 = findViewById(R.id.friend_hobbies2);
        tvFriendHobbies3 = findViewById(R.id.friend_hobbies3);
        friendBio = findViewById(R.id.friend_bio);
        friendImageView = findViewById(R.id.friend_profile_photo);

        tvFriendName.setText(helper.GetName(email));
        tvFriendHobbies1.setText(helper.geth1(email));
        tvFriendHobbies2.setText(helper.geth2(email));
        tvFriendHobbies3.setText(helper.geth3(email));
        friendBio.setText(helper.getStatus(email));
        friendImageView.setImageBitmap(helper.getImage(email));
    }
}
