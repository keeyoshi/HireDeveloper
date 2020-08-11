package com.theakatsuki.hiredevelopers.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.theakatsuki.hiredevelopers.R;

public class ImagesliderActivity extends AppCompatActivity {
ViewFlipper viewFlipper;
Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageslider_acitivity);
        button=findViewById(R.id.btnjoin);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(ImagesliderActivity.this, LoginActivity.class);
                    startActivity(intent);

            }
        });

       int imgarray[]= {R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four};
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