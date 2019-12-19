package com.taotao;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.orderInfo;

/**
 * 订单系统的接口，创建一个订单，包含订单ID
 */
public interface OrderInt {
    /**
     * 创建订单
     */
    public TaotaoResult createOrderInfo(orderInfo orderInfo);
}
