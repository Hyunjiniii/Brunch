package com.example.anhyunjin.brunch;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;

public class FourthFragment extends Fragment {
    public static LottieAnimationView postcard;
    public static int a = 1;

    public FourthFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.activity_fourth_fragment, container, false);
        postcard = view.findViewById(R.id.postcard_lottie);
        postcard.playAnimation();
        postcard.loop(true);

        return view;
    }
}
