package com.quest.weatherMonitors.dto;

public class ApiResponse {

    private final int responseCode;
    private final String responseBody;


    public ApiResponse(int statusCode, String responseBody) {
        this.responseCode = statusCode;
        this.responseBody = responseBody;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

}
