package com.taotao.mapper;

import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import java.util.List;

public interface TbUserMapper {
    int countByExample(TbUserExample example);

    int deleteByExample(TbUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbUser record);

    int insertSelective(TbUser record);

    List<TbUser> selectByExample(TbUserExample example);

    TbUser selectByPrimaryKey(Long id);

    int updateByExampleSelective( TbUser record,  TbUserExample example);

    int updateByExample( TbUser record,  TbUserExample example);

    int updateByPrimaryKeySelective(TbUser record);

    int updateByPrimaryKey(TbUser record);
}