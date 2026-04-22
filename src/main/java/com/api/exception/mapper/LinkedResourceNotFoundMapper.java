/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.exception.mapper;

import com.api.exception.LinkedResourceNotFoundException;

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
public class LinkedResourceNotFoundMapper implements ExceptionMapper<LinkedResourceNotFoundException>{
    
    @Override
    public Response toResponse (LinkedResourceNotFoundException ex){
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        
        return Response.status(422) //unprocessable entity
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
