package com.taotao.mapper;

import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;

import java.util.List;


public interface TbContentMapper {
    int countByExample(TbContentExample example);

    int deleteByExample(TbContentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbContent record);

    int insertSelective(TbContent record);

    List<TbContent> selectByExampleWithBLOBs( TbContentExample example);

    List<TbContent> selectByExample( TbContentExample example);

    TbContent selectByPrimaryKey(Long id);

    int updateByExampleSelective( TbContent record, TbContentExample example);

    int updateByExampleWithBLOBs(TbContent record,  TbContentExample example);

    int updateByExample( TbContent record,  TbContentExample example);

    int updateByPrimaryKeySelective(TbContent record);

    int updateByPrimaryKeyWithBLOBs(TbContent record);

    int updateByPrimaryKey(TbContent record);
}