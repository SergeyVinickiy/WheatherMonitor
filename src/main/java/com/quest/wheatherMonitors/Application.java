package com.quest.wheatherMonitors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.quest.wheatherMonitors.entity.City;
import com.quest.wheatherMonitors.helpers.RequestExecutorHelper;
import com.quest.wheatherMonitors.service.WeatherByCityName;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;


public class Application {

    static RequestExecutorHelper requestExecutorHelper = new RequestExecutorHelper();
    static  List<City> cities = null;


    public static void main(String[] args) {
        cities = getCitiesList();
        WeatherByCityName[] threads = new WeatherByCityName[cities.size()];

        for (int i = 0; i < threads.length; i++) {

            threads[i] = new WeatherByCityName(requestExecutorHelper, cities.get(i));
            Thread thread = new Thread(threads[i]);
            thread.start();
        }

    }

    private static List<City> getCitiesList(){
        Gson jsonParser = new Gson();
        List<City> cities = null;
        try {
            JsonReader reader = new JsonReader(new FileReader("C:\\Users\\sergeyv1\\OneDrive - Verifone\\Desktop\\weatherMonitors\\src\\main\\resources\\configuration.json"));
            cities = jsonParser.fromJson(reader, new TypeToken<List<City>>(){}.getType());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return cities;
    }

}
