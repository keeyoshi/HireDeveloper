package com.theakatsuki.hiredevelopers.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.theakatsuki.hiredevelopers.Model.User;
import com.theakatsuki.hiredevelopers.R;

public class RegisterActivity extends AppCompatActivity {
    EditText username, password,fullname,phoneNumber, email,work;
    Button btnRegister;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.country);
        progressBar= findViewById(R.id.progressbar);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        fullname = findViewById(R.id.name);
        work = findViewById(R.id.workPlace);
        phoneNumber = findViewById(R.id.phonenumber);
        btnRegister = findViewById(R.id.register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                final String userName = username.getText().toString();
                final String pass = password.getText().toString();
                final String name = fullname.getText().toString();
                final String workP = work.getText().toString();
                final String emailAddress = email.getText().toString();
                final String  number= phoneNumber.getText().toString();
                final String image = "Default";

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(RegisterActivity.this, "Please write your name....", Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(userName)){
                    Toast.makeText(RegisterActivity.this, "Please write your country name..... ", Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(number)){
                    Toast.makeText(RegisterActivity.this, "Please write your Phone Number ", Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(workP)){
                    Toast.makeText(RegisterActivity.this, "Please write your Work place......", Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(emailAddress)){
                    Toast.makeText(RegisterActivity.this, "Please write your Email Address.... ", Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(pass)){
                    Toast.makeText(RegisterActivity.this, "Please write your Password.... ", Toast.LENGTH_SHORT).show();
                }

                else {
                    firebaseAuth.createUserWithEmailAndPassword(emailAddress, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        User user = new User(FirebaseAuth.getInstance().getCurrentUser().getUid(), name, number, workP, userName, emailAddress, pass, image);
                                        FirebaseDatabase.getInstance().getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user);
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(), "User created", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }


                            });
                }
            }
        });
    }
}