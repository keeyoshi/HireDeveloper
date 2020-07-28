package com.theakatsuki.hiredevelopers.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
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
import com.theakatsuki.hiredevelopers.R;

public class LoginActivity extends AppCompatActivity {
    EditText etusername, etpassword;
    Button btnLogin,btnCreateNewAccount;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth.getInstance().signOut();
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);

        progressBar= findViewById(R.id.progressbar);
        etusername = findViewById(R.id.etemail);
        etpassword = findViewById(R.id.etpassword);
        etusername.setText("koc@gmail.com");
        etpassword.setText("password");
        btnLogin = findViewById(R.id.btnLogin);

        btnCreateNewAccount = findViewById(R.id.btncreateNewUser);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                String email = etusername.getText().toString();
                String password = etpassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    etusername.setError("Email Not entered");
                    etusername.requestFocus();
                 }
                else if(TextUtils.isEmpty(password)){
                    etpassword.setError("Password not Entered");
                    etpassword.requestFocus();
                 }
                else if(!email.matches(emailPattern)){
                    etusername.setError("Email format incorrect");
                    etusername.requestFocus();
                }

                else if(etpassword.length()<8){
                    etpassword.setError("Password length at least 8 characters");
                    etpassword.requestFocus();
                }

                else {

                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        progressBar.setVisibility(View.GONE);
                                        finish();

                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                                        vibrator.vibrate(500);
                                    }
                                }
                            });
                }
            }
        });

        btnCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}