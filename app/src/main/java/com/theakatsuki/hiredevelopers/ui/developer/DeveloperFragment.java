package com.theakatsuki.hiredevelopers.ui.developer;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.theakatsuki.hiredevelopers.Activity.JobsActivity;
import com.theakatsuki.hiredevelopers.Adapter.JobAdapter;
import com.theakatsuki.hiredevelopers.Model.Job;
import com.theakatsuki.hiredevelopers.R;

import java.util.ArrayList;
import java.util.List;


public class DeveloperFragment extends Fragment {

    EditText searchBar;
    RecyclerView jobRecyclerView;
    JobAdapter jobAdapter;
    List<Job> jobs;
    LinearLayout webLinear,designLinear,writingLinear,salesLinear,mobileLinear,dataLinear;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_developer, container, false);

        jobRecyclerView =view.findViewById(R.id.recommendJobs);
        jobRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        jobRecyclerView.setLayoutManager(layoutManager);
        jobs = new ArrayList<>();
        jobAdapter = new JobAdapter(getContext(),jobs);
        jobRecyclerView.setAdapter(jobAdapter);

        webLinear = view.findViewById(R.id.webLinear);
        designLinear = view.findViewById(R.id.designLinear);
        writingLinear = view.findViewById(R.id.writingLinear);
        salesLinear = view.findViewById(R.id.salesLinear);
        mobileLinear = view.findViewById(R.id.mobileLinear);
        dataLinear = view.findViewById(R.id.dataLinear);
        webLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), JobsActivity.class);
                startActivity(intent);
            }
        });
        designLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), JobsActivity.class);
                startActivity(intent);
            }
        });
        writingLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), JobsActivity.class);
                startActivity(intent);
            }
        });
        salesLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), JobsActivity.class);
                startActivity(intent);
            }
        });
        mobileLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), JobsActivity.class);
                startActivity(intent);
            }
        });
        dataLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), JobsActivity.class);
                startActivity(intent);
            }
        });



        return view;
    }
}