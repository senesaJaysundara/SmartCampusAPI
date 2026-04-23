/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.service;

import com.api.model.Sensor;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

/**
 *
 * @author Senesa
 */
public class SensorService {
    
    private static Map<String, Sensor> sensors = new HashMap<>();
    
    static{
        sensors.put("TEMP-001", new Sensor("TEMP-001", "Temperature", 2.5, "LIB-301", "ACTIVE"));
        sensors.put("CO2-001", new Sensor("CO2-001", "CO2", 400.0, "LIB-301", "ACTIVE"));
    }
    
    public static Collection<Sensor> getAllSensors(){
        return sensors.values();
    }
    
    public static Sensor getSensor(String id){
        return sensors.get(id);
    }
    
    public static Sensor addSensor(Sensor sensor){
        sensors.put(sensor.getId(), sensor);
        return sensor;
    }
    
    public static Sensor updateSensor(String id, Sensor sensor){
        sensor.setId(id);
        sensors.put(id, sensor);
        return sensor;
    }
    
    public static Sensor deleteSensor(String id){
        return sensors.remove(id);
    }
}
