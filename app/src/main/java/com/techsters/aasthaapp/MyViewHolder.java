package com.techsters.aasthaapp;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    CircleImageView profileImage;
    ImageView postImage;
    TextView username, postDesc;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        profileImage = itemView.findViewById(R.id.profileImagePost);
        postImage = itemView.findViewById(R.id.postImage);
        postDesc = itemView.findViewById(R.id.postDesc);
        username = itemView.findViewById(R.id.profileusernamePost);

    }
}
