package com.example.aasthaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.aasthaapp.databinding.ActivityAboutBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class aboutActivity extends AppCompatActivity {
    ActivityAboutBinding binding;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(aboutActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        binding.btnCare.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i= new Intent(aboutActivity.this, theripstActivity.class);
            startActivity(i);
        }
    });

        binding.btnX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(aboutActivity.this, knowusActivity.class);
                startActivity(i);
            }
        });


        binding.btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(aboutActivity.this, IntroActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
