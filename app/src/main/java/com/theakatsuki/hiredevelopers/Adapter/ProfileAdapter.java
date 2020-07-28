package com.theakatsuki.hiredevelopers.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theakatsuki.hiredevelopers.Activity.ProfileActivity;
import com.theakatsuki.hiredevelopers.Model.Events;
import com.theakatsuki.hiredevelopers.Model.User;
import com.theakatsuki.hiredevelopers.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private Context myContext;
    private List<Events> events;
    private String uid;

    public ProfileAdapter(Context myContext, List<Events> events, String uid) {
        this.myContext = myContext;
        this.events = events;
        this.uid = uid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(myContext).inflate(R.layout.event_display,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

       final Events event = events.get(position);
       holder.content.setText(event.getContent());
       if(event.getEventImage().equals("Blank"))
       {
           holder.imageView.setVisibility(View.GONE);
       }
       else {
           holder.imageView.setVisibility(View.VISIBLE);
           Glide.with(myContext).load(event.getEventImage()).into(holder.imageView);

           holder.userProfileImage.setImageResource(R.mipmap.ic_launcher);

       }
       DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(event.getUserid());
       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               User user = dataSnapshot.getValue(User.class);
               holder.country.setText(user.getCountry());
               holder.fullName.setText(user.getFullname());

               if(user.getProfileImage().equals("Default"))
               {
                   holder.userProfileImage.setImageResource(R.drawable.male);

               }
               else
               {
                   Glide.with(myContext).load(user.getProfileImage()).into(holder.userProfileImage);
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });


    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView userProfileImage;

        ImageView imageView;
        TextView fullName, content, followers, country;
        ImageView like , comment;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userProfileImage = itemView.findViewById(R.id.userProfileImage);
            imageView = itemView.findViewById(R.id.ContentImage);
            fullName = itemView.findViewById(R.id.proUsername);
            country = itemView.findViewById(R.id.countryName);
            content = itemView.findViewById(R.id.eventContent);
            like = itemView.findViewById(R.id.btnComment);
            comment = itemView.findViewById(R.id.btnLike);
        }
    }



}
