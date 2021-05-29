package com.example.aasthaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TableLayout;

import com.example.aasthaapp.Adapters.FragmentAdapter;
import com.example.aasthaapp.Adapters.PagerAdapter;
import com.example.aasthaapp.Fragments.About_Us;
import com.example.aasthaapp.Fragments.THERAPIST_LIST;
import com.example.aasthaapp.Fragments.TIPS;
import com.example.aasthaapp.databinding.ActivityMainBinding;
import com.example.aasthaapp.databinding.ActivityTipsBinding;
import com.google.android.material.tabs.TabLayout;

public class TipsActivity extends AppCompatActivity  {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        getSupportActionBar().hide();
        tabLayout = (TabLayout) findViewById(R.id.Tablayout);
        viewPager = (ViewPager) findViewById(R.id.ViewPager);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);




    }
    public  void setUpViewPager(ViewPager viewPager){
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new TIPS(),"TIPS");
        pagerAdapter.addFragment(new THERAPIST_LIST(),"THERAPIST LIST");
        pagerAdapter.addFragment(new About_Us(),"ABOUT US");
        viewPager.setAdapter(pagerAdapter);

    }
}