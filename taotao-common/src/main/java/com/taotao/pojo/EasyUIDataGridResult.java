package com.taotao.pojo;

import com.github.pagehelper.PageHelper;

import java.io.Serializable;
import java.util.List;

/**
 * json包装类，在不同系统间数据传递，要实现序列化接口，
 * 查询数据用的
 */
public class EasyUIDataGridResult implements Serializable {
    private Long total;

    private List<?> rows;

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
        return "EasyUIDataGridResult{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
