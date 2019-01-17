package com.acp;

import com.acp.Model.PostRequestInputBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import org.json.JSONObject;

class Util {
    private static final Logger logger = Logger.getLogger(Util.class.getName());
    private final String USER_AGENT = "User-Agent";

    static int getJobId(PostRequestInputBody postRequestInputBody){
        return postRequestInputBody.hashCode();
    }

    static String getCurrentTime(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD'T'HH:mm:ss.sssZ");
        return simpleDateFormat.format(timestamp);
    }

    String buildResponseAsJson(String message){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message" , message);
        return jsonObject.toString();
    }

    File downloadImageByUrl(String imageUrl) throws IOException {

        logger.info("Downloading image: " + imageUrl);
        String tempLocation = "target/temp";
        new File(tempLocation).mkdirs();
        URL url = new URL(imageUrl);

        String[] split = imageUrl.split("/");
        String imageName = split[split.length-1];
        String destinationFile = tempLocation + "/" + imageName;

        InputStream is;
        OutputStream os;
        URLConnection connection;
        try {
            connection = url.openConnection();
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
            connection.setRequestProperty(USER_AGENT, userAgent);
            is = connection.getInputStream();
        } catch (IOException e) {
            logger.info("Connection issue while downloading image: " + e.getMessage());
            throw e;
        }

        try {
            os = new FileOutputStream(destinationFile);
        } catch (FileNotFoundException e) {
            logger.info("temp folder not found: " + e.getMessage());
            throw e;
        }

        byte[] b = new byte[4096];
        int length;
        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }
        is.close();
        os.close();
        return new File(destinationFile);
    }
}
