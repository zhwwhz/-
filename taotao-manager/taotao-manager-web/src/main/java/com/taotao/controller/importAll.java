package com.taotao.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.search.inter.SearchItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class importAll {
    @Autowired
    private SearchItem searchItem;
    @RequestMapping(value = "/index/importAll" ,method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult getSearchList()
    {
        return searchItem.getSearchItenList();

    }
}
