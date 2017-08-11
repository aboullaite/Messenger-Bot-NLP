package me.aboullaite.nlpmessengerbot.utils;



import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

import java.io.IOException;

public class Weather {


    public static String weather(String target, String omwtoken){
        // declaring object of "OpenWeatherMap" class
        OpenWeatherMap owm = new OpenWeatherMap(omwtoken);
        owm.setUnits(OpenWeatherMap.Units.METRIC);

        // getting current weather data for the city/country
        CurrentWeather cwd = null;
        try {
            cwd = owm.currentWeatherByCityName(target);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Today Its " + cwd.getWeatherInstance(0).getWeatherDescription() + " at " + target + " with a temperature of "+ cwd.getMainInstance().getTemperature()+ " C";
    }

}
