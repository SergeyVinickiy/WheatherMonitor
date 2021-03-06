package com.quest.wheatherMonitors.service;


import java.util.Hashtable;

public class MapService {

    private static MapService mapService;
    public Hashtable<String, Integer> map;

    private MapService() {
        this.map = new Hashtable<>();
    }

    synchronized public static MapService getMapInstance(){
        if(mapService == null){
            mapService = new MapService();
        }
        return mapService;
    }


}
