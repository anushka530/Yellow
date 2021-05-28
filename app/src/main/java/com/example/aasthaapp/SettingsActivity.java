package com.example.aasthaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aasthaapp.Models.User;
import com.example.aasthaapp.databinding.ActivitySettingsBinding;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class SettingsActivity extends AppCompatActivity  {

    ActivitySettingsBinding binding;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        binding.logoutSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SettingsActivity.this)
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                auth.signOut();
                                Intent intent= new Intent(SettingsActivity.this,SignInActivity.class);
                                startActivity(intent);

                            }
                        }).setNeutralButton("Help", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SettingsActivity.this, "Press Yes to logout", Toast.LENGTH_SHORT).show();
                    }
                }) .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                }) .show();
            }
        });


        binding.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        binding.saveButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String username = binding.etUsername.getText().toString();

                // to update value
                HashMap<String, Object> obj = new HashMap<>();
                obj.put("username", username);


                database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                        .updateChildren(obj);

                Toast.makeText(SettingsActivity.this, "profile updated", Toast.LENGTH_SHORT).show();

            }

        });


        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User users = snapshot.getValue(User.class);
                        Picasso.get()
                                .load(users.getProfilepic())
                                .placeholder(R.drawable.uploadimg)
                                .into(binding.profileImage);


                        binding.etUsername.setText(users.getUsername());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(SettingsActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null) {

            Uri sFile = data.getData();
            binding.profileImage.setImageURI(sFile);

            final StorageReference ref = storage.getReference().child("profile_pictures")
                    .child(FirebaseAuth.getInstance().getUid());

            ref.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                    .child("profilepic").setValue(uri.toString());
                            Toast.makeText(SettingsActivity.this, "profile picture updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });


        }
    }

}