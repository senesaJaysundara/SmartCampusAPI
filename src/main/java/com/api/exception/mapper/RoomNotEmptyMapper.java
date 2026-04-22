/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.exception.mapper;

import com.api.exception.RoomNotEmptyException;

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
public class RoomNotEmptyMapper implements ExceptionMapper<RoomNotEmptyException>{
    
    @Override
    public Response toResponse(RoomNotEmptyException ex){
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        
        return Response.status(Response.Status.CONFLICT) // 409
                .entity(error)
                .build();
    }
}
