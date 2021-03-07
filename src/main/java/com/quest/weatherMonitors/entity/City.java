package com.quest.weatherMonitors.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class City {

    private int city_id;
    private String city_name;
    private int frequency;
    private int threshold;

}
