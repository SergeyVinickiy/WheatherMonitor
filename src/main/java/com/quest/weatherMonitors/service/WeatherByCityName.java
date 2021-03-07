package com.quest.weatherMonitors.service;

import com.quest.weatherMonitors.dto.ApiResponse;
import com.quest.weatherMonitors.dto.WeatherForOneLocationDto;
import com.quest.weatherMonitors.entity.City;
import com.quest.weatherMonitors.helpers.RequestExecutorHelper;
import com.quest.weatherMonitors.helpers.UrlBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.util.Arrays;
import java.util.Date;

public class WeatherByCityName extends BaseService implements Runnable{

    private final RequestExecutorHelper requestExecutorHelper;
    private final UrlBuilder urlBuilder;
    private final City city;
    private final MapService mapService;
    private String cityName;
    private Date date;
    private int tempInCelsius;
    private double windSpeed;



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

                if(response.getResponseCode() == 200) {

                    cityName = city.getCity_name();
                    date = new java.util.Date(weatherForOneLocation.getDt() * 1000);
                    tempInCelsius = (int) ((weatherForOneLocation.getMain().getTemp() - 273.15));
                    windSpeed = weatherForOneLocation.getWind().getSpeed();

                    System.out.println(this.toString());

                    int tempInCelsius = (int) ((weatherForOneLocation.getMain().getTemp() - 32) * (0.5556));

                    temperatureAnomalousWarning(city.getCity_name(), tempInCelsius, city.getThreshold(), mapService);

                    mapService.map.put(weatherForOneLocation.getName(), (int) ((weatherForOneLocation.getMain().getTemp() - 32) * (0.5556)));
                }
                else{
                    System.out.println(weatherForOneLocation.getMessage() + " - for city " + city.getCity_name());
                    break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                --frequency;
            }

        }

    }

    public synchronized static void temperatureAnomalousWarning(String city, int temperature, int threshold, MapService mapService) {

        if (mapService.map.containsKey(city) && Math.abs(mapService.map.get(city) - temperature) >= threshold) {
            System.out.printf("Warning! Current temperature sample for city %s has anomalous change\n", city);
        }

    }


    @Override
    public String toString() {

        StringBuffer dataOnCurrentRequest = new StringBuffer("Time of data calculation for city ").append(cityName).append(" is ").append(date).append("\n");
        dataOnCurrentRequest.append("City is ").append(cityName).append("\n");
        dataOnCurrentRequest.append("Temperature for city ").append(cityName).append(" is ").append(tempInCelsius).append("\n");
        dataOnCurrentRequest.append("Wind speed for city ").append(cityName).append(" is ").append(windSpeed);
        return dataOnCurrentRequest.toString();
    }
}
