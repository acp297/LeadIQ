package com.acp;

import com.acp.Model.Job;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class AllJobs {

    private static AllJobs allJobs;
    private static Set<String> imageUrls;
    private static Map<Integer, Job> jobList;
    private AllJobs(){}

    static AllJobs getInstance(){
        if (allJobs == null){
            allJobs = new AllJobs();
            jobList = new HashMap<>();
            imageUrls = new HashSet<>();
        }
        return allJobs;
    }

    Map<Integer, Job> getJobList() {
        return jobList;
    }

    void setJobList(Job job) {
        jobList.put(job.getJobId(), job);
    }

    void setImageUrls(String imageUrl){
        imageUrls.add(imageUrl);
    }

    Set<String> getImageUrls(){
        return imageUrls;
    }
}
