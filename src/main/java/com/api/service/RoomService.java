/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.service;

import com.api.model.Room;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

/**
 *
 * @author Senesa
 */
public class RoomService {
    
    private static Map<String, Room> rooms = new HashMap<>();
    
    static{
        rooms.put("LIB-301", new Room("LIB-301", "Library", 50));
        rooms.put("LAB-101", new Room("LAB-101", "Computer Lab", 30));
    }
    
    public static Collection<Room> getAllRooms(){
        return rooms.values();
    }
    
    public static Room getRoom(String id){
        return rooms.get(id);
    }
    public static Room addRoom(Room room){
        rooms.put(room.getId(), room);
        return room;
    }
    public static Room updateRoom(String id, Room room){
        room.setId(id);
        rooms.put(id, room);
        return room;
    }
    public static Room deleteRoom(String id){
        return rooms.remove(id);
    }
}
