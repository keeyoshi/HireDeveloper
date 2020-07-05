package com.theakatsuki.hiredevelopers.ActivityPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    EditText username, password,fullname,phoneNumber, email;
    Button btnRegister;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.username);
        progressBar= findViewById(R.id.progressbar);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        fullname = findViewById(R.id.name);
        phoneNumber = findViewById(R.id.phonenumber);
        btnRegister = findViewById(R.id.register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                final String userName = username.getText().toString();
                final String pass = password.getText().toString();
                final String name = fullname.getText().toString();
                final String emailaddress = email.getText().toString();
                final String  number= phoneNumber.getText().toString();
                firebaseAuth.createUserWithEmailAndPassword(emailaddress,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if( task.isSuccessful())
                                {
                                    User user = new User(FirebaseAuth.getInstance().getCurrentUser().getUid(),name,number,userName,emailaddress,pass);
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user);
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "User created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }


                        });
            }
        });
    }
}