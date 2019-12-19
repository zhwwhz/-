package com.taotao.mapper;

import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import java.util.List;


public interface TbItemCatMapper {
    int countByExample(TbItemCatExample example);

    int deleteByExample(TbItemCatExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbItemCat record);

    int insertSelective(TbItemCat record);

    List<TbItemCat> selectByExample(TbItemCatExample example);

    TbItemCat selectByPrimaryKey(Long id);

    int updateByExampleSelective( TbItemCat record,  TbItemCatExample example);

    int updateByExample( TbItemCat record,  TbItemCatExample example);

    int updateByPrimaryKeySelective(TbItemCat record);

    int updateByPrimaryKey(TbItemCat record);
}