package com.taotao.controller;

import com.taotao.content.services.TbContenList;
import com.taotao.content.services.TbcategoryList;
import com.taotao.pojo.AD1Node;
import com.taotao.pojo.JsonUtils;
import com.taotao.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 门户首页
 */
@Controller
public class crotroller {
    @Autowired
    private TbContenList tbContenList;
    @Value("${AD1_CATEGORY_ID}")
    private Long categoryid;
    @Value("${AD1_HEIGHT}")
    private Integer AD1_HEIGHT;
    @Value("${AD1_HEIGHT_B}")
    private Integer AD1_HEIGHT_B;
    @Value("${AD1_WIDTH}")
    private Integer AD1_WIDTH;
    @Value("${AD1_WIDTH_B}")
    private Integer AD1_WIDTH_B;

    @RequestMapping("/index")
    public String  index(Model model)
    {


    //添加业务逻辑，根据内容分类的id，查询内容列表（首页大广告位）
    //转换成自定义的pojo列表
    //通过model传递数据给jsp
    List<TbContent> list = tbContenList.getContentByCategoryId(categoryid);
    List<AD1Node> aD1NodeList = new ArrayList<>();
        for (TbContent tbContent : list) {
                AD1Node aD1Node = new AD1Node();
                aD1Node.setAlt(tbContent.getSubTitle());
                aD1Node.setHref(tbContent.getUrl());
                aD1Node.setSrc(tbContent.getPic());
                aD1Node.setSrcB(tbContent.getPic2());
                aD1Node.setHeight(AD1_HEIGHT);
                aD1Node.setHeightB(AD1_HEIGHT_B);
                aD1Node.setWidth(AD1_WIDTH);
                aD1Node.setWidthB(AD1_WIDTH_B);
                aD1NodeList.add(aD1Node);
                }
                //传递数据给jsp，直接传递aD1NodeList的话是对象，取不了。要转换成json
                model.addAttribute("ad1", JsonUtils.objectToJson(aD1NodeList));
        return "index";
    }
}


