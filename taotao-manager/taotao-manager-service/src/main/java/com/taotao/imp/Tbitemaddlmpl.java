package com.taotao.imp;

import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.IDUtils;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.services.Tbitemadd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;

@Service
public class Tbitemaddlmpl implements Tbitemadd {
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private JmsTemplate template;
    @Resource(name = "topic")
    private javax.jms.Destination destination;
    @Override
    public TaotaoResult saveItem(TbItem item, String desc) {
        // 1、生成商品id，本例中使用工具类IDUtils生成商品id
        final Long itemId = IDUtils.genItemId();
        item.setId(itemId);
        // 2、补全商品表TbItem的其他属性
        // 商品状态，1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        // 3、向商品表中插入数据
        tbItemMapper.insert(item);
        // 4、创建一个商品描述表TbItemDesc对象
        TbItemDesc itemDesc = new TbItemDesc();
        // 5、补全商品描述表TbItemDesc的其他属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        // 6、向商品描述表中插入数据
        tbItemDescMapper.insert(itemDesc);

        /**
         * 在返回之前添加业务逻辑，向MQ发送消息。同步数据库跟索引库
         */
        template.send(destination, new MessageCreator()
        {
            @Override
            public Message createMessage(Session session) throws JMSException
            {
                return session.createTextMessage(itemId+"");
            }
        });

        // 7、返回TaotaoResult.ok()
        return TaotaoResult.ok();
    }
}
