package com.taotao.services;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

/**
 * 商品新增的接口
 */
public interface Tbitemadd {
    /**
     * 根据商品的基础数据和商品的描述信息插入商品（插入商品表和商品描述表）
     * @param item
     * @param desc
     * @return
     */
    TaotaoResult saveItem(TbItem item, String desc);
}
