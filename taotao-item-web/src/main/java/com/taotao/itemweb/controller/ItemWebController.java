package com.taotao.itemweb.controller;

import com.taotao.itemweb.pojo.itemweb;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.services.TbitemList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemWebController {
    @Autowired
    TbitemList tbitemList;
    @RequestMapping("/item/{itemId}")
    //{itemId}括号里面的参数必须和方法的参数列表一样
    public String getItemMore(@PathVariable Long itemId, Model model)
    {
        TbItem itemMoreById = tbitemList.getItemMoreById(itemId);
        TbItemDesc itemDescById = tbitemList.getItemDescById(itemId);
        itemweb  itemweb = new itemweb(itemMoreById);
        model.addAttribute("item",itemweb);
        model.addAttribute("itemDesc",itemDescById);

        return "item";
    }
}
