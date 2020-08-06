package com.theakatsuki.hiredevelopers;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AllFunctions {
    public void FollowUser(final String userId, final String FollowingUserId)
    {

        final DatabaseReference followRef = FirebaseDatabase.getInstance().getReference("Follow")
                .child(userId)
                .child("Following")
                .child(FollowingUserId);
        followRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists())
                {
                    followRef.child("id").setValue(FollowingUserId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final DatabaseReference followRef2 = FirebaseDatabase.getInstance().getReference("Follow")
                .child(FollowingUserId)
                .child("Followers")
                .child(userId);
        followRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists())
                {
                    followRef2.child("id").setValue(userId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void RemoveFollow(String userID , String FollowingUserId)
    {

        FirebaseDatabase.getInstance().getReference("Follow").child(userID).child("Following").child(FollowingUserId).removeValue();
        FirebaseDatabase.getInstance().getReference("Follow").child(FollowingUserId).child("Followers").child(userID).removeValue();

    }

}
