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
    
    private static Map<Integer, Sensor> sensors = new HashMap<>();
    
    static{
        sensors.put(1, new Sensor(1, "Temperature", 2.5, 1));
        sensors.put(2, new Sensor(2, "C02", 400, 1));
    }
    
    public static Collection<Sensor> getAllSensors(){
        return sensors.values();
    }
    
    public static Sensor getSensor(int id){
        return sensors.get(id);
    }
    
    public static Sensor addSensor(Sensor sensor){
        sensors.put(sensor.getId(), sensor);
        return sensor;
    }
    
    public static Sensor updateSensor(int id, Sensor sensor){
        sensor.setId(id);
        sensors.put(id, sensor);
        return sensor;
    }
    
    public static Sensor deleteSensor(int id){
        return sensors.remove(id);
    }
}
