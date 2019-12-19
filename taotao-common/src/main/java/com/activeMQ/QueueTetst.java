package com.activeMQ;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class QueueTetst {
    public static void main(String[] args) throws Exception{
//        //1.创建一个连接工厂,参数-要连接的服务器的地址
//        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
//        //2.通过工厂获取连接对象
//        Connection connection = factory.createConnection();
//        //3.开启连接
//        connection.start();
//        //4.创建一个session对象。提供发送消息的方法,参数1-表示是否开启事务（JTA叫做分布式事务），
//        // 参数2-设置消息的应答模式。手动和自动，但是只有参数1设置为false的时候才有意义。
//        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
//        //5.创建目的地，Queue-Topic,参数名称-目的地的名称。
//        Queue queue = session.createQueue("QUEUE-TEST");
//        //6.创建生产者
//        MessageProducer messageProducer = session.createProducer(queue);
//        //7.构建消息的内容
//        TextMessage textMessage = session.createTextMessage("这是QUEUE发送的消息。。。");
//        //8.发送消息
//        messageProducer.send(textMessage);
//        //9.关闭资源
//        messageProducer.close();
//        session.close();
//        connection.close();



        ConnectionFactory factory = new  ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE );
        Queue queue = session.createQueue("queue");
        MessageProducer producer = session.createProducer(queue);
        TextMessage textMessage = session.createTextMessage("这是QUEUE发送的消息。。。");
        producer.send(textMessage);
        producer.close();
        session.close();
        connection.close();

    }
}
