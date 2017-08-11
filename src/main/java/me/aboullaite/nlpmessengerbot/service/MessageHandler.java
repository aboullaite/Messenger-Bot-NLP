package me.aboullaite.nlpmessengerbot.service;

import me.aboullaite.nlpmessengerbot.utils.EntityMap;
import me.aboullaite.nlpmessengerbot.utils.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class MessageHandler {
    private final String omwtoken;

    @Autowired
    public MessageHandler(final String omwtoken){
        this.omwtoken = omwtoken;
    }

    private final String GREETINGS_MSG = "greetings";
    private final String LOCATION_MSG = "location";
    private final String GENERIC_MSG = "Greeting from Fero Weld Hero";
    private final double TRESHOLD = 0.8;
    public  String handleMessage(Map<String,List<Map<String,Object>>> entities){
        if(EntityMap.entityExist(GREETINGS_MSG, entities))
         return  EntityMap.confidence(GREETINGS_MSG, entities) > TRESHOLD ? "Hello Sir!" : GENERIC_MSG;
        if(EntityMap.entityExist(LOCATION_MSG, entities))
            return  EntityMap.confidence(LOCATION_MSG, entities) > TRESHOLD ?  Weather.weather(EntityMap.value(LOCATION_MSG, entities), omwtoken) : GENERIC_MSG;

        return  GENERIC_MSG;

    }

}
