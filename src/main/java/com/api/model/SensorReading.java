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
    
    private String id;
    private double value;
    private long timestamp;
    
    public SensorReading(){}
    
    public SensorReading(String id, double value, long timestamp){
        this.id = id;
        this.value = value;
        this.timestamp = timestamp;
    }
    
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    
    public double getValue(){
        return value;
    }
    public void setValue(double value){
        this.value = value;
    }
    
    public long getTimestamp(){
        return timestamp;
    }
    public void setTimeStamp(long timestamp){
        this.timestamp = timestamp;
    }
}
