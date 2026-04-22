/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.resource;

import com.api.model.Sensor;
import com.api.service.SensorService;
import com.api.service.RoomService;
import com.api.exception.LinkedResourceNotFoundException;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;
import java.util.Collection;

/**
 *
 * @author Senesa
 */

@Path("/sensors")
public class SensorResource {
    
    //GET with filtering
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Sensor>getSensors(@QueryParam("type") String type){
        
        Collection <Sensor> sensors = SensorService.getAllSensors();
        
        if(type == null){
            return sensors;
        }
        
        return sensors.stream()
                .filter(s -> s.getType().equalsIgnoreCase(type))
                .toList();
    }
    
    //GET by ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Sensor getSensor(@PathParam("id") int id){
        return SensorService.getSensor(id);
    }
    @Path("/{id}/readings")
    public SensorReadingResource getReadingResource(@PathParam("id")int id){
        return new SensorReadingResource(id);
    }
    
    //POST with validation
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Sensor addSensor(Sensor sensor){
        //room validation
        if(RoomService.getRoom(sensor.getRoomId()) == null){
            throw new LinkedResourceNotFoundException("Room does not exist.");
        }
        return SensorService.addSensor(sensor);
    }
    
    //PUT
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Sensor updateSensor(@PathParam("id") int id, Sensor sensor){
        sensor.setId(id);
        return SensorService.updateSensor(id, sensor);
    }
    
    //DELETE
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Sensor deleteSensor(@PathParam("id") int id){
        return SensorService.deleteSensor(id);
    }
}
