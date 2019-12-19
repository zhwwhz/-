package com.taotao.search.inter;

import com.taotao.pojo.SearchResult;
import com.taotao.pojo.TaotaoResult;

/**
 * 三张表搜索的接口层
 */
public interface SearchItem
{
    /**
     * 三张表查询的接口
     * @return
     */
    public TaotaoResult getSearchItenList();

    /**
     *  前端页面查询栏的接口
     * @param query 查询的主条件
     * @param page  页码
     * @param rows  显示的条数
     * @return
     * @throws Exception
     */
    public SearchResult search(String query,Integer page,Integer rows) throws Exception;
}