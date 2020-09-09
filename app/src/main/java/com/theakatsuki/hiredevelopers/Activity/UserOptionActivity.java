package com.theakatsuki.hiredevelopers.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.theakatsuki.hiredevelopers.R;

public class UserOptionActivity extends AppCompatActivity {

    LinearLayout userLinear, developerLinear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_option);
        getSupportActionBar().setTitle("Registration selection");
        userLinear= findViewById(R.id.UserLinear);
        developerLinear= findViewById(R.id.DeveloperLinear);

        userLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                intent.putExtra("As","User");
                startActivity(intent);
            }
        });
        developerLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                intent.putExtra("As","Developer");
                startActivity(intent);
            }
        });
    }
}