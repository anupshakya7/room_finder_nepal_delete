package com.example.roomfindernepalasn;



import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class AboutUs_Page extends Fragment {

ViewFlipper logo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.about_us_page,container,false);

        int logoImages[] = {R.drawable.logoone, R.drawable.logotwo,R.drawable.logothree, R.drawable.logofour};

        logo=(ViewFlipper) view.findViewById(R.id.logoImage);
//        AnimationDrawable animationDrawable=(AnimationDrawable) logo.getDrawable();
//        animationDrawable.start();

        // for loop

        for(int logoimages: logoImages){
            flipperImages(logoimages);

        }

        return view;
    }

    public void flipperImages(int logoimages){
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(logoimages);

        logo.addView(imageView);
        logo.setFlipInterval(3000); //3sec
        logo.setAutoStart(true);


        //animation
        logo.setInAnimation(getContext(),android.R.anim.slide_in_left);
        logo.setOutAnimation(getContext(),android.R.anim.slide_out_right);
    }
}
