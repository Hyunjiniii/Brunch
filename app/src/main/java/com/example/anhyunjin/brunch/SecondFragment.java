package com.example.anhyunjin.brunch;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.airbnb.lottie.LottieAnimationView;

public class SecondFragment extends android.support.v4.app.Fragment {
    public static LottieAnimationView write;

    public SecondFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.activity_second_fragment, container, false);
        write = view.findViewById(R.id.write_lottie);
//        write.playAnimation();
        write.loop(true);
        return view;
    }
}
