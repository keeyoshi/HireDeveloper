package com.theakatsuki.hiredevelopers.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theakatsuki.hiredevelopers.Adapter.HomeAdapter;
import com.theakatsuki.hiredevelopers.Adapter.ProfileAdapter;
import com.theakatsuki.hiredevelopers.Model.Events;
import com.theakatsuki.hiredevelopers.Model.User;
import com.theakatsuki.hiredevelopers.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {



    CircleImageView circleImageView;
    TextView name, post, followers,following;
    ImageView home, job, about;
    RecyclerView recyclerView,jobRecyclerView;
    String userId;
    List<Events> events;
    FirebaseUser firebaseUser;
    ProfileAdapter profileAdapter;
    Button btnEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btnEdit= findViewById(R.id.btnEdit);
        circleImageView= findViewById(R.id.proImage);
        name= findViewById(R.id.proName);
        post= findViewById(R.id.proPost);
        followers= findViewById(R.id.proFollowers);
        following= findViewById(R.id.proFollowing);
        home= findViewById(R.id.proHome);
        job= findViewById(R.id.proWork);
        about= findViewById(R.id.proAbout);
        recyclerView= findViewById(R.id.proHomeRecyclerView);
        jobRecyclerView= findViewById(R.id.jobRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Intent intent = getIntent();
        userId = intent.getStringExtra("UID");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        readProfile(userId);
        readFollowers(userId);
        readFollowing(userId);
        home.setBackgroundColor(getResources().getColor(R.color.clickColor));
        readEvents(userId);
        if (firebaseUser.getUid().equals(userId))
            btnEdit.setVisibility(View.VISIBLE);
        else
            btnEdit.setVisibility(View.GONE);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),EditProfileActivity.class);
                startActivity(intent);
            }
        });


        job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                job.setBackgroundColor(getResources().getColor(R.color.clickColor));
                about.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                recyclerView.setVisibility(View.GONE);
                jobRecyclerView.setVisibility(View.VISIBLE);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home.setBackgroundColor(getResources().getColor(R.color.clickColor));
                job.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                about.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                jobRecyclerView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                readEvents(userId);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                job.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                about.setBackgroundColor(getResources().getColor(R.color.clickColor));
                jobRecyclerView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
            }
        });

    }
    public void readProfile(final String userId)
    {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user.getProfileImage().equals("Default"))
                    circleImageView.setImageResource(R.drawable.male);
                else
                    Glide.with(getApplicationContext()).load(user.getProfileImage()).into(circleImageView);
                name.setText(user.getFullname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readFollowing (final String userId)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Follow").child(userId).child("Following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                following.setText(dataSnapshot.getChildrenCount()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void readFollowers (final String userId)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Follow").child(userId).child("Followers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                followers.setText(dataSnapshot.getChildrenCount()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void readEvents (final String userId)
    {
        events = new ArrayList<>();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Events");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren())
                {
                    Events event = dataSnapshot1.getValue(Events.class);
                    if(event.getUserId().equals(userId))
                    {
                        events.add(event);
                    }

                }
                post.setText(events.size()+"");
                profileAdapter = new ProfileAdapter(getApplicationContext(),events,firebaseUser.getUid());
                recyclerView.setAdapter(profileAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}