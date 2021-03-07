package com.quest.weatherMonitors.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Wind {

    private double speed;
    private int deg;
    private double gust;

}
