package com.activeMQ;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage)
        {
            TextMessage message1 = (TextMessage)message;
            String text;
            try {
                text = message1.getText();
                System.out.println(text);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        System.out.println(message);
    }
}
