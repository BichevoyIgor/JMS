package ru.tander;

import org.apache.activemq.broker.BrokerService;


public class Broker {
    private final String brokerURL;
    private final boolean isPersist;
    private BrokerService brokerService;


    public Broker(String adress, boolean isPersist) {
        this.brokerURL = adress;
        this.isPersist = isPersist;
    }

    public void start() {
        try {
            brokerService = new BrokerService();
            brokerService.setUseJmx(false);
            brokerService.setPersistent(isPersist);
            brokerService.setBrokerName("brokerr");
            brokerService.addConnector(brokerURL);
            brokerService.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
