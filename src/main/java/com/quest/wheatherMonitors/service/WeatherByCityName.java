package com.quest.wheatherMonitors.service;

import com.quest.wheatherMonitors.dto.ApiResponse;
import com.quest.wheatherMonitors.dto.WeatherForOneLocationDto;
import com.quest.wheatherMonitors.entity.City;
import com.quest.wheatherMonitors.helpers.Printer;
import com.quest.wheatherMonitors.helpers.RequestExecutorHelper;
import com.quest.wheatherMonitors.helpers.UrlBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.util.Arrays;

public class WeatherByCityName extends BaseService implements Runnable{

    private final RequestExecutorHelper requestExecutorHelper;
    private final UrlBuilder urlBuilder;
    private final City city;
    private MapService mapService;


    public WeatherByCityName(RequestExecutorHelper requestExecutorHelper, City city){
        this.requestExecutorHelper = requestExecutorHelper;
        urlBuilder = new UrlBuilder();
        mapService = MapService.getMapInstance();
        this.city = city;
    }

    public void run() {
        int frequency = city.getFrequency();


        while (frequency > 0) {
            try {

                String url = urlBuilder.oneLocationUrl(Arrays.asList(new BasicNameValuePair("q", city.getCity_name()), new BasicNameValuePair("appid", properties.getString("appid"))));

                ApiResponse response = requestExecutorHelper.getGetResponseData(url);
                WeatherForOneLocationDto weatherForOneLocation = jsonParser.fromJson(response.getResponseBody(), WeatherForOneLocationDto.class);

                Printer.print(weatherForOneLocation, city.getThreshold(), mapService);

                mapService.map.put(weatherForOneLocation.getName(), (int) ((weatherForOneLocation.getMain().getTemp() - 32) * (0.5556)));



            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                --frequency;
            }

        }
    }
}
