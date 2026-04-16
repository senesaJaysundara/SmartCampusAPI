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
    
    private int id;
    private String type;
    private double value;
    private int roomId;
    
    public Sensor(){}
    
    public Sensor(int id, String type, double value, int roomId){
        this.id = id;
        this.type = type;
        this.value = value;
        this.roomId = roomId;
    }
    
    public int getId(){
        return id;
    }
    public void setId(int id){
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
    
    public int getRoomId(){
        return roomId;
    }
    public void setRoomId(int roomId){
        this.roomId = roomId;
    }
}
