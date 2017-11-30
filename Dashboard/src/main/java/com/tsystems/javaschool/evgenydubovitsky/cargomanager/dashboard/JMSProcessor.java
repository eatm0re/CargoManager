package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dashboard;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashSet;

@ApplicationScoped
public class JMSProcessor {

    private HashSet<Session> subscribers = new HashSet<>();

    public void notifySubscribers() {
        for (Session session : subscribers) {
            sendMessage(session, "update");
        }
    }

    public void addSubscriber(Session session) {
        subscribers.add(session);
    }

    public boolean removeSubscriber(Session session) {
        return subscribers.remove(session);
    }

    private void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            subscribers.remove(session);
        }
    }
}
