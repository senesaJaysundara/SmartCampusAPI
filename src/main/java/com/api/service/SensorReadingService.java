/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.service;

import com.api.model.SensorReading;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Senesa
 */
public class SensorReadingService {
    
    //sensorId -> list of readings
    private static Map<String, List<SensorReading>> readings = new HashMap<>();
    
    public static List<SensorReading> getReadings(String sensorId){
        return readings.getOrDefault(sensorId, new ArrayList<>());
    }
    
    public static SensorReading addReading(String sensorId, SensorReading reading){
        
        //Auto-generate ID and timestamp 
        if(reading.getId() == null || reading.getId().isEmpty()){
            reading.setId(UUID.randomUUID().toString());
        }
        if(reading.getTimestamp() == 0){
            reading.setTimeStamp(System.currentTimeMillis());
        }
        readings.putIfAbsent(sensorId, new ArrayList<>());
        readings.get(sensorId).add(reading);
        return reading;
    }
}
