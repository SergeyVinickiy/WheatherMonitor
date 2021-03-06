package com.quest.wheatherMonitors.helpers;


import com.quest.wheatherMonitors.dto.ApiResponse;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;


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
        System.out.println("Client about to send request to " + get.getURI());

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
