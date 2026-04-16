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
    
    private static Map<Integer, Room> rooms = new HashMap<>();
    
    static{
        rooms.put(1, new Room(1, "Lecture Hall", 100));
        rooms.put(2, new Room(2, "Computer Lab", 50));
    }
    
    public static Collection<Room> getAllRooms(){
        return rooms.values();
    }
    
    public static Room getRoom(int id){
        return rooms.get(id);
    }
    public static Room addRoom(Room room){
        rooms.put(room.getId(), room);
        return room;
    }
    public static Room updateRoom(int id, Room room){
        room.setId(id);
        rooms.put(id, room);
        return room;
    }
    public static Room deleteRoom(int id){
        return rooms.remove(id);
    }
}
