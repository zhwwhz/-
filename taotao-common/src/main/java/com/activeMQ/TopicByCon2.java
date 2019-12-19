package com.activeMQ;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopicByCon2 {
    public static void main(String[] args)throws  Exception {
        ConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //Queue queue = session.createQueue("topic-test1");
        Topic topic = session.createTopic("topic-test1");
        MessageConsumer consumer = session.createConsumer(topic);
        TextMessage textMessage = session.createTextMessage();
        System.out.println("start");
        consumer.setMessageListener(new MessageListener() {
            //监听器-当监听到消息时执行
            @Override
            public void onMessage(Message message) {
                System.out.println(message);
            }
        });
        System.out.println("end");
        Thread.sleep(11100000);
        consumer.close();
        session.close();
        connection.close();
    }
}
