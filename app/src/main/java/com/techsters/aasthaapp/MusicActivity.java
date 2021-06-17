package com.techsters.aasthaapp;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.techsters.aasthaapp.Adapters.CustomMusicAdapter;

import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity {
    ArrayList<Music> arrayList;
    CustomMusicAdapter adapter;
    private ListView songList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        arrayList = new ArrayList<>();
        songList = (ListView) findViewById(R.id.song_list);
        arrayList.add(new Music("Tajdar-E-Haram", R.raw.tajdar));
        arrayList.add(new Music("orbital", R.raw.orbital));
        arrayList.add(new Music("monsoon", R.raw.monsoon));
        arrayList.add(new Music("moonchild", R.raw.moonchild));
        arrayList.add(new Music("piano-in-the-great-hall", R.raw.pianoingreathall));
        arrayList.add(new Music("deep_meditation", R.raw.deepmeditation));
        arrayList.add(new Music("inner_peace", R.raw.innerpeace));
        arrayList.add(new Music("relaxing_bird_sound", R.raw.birdsound));
        arrayList.add(new Music("wqt_ki_baatein", R.raw.wqtkibaatein));
        arrayList.add(new Music("divine_maha_mantra", R.raw.divinemahamantra));
        arrayList.add(new Music("relax", R.raw.relax));



        adapter = new CustomMusicAdapter(this,R.layout.custom_list,arrayList);
        songList.setAdapter(adapter);



    }
}