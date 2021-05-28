package com.example.aasthaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.aasthaapp.Adapters.CustomMusicAdapter;
import com.example.aasthaapp.Adapters.UsersAdapter;

import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity {
    private ArrayList<Music> arrayList;
    CustomMusicAdapter adapter;
    private ListView songList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        arrayList = new ArrayList<>();
        songList = (ListView) findViewById(R.id.song_list);
        arrayList.add(new Music("cam_giac_yeu", R.raw.cam_giac_yeu));
        arrayList.add(new Music("can_vong_tinh_yeh", R.raw.cau_vong_tinh_yeu));
        arrayList.add(new Music("tan", R.raw.tan));
        arrayList.add(new Music("instrumental ", R.raw.music1));
        arrayList.add(new Music("piano-in-the-great-hall", R.raw.music2));
        arrayList.add(new Music("deep_meditation", R.raw.music3));
        arrayList.add(new Music("inner_peace", R.raw.music5));
        arrayList.add(new Music("relaxing_bird_sound", R.raw.music6));
        arrayList.add(new Music("wqt_ki_baatein", R.raw.music7));
        arrayList.add(new Music("divine_maha_mantra", R.raw.music8));
        arrayList.add(new Music("relax", R.raw.music9));



        adapter = new CustomMusicAdapter(this,R.layout.custom_list,arrayList);
        songList.setAdapter(adapter);



    }
}