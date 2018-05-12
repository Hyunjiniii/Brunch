package com.example.anhyunjin.brunch;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static com.example.anhyunjin.brunch.FirstFragment.welcome;
import static com.example.anhyunjin.brunch.SecondFragment.write;
import static com.example.anhyunjin.brunch.ThirdFragment.user;

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
                    case 2:
                        write.playAnimation();
                    case 3:
                        user.playAnimation();
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
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
