package com.taotao.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.ws.Action;


/**
 * 接收页面传递过来的参数决定跳转到哪个页面
 */
@Controller
public class PgaeController {

    @RequestMapping("/page/{page}")
    public String PageController(@PathVariable String page,String redirect, Model model) {
        model.addAttribute("redirect", redirect);//为了登录以后跳转到订单确认的页面设置的值
        System.out.println("redirect is  "+redirect);
        return page;
    }
}
