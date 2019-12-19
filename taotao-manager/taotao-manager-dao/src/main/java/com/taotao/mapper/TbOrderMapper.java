package com.taotao.mapper;

import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderExample;
import java.util.List;


public interface TbOrderMapper {
    int countByExample(TbOrderExample example);

    int deleteByExample(TbOrderExample example);

    int deleteByPrimaryKey(String orderId);

    int insert(TbOrder record);

    int insertSelective(TbOrder record);

    List<TbOrder> selectByExample(TbOrderExample example);

    TbOrder selectByPrimaryKey(String orderId);

    int updateByExampleSelective( TbOrder record,  TbOrderExample example);

    int updateByExample( TbOrder record,  TbOrderExample example);

    int updateByPrimaryKeySelective(TbOrder record);

    int updateByPrimaryKey(TbOrder record);
}