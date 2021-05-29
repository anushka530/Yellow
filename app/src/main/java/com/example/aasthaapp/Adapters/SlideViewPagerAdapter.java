package com.example.aasthaapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.aasthaapp.IntroActivity;
import com.example.aasthaapp.PostActivity;
import com.example.aasthaapp.R;

public class SlideViewPagerAdapter extends PagerAdapter {

    Context ctx;

    public SlideViewPagerAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull  View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater= (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_screen, container, false);
        ImageView logo1= view.findViewById(R.id.logo1);
        ImageView ind1= view.findViewById(R.id.ind1);
        ImageView ind2= view.findViewById(R.id.ind2);
        ImageView ind3= view.findViewById(R.id.ind3);

        TextView title = view.findViewById(R.id.titleOnboarding);
        TextView desc = view.findViewById(R.id.description);

        ImageView next= view.findViewById(R.id.next);
        ImageView back= view.findViewById(R.id.back);

        Button btnGetStarted = view.findViewById(R.id.btn_getStart);
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, PostActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(i);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntroActivity.viewPager.setCurrentItem(position+1);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntroActivity.viewPager.setCurrentItem(position-1);

            }
        });


        switch (position){
            case 0:
                logo1.setImageResource(R.drawable.together);
                ind1.setImageResource(R.drawable.selected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.unselected);
                title.setText("Update username before posting anything");
                desc.setText("Please go to settings and update your username by clicking on the save button before posting anything here. We get you, no need to struggle in silence anymore.");
                back.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);
                break;

            case 1:
                logo1.setImageResource(R.drawable.writing);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.selected);
                ind3.setImageResource(R.drawable.unselected);

                title.setText("Post here or Go Through Others' Posts  ");
                desc.setText("You can post stuffs related to mental health, it could be some write-ups, poems , pictures anything, just make sure to give warning ahead of any sensitive content.");


                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                break;

            case 2:
                logo1.setImageResource(R.drawable.meditation);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.selected);

                title.setText("No Comment or Like Feature");
                desc.setText("We have avoided comment and like feature as it does not help our motive in anyway.");


                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
                break;

        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
