package com.example.anhyunjin.brunch;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static com.example.anhyunjin.brunch.FirstFragment.welcome;
import static com.example.anhyunjin.brunch.SecondFragment.write;
import static com.example.anhyunjin.brunch.ThirdFragment.user;
import static com.example.anhyunjin.brunch.FourthFragment.postcard;
import static com.example.anhyunjin.brunch.FourthFragment.a;
import static com.example.anhyunjin.brunch.FifthFragment.acrobatic;

public class Viewpager extends AppCompatActivity {
    private ViewPager vp;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        vp = (ViewPager) findViewById(R.id.viewpager);
        fab = (FloatingActionButton) findViewById(R.id.first_next_fab);

        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(0);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp.setCurrentItem(getItem(+1));
                switch (vp.getCurrentItem()) {
                    case 1:
                        welcome.playAnimation();
                        break;
                    case 2:
                        write.playAnimation();
                        break;
                    case 3:
                        user.playAnimation();
                        break;
                    case 4:
                        Log.d("intintint", String.valueOf(a));
                        case5();
                        break;
                    case 5:
                        acrobatic.playAnimation();
                        break;

                }
            }
        });
    }

    private int getItem(int i) {
        return vp.getCurrentItem() + i;
    }

    private class pagerAdapter extends FragmentPagerAdapter {
        pagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FirstFragment();
                case 1:
                    return new SecondFragment();
                case 2:
                    return new ThirdFragment();
                case 3:
                    return new FourthFragment();
                case 4:
                    return new FifthFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    private void case5() {
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_black_24dp));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int infoFirst = 1;
                SharedPreferences a = getSharedPreferences("a", MODE_PRIVATE);
                SharedPreferences.Editor editor = a.edit();
                editor.putInt("First", infoFirst);
                editor.commit();
                finish();
            }
        });
    }
}
