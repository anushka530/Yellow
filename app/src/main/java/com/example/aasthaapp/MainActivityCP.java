package com.example.aasthaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.aasthaapp.Adapters.UsersAdapter;
import com.example.aasthaapp.Adapters.UsersAdapterCP;
import com.example.aasthaapp.Models.User;
import com.example.aasthaapp.Models.UserCP;
import com.example.aasthaapp.databinding.ActivityMainCPBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivityCP extends AppCompatActivity {
    FirebaseAuth auth;
    ActivityMainCPBinding binding;
    RecyclerView recview;
    UsersAdapterCP adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("CHATS");

        auth= FirebaseAuth.getInstance();

        binding = ActivityMainCPBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recview = findViewById(R.id.recviewCP);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<UserCP> options =
                new FirebaseRecyclerOptions.Builder<UserCP>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("UsersCP"), UserCP.class)
                        .build();
        adapter=new UsersAdapterCP(options);
        recview.setAdapter(adapter);

    }
    @Override
    public void onBackPressed() {
        Intent intent= new Intent(MainActivityCP.this, aboutActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.settings:
                Toast.makeText(this, "settings is clicked", Toast.LENGTH_SHORT).show();

                break;

            case R.id.logout:
                auth.signOut();
                Intent intent = new Intent(MainActivityCP.this, SignInActivityCP.class);
                startActivity(intent);

                break;

        }
        return true;
    }

}