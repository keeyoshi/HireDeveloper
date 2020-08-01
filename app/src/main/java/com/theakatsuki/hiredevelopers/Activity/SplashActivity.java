package com.theakatsuki.hiredevelopers.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.theakatsuki.hiredevelopers.R;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mFirebaseAuth=FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();

      FirebaseUser mFirebaseUser= mFirebaseAuth.getCurrentUser();
      if(mFirebaseUser!=null){
          Intent intent = new Intent(getApplicationContext(),MainActivity.class);
          startActivity(intent);
          finish();
      }
      else{
          Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
          startActivity(intent);
          finish();
      }
    }
}

