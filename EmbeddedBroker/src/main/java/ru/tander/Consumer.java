package ru.tander;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import javax.jms.*;
import java.io.IOException;


public class Consumer {
    private String brokerURL;
    private int session;
    private String queueName;
    private static final Logger logger = Logger.getLogger(Consumer.class);

    public Consumer(String brokerURL, int session, String queueName) {
        this.brokerURL = brokerURL;
        this.session = session;
        this.queueName = queueName;
    }

    public void start() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);
        try {
            Connection connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession((this.session==0?true:false), this.session);
            Queue queue = session.createQueue(queueName);
            MessageConsumer messageConsumer = session.createConsumer(queue);
            logger.info("Consumer start work");
            long start = System.currentTimeMillis();
            messageConsumer.setMessageListener((message) -> {
                try {
                    if (message instanceof TextMessage) {
                        System.err.println("Получено сообщение: " + ((TextMessage) message).getText());
                    }else {
                        System.out.println(message);
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });
            long end = System.currentTimeMillis();
            logger.info("Consumer finish work "  + (end - start) + "ms");
//            Message message = messageConsumer.receive();
//            if (message instanceof TextMessage){
//                System.out.println("Получено сообщение: " + ((TextMessage)message).getText());
//            }else {
//                System.out.println(message);
//            }
//            try {
//                System.in.read();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            messageConsumer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
