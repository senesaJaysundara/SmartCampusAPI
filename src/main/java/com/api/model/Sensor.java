/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.model;

/**
 *
 * @author Senesa
 */
public class Sensor {
    
    private String id;
    private String type;
    private double value;
    private String roomId;
    private String status;
    
    public Sensor(){}
    
    public Sensor(String id, String type, double value, String roomId, String status){
        this.id = id;
        this.type = type;
        this.value = value;
        this.roomId = roomId;
        this.status = status;
    }
    
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    
    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }
    
    public double getValue(){
        return value;
    }
    public void setValue(double value){
        this.value = value;
    }
    
    public String getRoomId(){
        return roomId;
    }
    public void setRoomId(String roomId){
        this.roomId = roomId;
    }
    
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
}
