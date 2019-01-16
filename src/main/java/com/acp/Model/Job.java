package com.acp.Model;

public class Job {

    private int jobId;
    private String created;
    private String finished;
    private Status status;
    private UploadStatus uploaded;

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UploadStatus getUploaded() {
        return uploaded;
    }

    public void setUploaded(UploadStatus uploaded) {
        this.uploaded = uploaded;
    }

}
