package com.example.aasthaapp.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.aasthaapp.Fragments.About_Us;
import com.example.aasthaapp.Fragments.THERAPIST_LIST;
import com.example.aasthaapp.Fragments.TIPS;

import java.util.ArrayList;

public class PagerAdapter extends FragmentPagerAdapter {
    public ArrayList<Fragment> fragmentList = new ArrayList<>();
    public ArrayList<String> fragmentTitle = new ArrayList<>();


    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
    public void addFragment(Fragment fragment, String title){
        fragmentList.add(fragment);
        fragmentTitle.add(title);

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }
}

