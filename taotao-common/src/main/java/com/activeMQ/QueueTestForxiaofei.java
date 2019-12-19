package com.activeMQ;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * ActiveMQ的消费者-用于获取生产者发送的消息
 */
public class QueueTestForxiaofei {
    public static void main(String[] args) throws Exception{
        /**
         * 1.创建连接工厂对象
         * 2.通过连接工厂对象获取连接
         * 3.开启连接
         * 4.创建Session，提供一系列方法
         * 5.创建接收消息的目的地
         * 6.创建消费者
         * 7.接收消息
         * 8.关闭资源
         */
        ConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("queue");
        MessageConsumer consumer = session.createConsumer(queue);
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
        consumer.close();
        session.close();
        connection.close();
    }
}
