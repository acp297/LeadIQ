package com.acp;

import com.acp.Model.Job;
import com.acp.Model.GetRequestResponse;
import com.acp.Model.PostRequestInputBody;
import com.acp.Model.PostRequestResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static com.acp.Util.getCurrentTime;
import static com.acp.Util.getJobId;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/v1/images")
public class Resource {

    private Handler handler;
    private Util util;
    public Resource() throws IOException {
        handler = new Handler();
        util = new Util();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/upload")
    public Response uploadImage(PostRequestInputBody postRequestInputBody) throws IOException {

        String errorMessage = "Mandatory parameter is missing: 'URL' ";
        if (postRequestInputBody == null){
            return Response.status(BAD_REQUEST.getStatusCode()).entity(util.buildResponseAsJson(errorMessage)).build();
        }
        if (postRequestInputBody.getUrls() == null){
            return Response.status(BAD_REQUEST.getStatusCode()).entity(util.buildResponseAsJson(errorMessage)).build();
        }
        if (postRequestInputBody.getUrls().length == 0){
            return Response.status(BAD_REQUEST.getStatusCode()).entity(util.buildResponseAsJson(errorMessage)).build();
        }
        Job job = new Job();
        int jobId = getJobId(postRequestInputBody);
        job.setJobId(jobId);
        job.setCreated(getCurrentTime());

        AsyncPost asyncPost = new AsyncPost(postRequestInputBody, job);
        asyncPost.start();

        PostRequestResponse postRequestResponse = new PostRequestResponse();
        postRequestResponse.setJobId(jobId);
        return Response.ok(postRequestResponse).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImages(){
        List<String> allImages = handler.getAllImages();
        GetRequestResponse getRequestResponse = new GetRequestResponse();
        getRequestResponse.setUploaded(allImages);
        return Response.ok(getRequestResponse).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{jobId}")
    public Response getImageById(@PathParam("jobId") int jobId) {
        Job job = handler.getImageById(jobId);

        if (job == null){
            return Response.status(NOT_FOUND.getStatusCode())
                    .entity(util.buildResponseAsJson("Job with jobId: " + jobId + " not found !")).build();
        }
        return Response.ok(job).build();
    }
}
