package com.example.aasthaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.aasthaapp.databinding.ActivityAboutBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class aboutActivity<c> extends AppCompatActivity {
    ActivityAboutBinding binding;
    FirebaseAuth auth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        binding.btnabuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(firstStart){
                    Intent intent = new Intent(aboutActivity.this, signUPActivity.class);
                    startActivity(intent);

                }
                else{
                    Intent intent = new Intent(aboutActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("firstStart", false);
                editor.apply();


            }
        });
        SharedPreferences prefscp = getSharedPreferences("prefscp", MODE_PRIVATE);
        boolean firstStartcp = prefscp.getBoolean("firstStartcp", true);
        binding.btncareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(firstStartcp){
                    Intent intent = new Intent(aboutActivity.this, signUPActivityCP.class);
                    startActivity(intent);

                }
                else{
                    Intent intent = new Intent(aboutActivity.this, MainActivityCP.class);
                    startActivity(intent);
                }

                SharedPreferences prefscp = getSharedPreferences("prefscp", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefscp.edit();
                editor.putBoolean("firstStartcp", false);
                editor.apply();

            }

        });

    }


}