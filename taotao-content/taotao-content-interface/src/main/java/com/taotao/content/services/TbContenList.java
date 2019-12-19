package com.taotao.content.services;

import com.taotao.pojo.EasyUICategoryResult;
import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * 内容管理表的接口
 */
public interface TbContenList {
    //自己写的查询内容的接口
//    EasyUICategoryResult FindCategoryListByid(Long categoryId, Integer total, Integer rows);
    //参考的查询内容的接口
    EasyUIDataGridResult  getContentList(Long categoryId, Integer page, Integer rows);
    //新增内容
    TaotaoResult SaveContent(TbContent content);
    //删除内容
    void DeleContentById(Long id);
    //编辑内容
    void UpdContent(TbContent tbContent);

    /**
     * 首页大广告图的接口，需要针对categoryId进行查询，返回的结果是集合。
     * @param categoryId
     * @return
     */
    List<TbContent> getContentByCategoryId(Long categoryId);
}
