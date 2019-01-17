package com.acp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

class Connection {
    private static final Logger logger = Logger.getLogger(Connection.class.getName());

    private final String POST = "POST";
    private final String AUTHORIZATION = "Authorization";
    private final String CONTENT_TYPE = "Content-Type";
    private final String APPLICATION_FORM = "application/x-www-form-urlencoded";
    private final String ACCEPT = "Accept";
    private final String APPLICATION_JSON = "application/json";
    private final String CONNECTION_ID = "Client-ID";
    private final String SPACE = " ";

    InputStream establishConnectionForPost(URL url, File imageFile, String clientId) throws IOException {
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            logger.info("Connection with Imgur could not be establish");
            throw e;
        }
        connection.setRequestMethod(POST);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        connection.setRequestProperty(AUTHORIZATION , CONNECTION_ID + SPACE + clientId);
        connection.setRequestProperty(CONTENT_TYPE, APPLICATION_FORM);
        connection.setRequestProperty(ACCEPT, APPLICATION_JSON);

        byte[] fileContent = FileUtils.readFileToByteArray(imageFile);
        String encodedImage= Base64.getEncoder().encodeToString(fileContent);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
        outputStreamWriter.write(encodedImage);
        outputStreamWriter.flush();

        return connection.getInputStream();
    }
}
