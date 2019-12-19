package com.taotao.search.mapper;

import com.taotao.pojo.SearchItem;

import java.util.List;

/**
 * 多表查询（不能用逆向工程，逆向工程只能生成单表）
 */
public interface Searchmapper {
    /**
     * 搜索三张表的接口
     * @return
     */
    List<SearchItem> getSearchList() throws Exception;
    /**
     * ActiveMQ的根据商品id获取商品数据的接口
     */
    SearchItem getSearchByid(Long itemId);
}
