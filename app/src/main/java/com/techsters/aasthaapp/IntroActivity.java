package com.techsters.aasthaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.techsters.aasthaapp.Adapters.SlideViewPagerAdapter;

public class IntroActivity extends AppCompatActivity {

    public static ViewPager viewPager;
    SlideViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        getSupportActionBar().hide();

        viewPager= findViewById(R.id.viewpagerOnboarding);
        adapter=new SlideViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        if(isOpenAlready()){
            Intent i = new Intent(IntroActivity.this, PostActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
        else{
            SharedPreferences.Editor editor=getSharedPreferences("slide", MODE_PRIVATE).edit();
            editor.putBoolean("slide", true);
            editor.commit();
        }

    }

    private boolean isOpenAlready() {

        SharedPreferences sharedPreferences = getSharedPreferences("slide",MODE_PRIVATE);
        boolean result = sharedPreferences.getBoolean("slide", false);
        return result;

    }
}