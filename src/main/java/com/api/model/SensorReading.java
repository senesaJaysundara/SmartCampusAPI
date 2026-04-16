/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.model;

/**
 *
 * @author Senesa
 */
public class SensorReading {
    
    private int id;
    private double value;
    private String timestamp;
    
    public SensorReading(){}
    
    public SensorReading(int id, double value, String timestamp){
        this.id = id;
        this.value = value;
        this.timestamp = timestamp;
    }
    
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    
    public double getValue(){
        return value;
    }
    public void setValue(double value){
        this.value = value;
    }
    
    public String getTimestamp(){
        return timestamp;
    }
    public void setTimeStamp(String timestamp){
        this.timestamp = timestamp;
    }
}
