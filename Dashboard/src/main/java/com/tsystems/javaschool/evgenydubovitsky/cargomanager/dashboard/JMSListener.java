package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dashboard;

import org.jboss.ejb3.annotation.ResourceAdapter;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(
                propertyName = "destinationType",
                propertyValue = "javax.jms.Topic"
        ),
        @ActivationConfigProperty(
                propertyName = "destination",
                propertyValue = "CargoManager"
        ),
        @ActivationConfigProperty(
                propertyName = "acknowledgeMode",
                propertyValue = "Auto-acknowledge"
        )
})
@ResourceAdapter("activemq")
public class JMSListener implements MessageListener {

    @Inject
    private JMSProcessor processor;

    @Override
    public void onMessage(Message message) {
        processor.notifySubscribers();
    }
}
