package com.techsters.aasthaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.techsters.aasthaapp.Adapters.PagerAdapter;
import com.techsters.aasthaapp.Fragments.About_Us;
import com.techsters.aasthaapp.Fragments.THERAPIST_LIST;
import com.techsters.aasthaapp.Fragments.TIPS;
import com.techsters.aasthaapp.databinding.ActivityMainBinding;
import com.techsters.aasthaapp.databinding.ActivityTipsBinding;
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