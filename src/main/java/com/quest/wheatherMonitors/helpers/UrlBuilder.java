package com.quest.wheatherMonitors.helpers;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.http.message.BasicNameValuePair;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.ResourceBundle;

public class UrlBuilder {

    private static final Logger logger = LoggerFactory.getLogger(UrlBuilder.class);

    private static String getRequestHost() {
        return ResourceBundle.getBundle("application").getString("openWeather.host");
    }

    public synchronized String oneLocationUrl(List<BasicNameValuePair> pageParams){
        return addParamsToUrl(getRequestHost(), pageParams);
    }


    private String addParamsToUrl(String baseUrl, List<BasicNameValuePair> urlParams) {

        StringBuilder paramsBuilder = new StringBuilder();
        if (!urlParams.isEmpty()) {
            paramsBuilder.append("?");
            for (BasicNameValuePair param : urlParams) {
                try {
                    paramsBuilder.append(param.getName()).append("=").append(URLEncoder.encode(param.getValue(), Charset.defaultCharset().name())).append("&");
                } catch (Exception e) {
                    logger.error("Error adding param to url query: " + param.getName() + "=" + param.getValue());
                }
            }
        }

        String paramsString = paramsBuilder.toString();
        if (paramsString.endsWith("&")) {
            paramsString = paramsString.substring(0, paramsString.length() - 1);
        }

        return baseUrl + paramsString;
    }

}
