package com.quest.weatherMonitors.service;

import com.google.gson.Gson;

import java.util.ResourceBundle;

/**
 * Class used as the parent class for all service classes
 *
 * Service classes contain the main logic of the application
 *
 * */

public class BaseService {

    protected Gson jsonParser = new Gson();
    protected ResourceBundle properties = ResourceBundle.getBundle("application");

}
