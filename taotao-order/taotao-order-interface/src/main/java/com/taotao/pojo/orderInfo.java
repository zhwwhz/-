package com.taotao.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 订单系统的顶点提交所需要在Controller中接收到前端传输过来的三张表的数据。
 * 所以要创建一个pojo来接收这三张表的数据。
 * 这也就是为什么要创建一个pojo，是为了解决如何接收前端发送过来的包含三张表的数据。
 */
public class orderInfo extends TbOrder implements Serializable {
    // 订单明细表
    private List<TbOrderItem> orderItems; // springmvc属性绑定的命名要求：与页面上的一致

    // 订单物流表
    private TbOrderShipping orderShipping; // springmvc属性绑定的命名要求：与页面上的一致

    //另外的payment跟paymentType属性通过继承TBOrder表获取到。

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
