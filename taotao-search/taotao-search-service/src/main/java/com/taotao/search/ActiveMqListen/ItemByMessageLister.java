package com.taotao.search.ActiveMqListen;

import com.taotao.search.Dao.SearchDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 查询时的监听方法
 */
public class ItemByMessageLister implements MessageListener
{
    @Autowired
    private  SearchDao searchDao;
    @Override
    public void onMessage(Message message) {

        if(message instanceof TextMessage)
        {
            TextMessage str1 =(TextMessage)message;
            try
            {
                String text = str1.getText();
                long id = Long.parseLong(text);
                searchDao.UpdateIndexById(id);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
