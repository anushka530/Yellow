package com.example.aasthaapp.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aasthaapp.Models.UserCP;
import com.example.aasthaapp.R;
import com.example.aasthaapp.chatDetailActivityCp;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UsersAdapterCP extends FirebaseRecyclerAdapter<UserCP,UsersAdapterCP.viewHolder> {



    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UsersAdapterCP(@NonNull FirebaseRecyclerOptions<UserCP> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UsersAdapterCP.viewHolder holder, int position, @NonNull UserCP users) {
        Picasso.get().load(users.getProfilepic()).placeholder(R.drawable.user).into(holder.image);
        holder.userName.setText(users.getUsername());

        FirebaseDatabase.getInstance().getReference().child("chats").child(FirebaseAuth.getInstance().getUid() + users.getUserId())
                .orderByChild("timestamp")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChildren()) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                holder.lastMessage.setText(snapshot1.child("message").getValue().toString());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), chatDetailActivityCp.class);
                intent.putExtra("userId", users.getUserId());
                intent.putExtra("profilePic", users.getProfilepic());
                intent.putExtra("username", users.getUsername());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.samle_user_cp, parent, false);

        return new viewHolder(view);
    }
    class viewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView userName, lastMessage;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            image= itemView.findViewById(R.id.profile_imageCP);
            userName= itemView.findViewById(R.id.usernameCP);
            lastMessage= itemView.findViewById(R.id.LastMessageCP);



        }
    }

}




