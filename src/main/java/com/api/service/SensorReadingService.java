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

/**
 *
 * @author Senesa
 */
public class SensorReadingService {
    
    //sensorId -> list of readings
    private static Map<Integer, List<SensorReading>> readings = new HashMap<>();
    
    public static List<SensorReading> getReadings(int sensorId){
        return readings.getOrDefault(sensorId, new ArrayList<>());
    }
    
    public static SensorReading addReading(int sensorId, SensorReading reading){
        
        readings.putIfAbsent(sensorId, new ArrayList<>());
        readings.get(sensorId).add(reading);
        
        return reading;
    }
}
