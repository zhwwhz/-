package com;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.print.attribute.standard.Destination;

/**
 * Spring整合ActiveQueue
 */
public class SpringAndMQueue {
    public static void main(String[] args) throws  Exception{
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applictionContext-activeMq.xml");
        //创建模板类的接口
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        //创建目的地
        javax.jms.Destination destination = context.getBean(javax.jms.Destination.class);
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("Spring111111111");
            }
        });
    }
}
