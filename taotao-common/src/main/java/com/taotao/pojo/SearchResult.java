package com.taotao.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<SearchItem> itemList; // 搜索的结果列表（列表中放的是新的商品数据！不是之前的Item）
    private Long recordCount; // 总记录数
    private Long pageCount; // 总页数

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }
}
