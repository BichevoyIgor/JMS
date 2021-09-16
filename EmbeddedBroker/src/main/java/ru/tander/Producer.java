package ru.tander;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import javax.jms.*;

public class Producer {
    private String connector;
    private int session;
    private String queueName;
    private static final Logger logger = Logger.getLogger(Producer.class);

    public Producer(String connector, int session, String queueName) {
        this.connector = connector;
        this.session = session;
        this.queueName = queueName;

    }

    public void start() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(connector);
        try {
            Connection connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession((this.session == 0 ? true : false), this.session);
            Queue queue = session.createQueue(queueName);
            MessageProducer messageProducer = session.createProducer(queue);
            messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
            logger.info("Producer start work");
            long start = System.currentTimeMillis();
            for (int i = 0; i < 12000; i++) {
                messageProducer.send(session.createTextMessage("Тестовое сообщение " + i));
            }
            long end = System.currentTimeMillis();
            logger.info("Producer finish work "  + (end - start) + "ms");
            if (this.session == 0) {
                session.commit();
            }
            messageProducer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
