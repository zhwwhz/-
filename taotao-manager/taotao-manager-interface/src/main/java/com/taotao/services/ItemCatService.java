package com.taotao.services;

import com.taotao.pojo.EasyUITreeNode;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

import java.util.List;

/**
 * 数据类目接口
 */
public interface ItemCatService {
    /**
     * 查询数据类目
     * @param parentId
     * @return
     */
    List<EasyUITreeNode> getItemCatList(Long parentId);

}