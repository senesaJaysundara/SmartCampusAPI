/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.resource;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author Senesa
 */

@Path("/discovery")
public class DiscoveryResource {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getApiInfo(){
         Map<String, Object> response = new HashMap<>();
         
         response.put("version", "v1");
         response.put("description", "Smart Campus API");
         response.put("contact", "admin@university.com");
         
         Map<String, String> links = new HashMap<>();
         links.put("rooms", "/api/v1/rooms");
         links.put("sensors","/api/v1/sensors");
         
         response.put("resources", links);
         
         return response;
    }
}
