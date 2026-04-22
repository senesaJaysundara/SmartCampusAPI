/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.resource;

import com.api.model.Sensor;
import com.api.model.SensorReading;
import com.api.service.SensorReadingService;
import com.api.service.SensorService;
import com.api.exception.SensorUnavailableException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 *
 * @author Senesa
 */
public class SensorReadingResource {
    
    private int sensorId;
    
    public SensorReadingResource(int sensorId){
        this.sensorId = sensorId;
    }
    
    //GET readings
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorReading> getReading(){
        return SensorReadingService.getReadings(sensorId);
    }
    
    //POST new readings
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SensorReading addReading(SensorReading reading){
        //Add reading
        //SensorReadingService.addReading(sensorId, reading);
        
        Sensor sensor = SensorService.getSensor(sensorId);
        
        if(sensor.getStatus().equals("MAINTENANCE")){
            throw new SensorUnavailableException("Sensor id under maintenance.");
        }
        
        //Save reading
        SensorReadingService.addReading(sensorId, reading);
        
        //update parent sensor value
        if (sensor != null){
            sensor.setValue(reading.getValue());
        }
        return reading;
    }
}
