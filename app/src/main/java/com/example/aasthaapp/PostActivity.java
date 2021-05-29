package com.example.aasthaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Toast;

import com.example.aasthaapp.Utils.posts;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;


public class PostActivity extends AppCompatActivity {

    ImageView addImagePost, sendImagePost;
    EditText inputPostDesc;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mUserRef, postRef;
    Uri imageUri;
    String profileImageUrlV, usernameV;
    ProgressDialog mLoadingBar;
    StorageReference postImageRef;
    FirebaseRecyclerAdapter<posts, MyViewHolder> adapter;
    FirebaseRecyclerOptions<posts> options;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        postRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        addImagePost = findViewById(R.id.addImagePost);
        sendImagePost = findViewById(R.id.sned_post_imageView);
        inputPostDesc = findViewById(R.id.inputAddPost);
        mLoadingBar = new ProgressDialog(this);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postImageRef = FirebaseStorage.getInstance().getReference().child("PostImages");
        floatingActionButton = findViewById(R.id.floatingActionButton);






        sendImagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPost();

            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PostActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });
        addImagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(PostActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(33);

            }
        });
        LoadPost();

    }



    private void LoadPost() {
        options = new FirebaseRecyclerOptions.Builder<posts>().setQuery(postRef, posts.class).build();
        adapter = new FirebaseRecyclerAdapter<posts, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull posts model) {
                holder.postDesc.setText(model.getPostDesc());
                holder.username.setText(model.getUsername());
                Picasso.get().load(model.getPostImageUrl()).into(holder.postImage);
                Picasso.get().load(model.getProfilepic()).placeholder(R.drawable.user).into(holder.profileImage);


            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_post, parent, false);
                return new MyViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mUser == null) {
            SendUserToLoginActivity();
        } else {
            mUserRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    try{
                        if (snapshot.exists()) {
                            profileImageUrlV = snapshot.child("profilepic").getValue().toString();
                            usernameV = snapshot.child("username").getValue().toString();
                        }

                    }
                    catch (NullPointerException ignored){

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(PostActivity.this, "Sorry!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void SendUserToLoginActivity() {
        Intent intent = new Intent(PostActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 33 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            addImagePost.setImageURI(imageUri);


        }

    }

    private void AddPost() {
        String postDesc = inputPostDesc.getText().toString();
        if (postDesc.isEmpty()) {
            inputPostDesc.setError("please write something");
        } else if (imageUri == null) {
            Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show();

        } else {
            mLoadingBar.setTitle("Adding Post");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            String strDate = formatter.format(date);

            postImageRef.child(mUser.getUid() + strDate).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        postImageRef.child(mUser.getUid() + strDate).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                HashMap hashMap = new HashMap();
                                hashMap.put("datePost", strDate);
                                hashMap.put("postImageUrl", uri.toString());
                                hashMap.put("postDesc", postDesc);
                                hashMap.put("profilepic", profileImageUrlV);
                                hashMap.put("username", usernameV);
                                postRef.child(mUser.getUid() + strDate).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()) {
                                            mLoadingBar.dismiss();
                                            Toast.makeText(PostActivity.this, "post added", Toast.LENGTH_SHORT).show();
                                            addImagePost.setImageResource(R.drawable.ic_add_post_image);
                                            inputPostDesc.setText("");
                                        } else {
                                            Toast.makeText(PostActivity.this, "" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                            }

                        });
                    } else {
                        mLoadingBar.dismiss();
                        Toast.makeText(PostActivity.this, "" + task.getException().toString(), Toast.LENGTH_SHORT).show();


                    }

                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(PostActivity.this, aboutActivity.class);
        startActivity(i);
        super.onBackPressed();
    }
}