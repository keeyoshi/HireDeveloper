package com.theakatsuki.hiredevelopers.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theakatsuki.hiredevelopers.Activity.SingleJobActivity;
import com.theakatsuki.hiredevelopers.Model.Job;
import com.theakatsuki.hiredevelopers.R;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {

    Context context;
    List<Job> jobList;

    public JobAdapter(Context context, List<Job> jobList) {
        this.context = context;
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_display,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Job job = jobList.get(position);
        holder.title.setText(job.getTitle());
        holder.deadline.setText(job.getDate());
        holder.cost.setText(job.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SingleJobActivity.class);
                intent.putExtra("JobId",job.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,deadline,cost;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.job_title);
            deadline = itemView.findViewById(R.id.job_deadline);
            cost = itemView.findViewById(R.id.job_price);
        }
    }
}
