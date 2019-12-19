package com.taotao.content.services;

import com.taotao.pojo.EasyUITreeNode;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;

import java.util.List;

/**
 * 内容分类接口-树形结构
 */
public interface TbcategoryList {
    //获取列表
    List<EasyUITreeNode> showCategory(Long id);
    //新建分类
    TaotaoResult createCatdgory(Long id,String name);
    //删除分类
    void deleteCategory(Long id);
    //重命名分类
    void updateCategory(Long id,String name);
}
