package com.example.whereismyfriend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MyViewHolder> {

    Context context;

    ArrayList user_name, user_hobbies1, user_hobbies2, user_hobbies3, user_email;

    FriendsAdapter(Context context,
                   ArrayList user_name,
                   ArrayList user_hobbies1,
                   ArrayList user_hobbies2,
                   ArrayList user_hobbies3,
                   ArrayList user_email){
        this.context = context;
        this.user_name = user_name;
        this.user_hobbies1 = user_hobbies1;
        this.user_hobbies2 = user_hobbies2;
        this.user_hobbies3 = user_hobbies3;
        this.user_email = user_email;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.userName.setText(String.valueOf(user_name.get(position)));
        holder.userHobbies1.setText(String.valueOf(user_hobbies1.get(position)));
        holder.userHobbies2.setText(String.valueOf(user_hobbies2.get(position)));
        holder.userHobbies3.setText(String.valueOf(user_hobbies3.get(position)));
        holder.userEmail.setText(String.valueOf(user_email.get(position)));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, FriendProfiles.class);
                intent.putExtra("email89", String.valueOf(user_email.get(position)));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return user_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout parentLayout;
        TextView userName, userHobbies1, userHobbies2, userHobbies3, userEmail;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.name_text);
            userHobbies1 = itemView.findViewById(R.id.user_hobbies1);
            userHobbies2 = itemView.findViewById(R.id.user_hobbies2);
            userHobbies3 = itemView.findViewById(R.id.user_hobbies3);
            userEmail = itemView.findViewById(R.id.my_row_email);
            parentLayout = itemView.findViewById(R.id.my_row);
        }
    }
}
