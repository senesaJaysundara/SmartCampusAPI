/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.resource;

import com.api.model.Room;
import com.api.service.RoomService;
import com.api.exception.RoomNotEmptyException;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import javax.ws.rs.PathParam;

/**
 *
 * @author Senesa
 */

@Path("/rooms")
public class RoomResource {
    
    //Get all rooms
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Room> getRooms(){
        return RoomService.getAllRooms();
    }
    
    //GET room by ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Room getRoom(@PathParam("id") int id){
        return RoomService.getRoom(id);
    }
    
    //GET sensors belongs to a specific room
    @GET
    @Path("/{id}/sensors")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<com.api.model.Sensor> getSensorByRoom(@PathParam("id")int id){
        
        return com.api.service.SensorService.getAllSensors()
                .stream()
                .filter(sensor -> sensor.getRoomId() == id)
                .toList();
    }
    
    //POST create room
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Room addRoom(Room room){
        return RoomService.addRoom(room);
    }
    
    //PUT Update rooms
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Room updateRoom(@PathParam("id") int id, Room room){
        return RoomService.updateRoom(id, room);
    }
    
    //DELETE Room
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Room deleteRoom(@PathParam("id") int id){
        
        boolean hasSensor = com.api.service.SensorService.getAllSensors()
                .stream()
                .anyMatch(sensor -> sensor.getRoomId() == id);
        if (hasSensor){
            throw new RoomNotEmptyException("Room cannot be deleted, sensors are still assigned.");
        }
        return RoomService.deleteRoom(id);
    }
}
