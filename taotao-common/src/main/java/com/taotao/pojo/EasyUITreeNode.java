package com.taotao.pojo;

import java.io.Serializable;

/**
 * 查询树形结构用的
 */
public class EasyUITreeNode implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String text;

    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "EasyUITreeNode [id=" + id + ", text=" + text + ", state=" + state + "]";
    }
}
