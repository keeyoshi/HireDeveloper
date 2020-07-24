package com.theakatsuki.hiredevelopers.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theakatsuki.hiredevelopers.Model.User;
import com.theakatsuki.hiredevelopers.R;
import com.theakatsuki.hiredevelopers.ui.AboutUsFragment;
import com.theakatsuki.hiredevelopers.ui.JobFragment;
import com.theakatsuki.hiredevelopers.ui.ProfileHomeFragment;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ImageView profileImage;
    TextView name,followers,following ;
    Intent intent;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.view_pager);
        followers= findViewById(R.id.profileFollowers);
        following= findViewById(R.id.profileFollowing);
        intent = getIntent();
        String UserID  = intent.getStringExtra("UID");

        profileImage = findViewById(R.id.profileImage);
//        ProfileHomeFragment profileHomeFragment = new ProfileHomeFragment();
//        profileHomeFragment.getUserID(UserID);
//        getSupportFragmentManager().beginTransaction().add(R.id.frame123,profileHomeFragment).commit();
        ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());
        viewpagerAdapter.addFragments(new ProfileHomeFragment(),"Home");
        viewpagerAdapter.addFragments(new AboutUsFragment(),"About");
        viewpagerAdapter.addFragments(new JobFragment(),"Job");
        viewPager.setAdapter(viewpagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(UserID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                name.setText(user.getFullname());
                 if (user.getProfileImage().equals("Default"))
                {
                    profileImage.setImageResource(R.drawable.male);
                }
                else
                {
                    Glide.with(getApplicationContext()).load(user.getProfileImage()).into(profileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Follow").child(UserID).child("Following");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    following.setText(dataSnapshot.getChildrenCount()+"");
                }
                else
                {
                    following.setText("0");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Follow").child(UserID).child("Followers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    followers.setText(dataSnapshot.getChildrenCount()+"");
                }
                else
                {
                    followers.setText("0");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    class ViewpagerAdapter extends FragmentPagerAdapter{
        private ArrayList<Fragment> fragments;
        private  ArrayList<String> titles;
        ViewpagerAdapter(FragmentManager fm )
        {
            super( fm);
            this.fragments=new ArrayList<>();
            this.titles= new ArrayList<>();

        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        public void addFragments(Fragment fragment, String title)
        {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}