package com.tsystems.javaschool.evgenydubovitsky.cargomanager.util;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class JMSProducer {

    private Logger logger;

    private ActiveMQConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;
    private MessageProducer producer;

    public JMSProducer() throws JMSException {
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                ActiveMQConnection.DEFAULT_BROKER_URL
        );
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createTopic("CargoManager");
        producer = session.createProducer(destination);
    }

    public void sendMessage() {
        try {
            TextMessage message = session.createTextMessage("Update");
            producer.send(message);
        } catch (JMSException e) {
            if (logger == null) {
                logger = LoggerFactory.getLogger(JMSProducer.class);
            }
            logger.warn("Failed to send a message to broker: {} - {}", e.getClass().getSimpleName(), e.getMessage());
        }
    }
}