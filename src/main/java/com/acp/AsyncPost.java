package com.acp;

import com.acp.Model.ImgurResponse;
import com.acp.Model.Job;
import com.acp.Model.PostRequestInputBody;
import com.acp.Model.Status;
import com.acp.Model.UploadStatus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import static com.acp.Util.getCurrentTime;

public class AsyncPost extends Thread {
    private static final Logger logger = Logger.getLogger(AsyncPost.class.getName());

    private Handler handler;
    private PostRequestInputBody postRequestInputBody;
    private Job job;
    private AllJobs allJobs;
    private Util util;

    AsyncPost(PostRequestInputBody postRequestInputBody, Job job) throws IOException {
        this.postRequestInputBody = postRequestInputBody;
        this.job = job;
        job.setStatus(Status.pending);
        handler = new Handler();
        allJobs = AllJobs.getInstance();
        util = new Util();
    }

    @Override
    public void run() {

        String[] imageUrls = postRequestInputBody.getUrls();
        UploadStatus uploadStatus = new UploadStatus();
        List<String> uploadedImages = new ArrayList<>();
        List<String> failedImages = new ArrayList<>();
        List<String> pendingImages = new ArrayList<>(Arrays.asList(imageUrls));
        uploadStatus.setPending(pendingImages);

        for (String imageUrl : imageUrls){
            Thread thread = new Thread(){
                @Override
                public synchronized void run() {
                    job.setStatus(Status.inprogress);
                    try {
                        File imageFile = util.downloadImageByUrl(imageUrl);
                        logger.info("Uploading Image: " + imageUrl);
                        ImgurResponse imgurResponse = handler.uploadImage(imageFile);

                        if (imgurResponse != null && imgurResponse.getData() != null && imgurResponse.getData().getId() != null){
                            uploadedImages.add(imgurResponse.getData().getLink());
                        } else {
                            failedImages.add(imageUrl);
                            logger.info("Uploading Image failed: " + imageUrl);
                        }

                    } catch (Exception e) {
                        failedImages.add(imageUrl);
                        logger.info("Uploading Image failed: " + imageUrl + " " + e.getMessage());
                    }
                    pendingImages.remove(imageUrl);
                    uploadStatus.setPending(pendingImages);
                    uploadStatus.setComplete(uploadedImages);
                    uploadStatus.setFailed(failedImages);

                    job.setUploaded(uploadStatus);
                    job.setFinished(getCurrentTime());
                    allJobs.setJobList(job);
                }
            };
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        job.setStatus(Status.complete);
        allJobs.setJobList(job);
    }
}
