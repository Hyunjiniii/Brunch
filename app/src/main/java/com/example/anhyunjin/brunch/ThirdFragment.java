package com.example.anhyunjin.brunch;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;

public class ThirdFragment extends Fragment {
    public static LottieAnimationView user;

    public ThirdFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.activity_third_fragment, container, false);
        user = view.findViewById(R.id.user_lottie);
//        user.playAnimation();
        user.loop(true);

        return view;
    }
}
