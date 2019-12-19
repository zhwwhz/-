package com.taotao.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 内容管理表的查询
 */
public class EasyUICategoryResult implements Serializable {
    private Long categoryId;
    private Long total;
    private List<?> rows;

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "EasyUICategoryResult{" +
                "categoryId=" + categoryId +
                ", total=" + total +
                ", rows=" + rows +
                '}';
    }
}
