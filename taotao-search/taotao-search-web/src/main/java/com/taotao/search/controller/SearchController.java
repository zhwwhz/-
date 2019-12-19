package com.taotao.search.controller;

import com.github.pagehelper.Page;
import com.taotao.pojo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.taotao.search.inter.SearchItem;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {
    @Value("${ROWS}")
    private Integer ROWS;
    @Autowired
    private SearchItem searchItem;

    @RequestMapping("/search")
    public String Srarch(Model model,@RequestParam(defaultValue ="1")Integer page,@RequestParam(value = "q") String query)
    {
        System.out.println(query+"搜索商品转码之前");
        try {
            query = new String(query.getBytes("iso8859-1"), "UTF-8");
            System.out.println(query+"搜索商品转码之后");
//            if (query.equals(new String(query.getBytes("GB2312"), "GB2312")))
//            {
//                System.out.println(29);
//                query = new String(query.getBytes("GB2312"), "utf-8");
//            }
//            if (query.equals(new String(query.getBytes("ISO-8859-1"), "ISO-8859-1")))
//            {
//                System.out.println(33);
//                query = new String(query.getBytes("ISO-8859-1"), "utf-8");
//            }
//            if (query.equals(new String(query.getBytes("GBK"), "GBK")))
//            {
//                System.out.println(37);
//                query = new String(query.getBytes("GBK"), "utf-8");
//            }
            SearchResult search = searchItem.search(query, page,ROWS);
            model.addAttribute("query",query);
            model.addAttribute("totalPages",search.getPageCount());
            model.addAttribute("itemList",search.getItemList());
            model.addAttribute("page",page);
            System.out.println(query);
            System.out.println(search);
            System.out.println(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "search";
    }
}
