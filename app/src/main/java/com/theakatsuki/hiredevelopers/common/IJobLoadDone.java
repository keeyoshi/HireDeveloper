package com.theakatsuki.hiredevelopers.common;

import com.theakatsuki.hiredevelopers.Model.Job;

import java.util.List;

public interface IJobLoadDone {
    void onJobLoadDoneListener(List<Job> jobList);
}
