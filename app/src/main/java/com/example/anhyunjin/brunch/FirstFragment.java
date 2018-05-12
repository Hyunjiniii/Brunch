package com.example.anhyunjin.brunch;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;

public class FirstFragment extends android.support.v4.app.Fragment {
    public static LottieAnimationView welcome;

    public FirstFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.activity_first_fragment, container, false);
        welcome = view.findViewById(R.id.welcome_lottie);
        welcome.playAnimation();
        welcome.loop(true);

        return view;
    }

}
