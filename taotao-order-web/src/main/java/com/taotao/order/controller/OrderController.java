package com.taotao.order.controller;


import com.taotao.CartFotInter;
import com.taotao.OrderInt;
import com.taotao.pojo.*;
import com.taotao.sso.UserRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private UserRegister userRegister;
    @Autowired
    private CartFotInter cartFotInter;
    @Autowired
    private OrderInt orderInt;
    /**
     * 展示购物车列表的方法
     */
    @RequestMapping("/order/order-cart")
    public String getOrderItemList(HttpServletRequest request , HttpServletResponse response , Model model)
    {
        //1.从cookie中通过token获取用户信息
        String tt_token = CookieUtils.getCookieValue(request, "TT_Token");
        System.out.println(tt_token+"******");
        //2.调用sso服务接口获取到用户信息
        TaotaoResult result = userRegister.getUserDataByToken("SsoForSession"+tt_token);
        System.out.println(result+"******");
        TbUser user = null;
        if(request!=null)
        {
             user = (TbUser)result.getData();
            System.out.println(user+"****");
        }
        //3.查询用户cookie中的商品列表
        List<TbItem> tbItemListByCookie = getTbItemListByCookie(request);
        System.out.println("tbItemListByCookie is "+tbItemListByCookie);
        //4.查询用户redis中的商品列表
        List<TbItem> tbItemListByRedis = cartFotInter.getItemListByRedisOrCookie(user.getId());
        System.out.println("tbItemListByRedis is "+tbItemListByRedis);
        //5.cookie中的商品列表和redis中的商品列表合并
        if(tbItemListByCookie != null)
        {
            System.out.println((tbItemListByCookie != null)+"535353");
            for(TbItem  rsCookie : tbItemListByCookie)//cookie不为空的情况下遍历cookie中的商品信息
            {
                if(tbItemListByRedis != null && tbItemListByRedis.size()>0 && !(tbItemListByRedis.isEmpty()))
                {
                    System.out.println((tbItemListByRedis != null && tbItemListByRedis.size()>0 && !(tbItemListByRedis.isEmpty()))+"585858");
                    for(TbItem rsRedis : tbItemListByRedis)//redis不为空的情况下遍历redis中的商品信息
                    {
                        if(rsCookie.getId()==rsRedis.getId().longValue())//判断是否相等，记得加上longValue()。
                        {
                            System.out.println(rsCookie.getId()+" ... "+rsRedis.getId().longValue()+"rsCookie.getId()==rsRedis.getId().longValue() 636363");
                            //相等的情况下数量相加并设置回去redis
                            System.out.println("redis中原本有数据62626262");
                            rsRedis.setNum(rsCookie.getNum()+rsRedis.getNum());
//                            jedisClient.hset(user.getId()+"", rsRedis.getId()+"" ,JsonUtils.objectToJson(rsRedis));
//                            jedisClient.expire(user.getId()+"" , 864000);
                            cartFotInter.updItemNumByRedis(user.getId(),rsRedis.getId(),rsRedis.getNum());

                        }
                        else
                        {
                            //若没在redis找到商品，则新加商品到redis中
                           // jedisClient.hset(user.getId()+"", rsCookie.getId()+"" ,JsonUtils.objectToJson(rsCookie));
                            //jedisClient.expire(user.getId()+"" , 864000);
                            System.out.println("redis中原本没有数据72727272");
                            cartFotInter.addItemCart(user.getId(), rsCookie ,rsCookie.getNum());

                        }
                    }
                }
                else //redis中原本没有数据
                    {
                        System.out.println("redis中原本没有数据79797979");
                        cartFotInter.addItemCart(user.getId(), rsCookie ,rsCookie.getNum());
                    }
            }
        }
        //6.删除cookie中的商品列表。从redis中获取新的商品列表
        // 9、合并数据后删除cookie
//        if (tbItemListByCookie!=null) {
//            CookieUtils.deleteCookie(request, response, "COOKIE_CART_KEY");
//        }
        System.out.println("id is "+user.getId());
        List<TbItem> newTbitemList =cartFotInter.getItemListByRedisOrCookie(user.getId());
        System.out.println("newTbitemList is "+newTbitemList);
        //7.model设置返回的参数数据
        model.addAttribute("cartList" , newTbitemList);
        return "order-cart";
    }
    /**
     * 从Cookie中获取商品列表信息的方法
     */
    private List<TbItem> getTbItemListByCookie(HttpServletRequest request)
    {
        //COOKIE_CART_KEY
        String cookie_cart_key = CookieUtils.getCookieValue(request, "COOKIE_CART_KEY", true);
        //为空则返回空字符串
        if(cookie_cart_key == null)
            return new ArrayList<>();
        //不为空则返回数据，cookie_cart_key转换成list，cookie中存的是json。
        List<TbItem> list = JsonUtils.jsonToList(cookie_cart_key, TbItem.class);
        return list;
    }

    /**
     * 创建订单的方法
     */
    @RequestMapping(value = "/order/create" , method = RequestMethod.POST)
    public String createOrderInfo(orderInfo  orderInfo ,Model model)
    {
        TaotaoResult orderInfo1 = orderInt.createOrderInfo(orderInfo);
        String payment = orderInfo.getPayment();
        model.addAttribute("orderId" , orderInfo1.getData().toString());
        model.addAttribute("payment" , payment);
        model.addAttribute("date" ,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        return "success";
    }
}
