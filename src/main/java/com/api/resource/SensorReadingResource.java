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
import javax.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author Senesa
 */

public class SensorReadingResource {
    
    private String sensorId;
    
    public SensorReadingResource(String sensorId){
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
    public Response addReading(SensorReading reading){
        //Add reading
        //SensorReadingService.addReading(sensorId, reading);
        Sensor sensor = SensorService.getSensor(sensorId);
        
        if (sensor == null){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Sensor not found\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        
        //Block readings for sensors under maintenance
        if("MAINTENANCE".equals(sensor.getStatus())){
            throw new SensorUnavailableException("Sensor " + sensorId + "is under maintenance and cannot accept readings");
        }
        
        //Save reading
        SensorReading saved = SensorReadingService.addReading(sensorId, reading);
        
        //update parent sensor's current value
        sensor.setValue(saved.getValue());
        
        return Response.status(Response.Status.CREATED)
                .entity(saved)
                .build();
        
    }
}
