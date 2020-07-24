package com.theakatsuki.hiredevelopers.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.service.autofill.DateTransformation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theakatsuki.hiredevelopers.Adapter.HomeAdapter;
import com.theakatsuki.hiredevelopers.Adapter.ProfileHomeAdapter;
import com.theakatsuki.hiredevelopers.Model.Events;
import com.theakatsuki.hiredevelopers.R;

import java.util.ArrayList;
import java.util.List;


public class ProfileHomeFragment extends Fragment {

    RecyclerView recyclerView;
    FirebaseUser firebaseUser;
    List<Events> events;
    private String userid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile_home, container, false);
        recyclerView = view.findViewById(R.id.profilehomerecycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        events= new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Events");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Events event = dataSnapshot1.getValue(Events.class);
                    if(event.getUserid().equals(firebaseUser.getUid()))
                    {
                        events.add(event);
                    }
                }
                ProfileHomeAdapter profileHomeFragment = new ProfileHomeAdapter(getContext(),events);
                recyclerView.setAdapter(profileHomeFragment);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
    public void getUserID(String userid)
    {
        this.userid = userid;
    }

}