/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.resource;

import com.api.model.Room;
import com.api.model.Sensor;
import com.api.service.RoomService;
import com.api.service.SensorService;
import com.api.exception.RoomNotEmptyException;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import javax.ws.rs.PathParam;

/**
 *
 * @author Senesa
 */

@Path("/rooms")
//@Produces("application/json")
//@Consumes("application/json")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {
    
    //Get all rooms
    @GET
    public Collection<Room> getRooms(){
        return RoomService.getAllRooms();
    }
    
    //GET room by ID
    @GET
    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoom(@PathParam("id") String id){
        Room room = RoomService.getRoom(id);
        if(room == null){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Room not found\"}")
                    .build();
        }
        return Response.ok(room).build();
    }
    
    //GET sensors belongs to a specific room
    @GET
    @Path("/{id}/sensors")
//    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Sensor> getSensorByRoom(@PathParam("id")String id){
        
        return SensorService.getAllSensors()
                .stream()
                .filter(sensor -> id.equals(sensor.getRoomId()))
                .toList();
    }
    
    //POST create room
    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
    public Response addRoom(Room room){
        Room created = RoomService.addRoom(room);
        return Response.status(Response.Status.CREATED)
                .entity(created)
                .header("Location","/api/v1/rooms"+created.getId())
                .build();
    }
    
    //PUT Update rooms
    @PUT
    @Path("/{id}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
    public Room updateRoom(@PathParam("id") String id, Room room){
        return RoomService.updateRoom(id, room);
    }
    
    //DELETE Room
    @DELETE
    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRoom(@PathParam("id") String id){
        
        boolean hasSensor = SensorService.getAllSensors()
                .stream()
                .anyMatch(sensor -> id.equals(sensor.getRoomId()));
        if (hasSensor){
            throw new RoomNotEmptyException("Room cannot be deleted, sensors are still assigned.");
        }
        Room deleted = RoomService.deleteRoom(id);
        if(deleted == null){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Room not found\"}")
                    .build();
        }
        return Response.ok(deleted).build();
    }
}
