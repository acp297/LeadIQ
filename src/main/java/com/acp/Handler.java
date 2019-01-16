package com.acp;

import com.acp.Model.ImgurResponse;
import com.acp.Model.Job;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;


class Handler {
    private Properties properties;
    private final String IMGUR_URL = "imgurUrl";
    private final String CONFIG_PROPERTIES = "config.properties";
    private final String CLIENT_ID = "clientId";
    private AllJobs allJobs;
    private static final Logger logger = Logger.getLogger(Handler.class.getName());

    Handler() throws IOException {
        this.properties = new Properties();
        InputStream input = this.getClass().getClassLoader().getResourceAsStream(CONFIG_PROPERTIES);
        properties.load(input);
        allJobs = AllJobs.getInstance();
    }

    ImgurResponse uploadImage(File imageFile) throws IOException {
        URL url = new URL(properties.getProperty(IMGUR_URL));
        Connection connection = new Connection();
        InputStream inputStream = connection.establishConnectionForPost(url, imageFile, properties.getProperty(CLIENT_ID));

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(inputStream, ImgurResponse.class);
    }

    Job getImageById(int jobId) {
        Map<Integer, Job> jobList = allJobs.getJobList();
        for (Job job : jobList.values()){
            if (job.getJobId() == jobId){
                return job;
            }
        }
        return null;
    }

    List<String> getAllImages() {
        List<String> images = new ArrayList<>();
        for (Job job : allJobs.getJobList().values()){
            images.addAll(job.getUploaded().getComplete());
        }
        return images;
    }
}
