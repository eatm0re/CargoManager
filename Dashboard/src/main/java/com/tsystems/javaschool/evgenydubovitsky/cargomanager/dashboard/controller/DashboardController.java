package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dashboard.controller;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ApplicationScoped
@ServerEndpoint("/")
public class DashboardController {

    @OnOpen
    public void open(Session session) {
        
    }
}
