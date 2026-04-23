/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.MediaType;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Senesa
 */
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable>{
    
    @Override
    public Response toResponse (Throwable ex){
        Map<String, String> error = new HashMap<>();
        error.put("error", "Internal Server Error");
        error.put("message", ex.getMessage() != null ? ex.getMessage():"An unexpected error occured");
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR) 
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
