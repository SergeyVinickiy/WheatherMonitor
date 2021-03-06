package com.quest.wheatherMonitors.helpers;

import com.quest.wheatherMonitors.dto.WeatherForOneLocationDto;
import com.quest.wheatherMonitors.service.MapService;

import java.util.Date;

public class Printer {

    public static synchronized void print(WeatherForOneLocationDto weather, int threshold, MapService mapService) {

        MapService mapServiceLocal = mapService;
        int tempInCelsius = (int) ((weather.getMain().getTemp() - 32) * (0.5556));
        Date time = new java.util.Date(weather.getDt() * 1000);
        String city = weather.getName();

        System.out.printf("Time of data calculation for city %s is %s\n", city, time);
        System.out.printf("City is %s\n", city);
        System.out.printf("Temperature for city %s is %s\n", city, tempInCelsius);
        System.out.printf("Wind speed for city %s is %s\n", city, weather.getWind().getSpeed());


        isTemperatureAnomalous(weather.getName(), tempInCelsius, threshold, mapServiceLocal);


    }

    private static void isTemperatureAnomalous(String city, int temperature, int threshold, MapService mapService) {

        if (mapService.map.containsKey(city) && Math.abs(mapService.map.get(city) - temperature) >= threshold) {
            System.out.printf("Warning! Current temperature sample for city %s has anomalous change\n", city);
        }

    }

}
