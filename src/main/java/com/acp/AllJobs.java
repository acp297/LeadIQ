package com.acp;

import com.acp.Model.Job;
import java.util.HashMap;
import java.util.Map;

class AllJobs {

    private static AllJobs allJobs;
    private static Map<Integer, Job> jobList;
    private AllJobs(){}

    static AllJobs getInstance(){
        if (allJobs == null){
            allJobs = new AllJobs();
            jobList = new HashMap<>();
        }
        return allJobs;
    }

    Map<Integer, Job> getJobList() {
        return jobList;
    }

    void setJobList(Job job) {
        jobList.put(job.getJobId(), job);
    }
}
