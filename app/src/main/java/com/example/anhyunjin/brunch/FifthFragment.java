package com.example.anhyunjin.brunch;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;

public class FifthFragment extends Fragment {
    public static LottieAnimationView acrobatic;

    public FifthFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.activity_fifth_fragment, container, false);
        acrobatic = view.findViewById(R.id.acrobatics_lottie);
        acrobatic.playAnimation();
        acrobatic.setImageAssetsFolder("images/");
        acrobatic.loop(true);

        return view;
    }
}
