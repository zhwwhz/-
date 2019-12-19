package com.activeMQ;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * ActiveMQ的发布-订阅模式
 */
public class TopicByProducer {
    public static void main(String[] args) throws  Exception{
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("topic-test1");
        MessageProducer producer = session.createProducer(topic);
        TextMessage textMessage = session.createTextMessage("topic--1111111111111111");
        producer.send(textMessage);
        producer.close();
        session.close();
        connection.close();
    }
}
