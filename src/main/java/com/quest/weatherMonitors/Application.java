package com.quest.weatherMonitors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.quest.weatherMonitors.entity.City;
import com.quest.weatherMonitors.helpers.RequestExecutorHelper;
import com.quest.weatherMonitors.service.WeatherByCityName;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;


public class Application {

    static RequestExecutorHelper requestExecutorHelper = new RequestExecutorHelper();
    static  List<City> cities = null;


    public static void main(String[] args) {
        cities = getCitiesList();
        WeatherByCityName[] cityWeatherThreads = new WeatherByCityName[cities.size()];
        for (int i = 0; i < cityWeatherThreads.length; i++) {

            cityWeatherThreads[i] = new WeatherByCityName(requestExecutorHelper, cities.get(i));
            Thread thread = new Thread(cityWeatherThreads[i]);
            thread.start();
        }

    }

    private static List<City> getCitiesList(){
        Gson jsonParser = new Gson();
        List<City> cities = null;
        try {
            JsonReader reader = new JsonReader(new FileReader("src\\main\\resources\\configuration.json"));
            cities = jsonParser.fromJson(reader, new TypeToken<List<City>>(){}.getType());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return cities;
    }

}
