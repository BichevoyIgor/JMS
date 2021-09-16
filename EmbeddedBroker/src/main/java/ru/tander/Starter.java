package ru.tander;

import org.apache.log4j.Logger;
import javax.jms.Session;

public class Starter {
    private static String brokerURL = "tcp://localhost:61616";
   // private static String brokerURL = "vm://brokerr";

    private static boolean persistent = true;
    //private static boolean persistent = false;

   // private static int session = Session.AUTO_ACKNOWLEDGE;
  // private static int session = Session.CLIENT_ACKNOWLEDGE;
  //  private static int session = Session.DUPS_OK_ACKNOWLEDGE;
    private static int session = Session.SESSION_TRANSACTED;

    private static String queueName = "queue_test";

    private static final Logger logger = Logger.getLogger(Starter.class);

    public static void main(String[] args){
        logger.info(String.format("Start with parameters: %s, persistent %s, %s", brokerURL, persistent, session));
        long start = System.currentTimeMillis();
        Broker broker = new Broker(brokerURL, persistent);
        broker.start();
        Producer producer = new Producer(brokerURL, session, queueName);
        producer.start();
        Consumer consumer = new Consumer(brokerURL, session, queueName);
        consumer.start();
        long end = System.currentTimeMillis();
        System.err.println("Время выполнения: " + (end - start) + "ms");
        logger.info("!!!!!!!!!!!!!!End!!!!!!!!!!!!!!");
    }
}
