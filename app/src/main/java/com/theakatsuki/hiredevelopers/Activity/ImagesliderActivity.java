package com.theakatsuki.hiredevelopers.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.theakatsuki.hiredevelopers.R;

public class ImagesliderActivity extends AppCompatActivity {
ViewFlipper viewFlipper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageslider_acitivity);

       int imgarray[]= {R.drawable.cover2,R.drawable.cover3,R.drawable.cover5,R.drawable.coverone};
        viewFlipper= findViewById(R.id.viewflipper);
        for(int i=0;i<imgarray.length;i++)
            showimage(imgarray[i]);

    }
    public void showimage(int img)
    {
        ImageView imageView =new ImageView(this);
        imageView.setBackgroundResource(img);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        //viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }

}