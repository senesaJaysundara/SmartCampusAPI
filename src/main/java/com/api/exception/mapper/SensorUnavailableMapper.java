/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.exception.mapper;

import com.api.exception.SensorUnavailableException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Senesa
 */
@Provider
public class SensorUnavailableMapper implements ExceptionMapper<SensorUnavailableException>{
    
    @Override
    public Response toResponse (SensorUnavailableException ex){
        Map<String, String> error = new HashMap<>();
        error.put("error", "Forbidden");
        error.put("message", ex.getMessage());
        
        return Response.status(Response.Status.FORBIDDEN) //403
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
