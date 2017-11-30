package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dashboard;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ApplicationScoped
@ServerEndpoint("/socket")
public class DashboardController {

    @Inject
    private JMSProcessor jms;

    @OnOpen
    public void open(Session session) {
        jms.addSubscriber(session);
    }

    @OnClose
    public void close(Session session) {
        jms.removeSubscriber(session);
    }
}
