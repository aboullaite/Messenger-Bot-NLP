package me.aboullaite.nlpmessengerbot.utils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EntityMap {

    public static double confidence(String entityId, Map<String,List<Map<String,Object>>> entities){

        Optional<Map<String,Object>> entity =  entities.get(entityId).stream().findFirst();
        if(entity.isPresent()){
            Object confidenceObject = entity.get().get("confidence");
            if(confidenceObject != null)
                return (double) confidenceObject;
        }
        return 0;
    }

    public static String value(String entityId, Map<String,List<Map<String,Object>>> entities){

        Optional<Map<String,Object>> entity =  entities.get(entityId).stream().findFirst();
        if(entity.isPresent()){
            Object valueObject = entity.get().get("value");
            if(valueObject != null)
                return (String) valueObject;
        }
        return "";
    }

    public static boolean entityExist(String entityId, Map<String,List<Map<String,Object>>> entities){
        return entities.get(entityId) != null ? true : false;
    }

}
