package com.taotao.mapper;

import com.taotao.pojo.TbOrderShipping;
import com.taotao.pojo.TbOrderShippingExample;
import java.util.List;


public interface TbOrderShippingMapper {
    int countByExample(TbOrderShippingExample example);

    int deleteByExample(TbOrderShippingExample example);

    int deleteByPrimaryKey(String orderId);

    int insert(TbOrderShipping record);

    int insertSelective(TbOrderShipping record);

    List<TbOrderShipping> selectByExample(TbOrderShippingExample example);

    TbOrderShipping selectByPrimaryKey(String orderId);

    int updateByExampleSelective( TbOrderShipping record, TbOrderShippingExample example);

    int updateByExample( TbOrderShipping record,  TbOrderShippingExample example);

    int updateByPrimaryKeySelective(TbOrderShipping record);

    int updateByPrimaryKey(TbOrderShipping record);
}