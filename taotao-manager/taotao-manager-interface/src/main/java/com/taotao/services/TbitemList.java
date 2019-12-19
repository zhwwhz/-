package com.taotao.services;

import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import org.springframework.stereotype.Repository;

/**
 * 数据业务层接口
 */
//@Repository
public interface TbitemList {
    //查询接口
     EasyUIDataGridResult show(Integer total,Integer rows);
    //删除接口
    void DelitemById(Long id);
    //商品下架
    void instock(Long id);
    //商品上架
    void reshelf(Long id);
    //编辑商品
    void Upditem(TbItem item, TbItemDesc tbItemDesc);
    /**
     * 查询商品详情
     */
    TbItem getItemMoreById(Long itemId);
    /**
     * 查询商品详情中的描述，从描述表获取
     */
    TbItemDesc getItemDescById(Long itemId);
}
