package com.taotao.order.impl;

import com.taotao.OrderInt;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.impl.jedis.JedisClient;
import com.taotao.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderImpl implements OrderInt {
    @Autowired
    private TbOrderMapper tbOrderMapper;
    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;
    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;
    @Autowired
    private JedisClient jedisClient;
    @Override
    public TaotaoResult createOrderInfo(orderInfo orderInfo) {
        /*
            业务逻辑：
            参数：提交的是表单的数据。保存的数据：订单、订单明细、配送地址。
            a) 向tb_order中插入记录。
                i.订单号需要手动生成。
                    要求订单号不能重复。
                    订单号可读性好。
                    可以使用redis的incr命令生成订单号。订单号需要一个初始值。
                ii.payment：表单数据
                iii.payment_type：表单数据
                iv.user_id：用户信息
                v.buyer_nick：用户名
                vi.其他字段null
            b) 向tb_order_item订单明细表中插入数据。
                i.id：使用incr生成
                ii.order_id：生成的订单号
                iii.其他的都是表单中的数据
            c) 向tb_order_shipping订单配送信息中插入数据。
                i.order_id：生成的订单号
                ii.其他字段都是表单中的数据
            d) 使用pojo接收表单的数据。
                可以扩展TbOrder，在子类中添加两个属性一个是商品明细列表，一个是配送信息表。
                把pojo放到taotao-order-interface工程中。
         */

        //1.插入TbOrder表
        TbOrder tbOrder = new TbOrder();
        jedisClient.incr("orderid");
        String orderid = jedisClient.get("orderid");
        tbOrder.setOrderId(orderid);
        tbOrder.setPayment(orderInfo.getPayment());
        tbOrder.setPaymentType(orderInfo.getPaymentType());
        tbOrder.setUserId((long)20191119);
        tbOrder.setBuyerNick("59874986");
        tbOrderMapper.insertSelective(tbOrder);

        //2.插入tb_order_item表
        TbOrderItem tbOrderItem = new TbOrderItem();
        tbOrderItem.setId(orderid);
        tbOrderItem.setItemId(orderid);
        tbOrderItem.setNum(789789);
        tbOrderItem.setOrderId(orderid);
        tbOrderItem.setPicPath("tupianlujing");
        tbOrderItem.setPrice((long)99999999);
        tbOrderItem.setTitle("biaoshu");
        tbOrderItem.setTotalFee((long)2019119);
        tbOrderItemMapper.insertSelective(tbOrderItem);

        //3.插入tb_order_shipping表
        TbOrderShipping tbOrderShipping = new TbOrderShipping();
        tbOrderShipping.setCreated(new Date());
        tbOrderShipping.setOrderId(orderid);
        tbOrderShipping.setReceiverAddress(orderInfo.getOrderShipping().getReceiverAddress());
        tbOrderShipping.setReceiverCity(orderInfo.getOrderShipping().getReceiverCity());
        tbOrderShipping.setReceiverDistrict(orderInfo.getOrderShipping().getReceiverDistrict());
        tbOrderShipping.setReceiverMobile(orderInfo.getOrderShipping().getReceiverMobile());
        tbOrderShipping.setReceiverName(orderInfo.getOrderShipping().getReceiverName());
        tbOrderShipping.setReceiverPhone(orderInfo.getOrderShipping().getReceiverPhone());
        tbOrderShipping.setReceiverState(orderInfo.getOrderShipping().getReceiverState());
        tbOrderShipping.setReceiverZip(orderInfo.getOrderShipping().getReceiverZip());
        tbOrderShipping.setUpdated(new Date());
        tbOrderShippingMapper.insertSelective(tbOrderShipping);
        return TaotaoResult.ok(orderid);
    }
}
