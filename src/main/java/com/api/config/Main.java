/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.api.config;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import java.net.URI;

/**
 *
 * @author Senesa
 */

public class Main {

    public static final String BASE_URI = "http://localhost:8080/api/v1";
    
    public static HttpServer startServer(){
        final ResourceConfig rc = new ResourceConfig()
                .packages("com.api")
                .register(com.api.resource.DiscoveryResource.class)
                .register(com.api.resource.RoomResource.class)
                .register(com.api.resource.SensorResource.class)
                .register(com.api.filter.LoggingFilter.class);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }
    
    public static void main(String[] args) {
        final HttpServer server = startServer();
        System.out.println("Server started at " + BASE_URI);
    }
    
}
