package com.quest.weatherMonitors.helpers;


import com.quest.weatherMonitors.dto.ApiResponse;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * Class that execute requests
 *
 * */


public class RequestExecutorHelper {

    HttpClient httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());



    public ApiResponse getGetResponseData(String url) throws Exception {

        GetMethod get = new GetMethod(url);
        System.out.println("\nClient about to send request to " + get.getURI() + "\n");

        String responseBody = null;
        try {
            httpClient.executeMethod(get);
            byte[] bytes = get.getResponseBody();
            responseBody = new String(bytes);
        } catch (Exception e) {
            logger.error("Error - " + e);
        }
        finally {
            get.releaseConnection();
        }

        return new ApiResponse(get.getStatusLine().getStatusCode() , responseBody);
    }




}
