package com.taotao.mapper;

import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemDescExample;
import java.util.List;


public interface TbItemDescMapper {
    int countByExample(TbItemDescExample example);

    int deleteByExample(TbItemDescExample example);

    int deleteByPrimaryKey(Long itemId);

    int insert(TbItemDesc record);

    int insertSelective(TbItemDesc record);

    List<TbItemDesc> selectByExampleWithBLOBs(TbItemDescExample example);

    List<TbItemDesc> selectByExample(TbItemDescExample example);

    TbItemDesc selectByPrimaryKey(Long itemId);

    int updateByExampleSelective( TbItemDesc record,  TbItemDescExample example);

    int updateByExampleWithBLOBs( TbItemDesc record,  TbItemDescExample example);

    int updateByExample( TbItemDesc record,  TbItemDescExample example);

    int updateByPrimaryKeySelective(TbItemDesc record);

    int updateByPrimaryKeyWithBLOBs(TbItemDesc record);

    int updateByPrimaryKey(TbItemDesc record);
}