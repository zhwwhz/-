package com.taotao.cart.controller;

import com.taotao.CartFotInter;

import com.taotao.pojo.*;
import com.taotao.services.TbitemList;
import com.taotao.sso.UserRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车模块的控制层
 */
@Controller
public class CartForController {
    @Autowired
    private CartFotInter cartFotInter;
    @Autowired
    private UserRegister userRegister;
    @Autowired
    private TbitemList tbitemList;
    @Value("${TAOTAO.A}")
    private String TT_Token;
    @Value("${COOKIE_CART_KEY}")
    private String  COOKIE_CART_KEY;


    @RequestMapping("/cart/add/{itemId}")
    //@ResponseBody
    //为什么返回逻辑视图但是却返回了字符串，因为ResponseBody默认返回字符串的相应形式啦。
    public String addCart(@PathVariable Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response)
    {
        /*
         * 业务逻辑：
         *   1、调用SSO的服务，获取用户相关的信息。
         *   2、调用商品(manage)的服务，获取商品的相关的信息。
         *   3、判断如果是登录的状态，则调用登录的添加购物车的service。
         *   4、如果是未登录的状态，则调用的是未登录的添加购物车的方法。
         */
        System.out.println("购物车模块ID为; "+itemId);
        System.out.println("购物车模块NUM为; "+num);
        // 1、从cookie中获取token
        String tt_token = CookieUtils.getCookieValue(request, "TT_Token");
        System.out.println("tt_tokem为："+tt_token);
        // 2、根据token调用SSO的服务，获取用户的信息
        TaotaoResult userDataByToken = userRegister.getUserDataByToken("SsoForSession"+tt_token);
        System.out.println("userDataByToken为："+userDataByToken);
        System.out.println("JSON 为"+ JsonUtils.objectToJson(userDataByToken));
        TbItem itemMoreById = tbitemList.getItemMoreById(itemId);
        System.out.println("itemMoreById 为： "+itemMoreById);
        //3.  判断用户是否已经登录
        //3.1 用户处于登录状态
        if(userDataByToken != null && userDataByToken.getStatus()==200 && userDataByToken.getData() !=null)
        {
            // 3.2、调用添加购物车的方法，将商品数据添加到redis中
            TbUser tbUser = (TbUser) userDataByToken.getData();
            String string = JsonUtils.objectToJson(userDataByToken);
            System.out.println("tbUser为："+tbUser);
            System.out.println("string 为" +string);
            cartFotInter.addItemCart(tbUser.getId(), itemMoreById, num);
        }
        //3.2 用户处于没有登录的状态
        else
        {
            //3.2.1  从cookie中查询商品集合，看集合中是否有该商品
            List<TbItem> tbItemListByCookie = getTbItemListByCookie(request);
            //3.2.2  若有，设置数量为：原先数量+num
            boolean falg =false;//加个判断标志条件，不然if内部又要嵌入if-else
            for(TbItem rs : tbItemListByCookie)
            {
                if (rs.getId() == itemId.longValue())// 两个对象比的是内存地址值，longValue()取出的是基本类型的值
                {
                    rs.setNum(rs.getNum()+num);
                    falg =true;
                    break;
                }
            }
            if(!falg)
            {
                //3.2.3  若没有，转换成json存入集合，在存入cookie中，设置过期时间
                itemMoreById.setNum(num);
                if (itemMoreById.getImage() != null) {
                    itemMoreById.setImage(itemMoreById.getImage().split(",")[0]);
                }
                tbItemListByCookie.add(itemMoreById);
            }
            //设置Cookie及过期时间。
            System.out.println("购物车模块用户未登录下设置COOKIE_CART_KEY之前");
            CookieUtils.setCookie(request, response, COOKIE_CART_KEY, JsonUtils.objectToJson(tbItemListByCookie), 864000, true);
            System.out.println("购物车模块用户未登录下设置COOKIE_CART_KEY之后");
        }
        return "cartSuccess";
    }

    /**
     * 从Cookie中获取商品列表信息的方法
     */
    private List<TbItem> getTbItemListByCookie(HttpServletRequest request)
    {
        //COOKIE_CART_KEY
        String cookie = CookieUtils.getCookieValue(request, "COOKIE_CART_KEY", true);
        //为空则返回空字符串
        if(cookie == null)
            return new ArrayList<>();
        //不为空则返回数据，cookie_cart_key转换成list，cookie中存的是json格式的数据。
        List<TbItem> tbitemlist = JsonUtils.jsonToList(cookie, TbItem.class);
        return tbitemlist;
    }

    /**
     * 从购物车中查询商品列表，分为登录模式下从redis中查询跟未登录模式下从cookie下查询
     */
    @RequestMapping("/cart/cart")
    public String getTbitemListByRedisOrCookie(HttpServletRequest request , Model model)
    {
        String tt_token = CookieUtils.getCookieValue(request, "TT_Token");
        System.out.println("tt_tokem为购物车："+tt_token);
        // 2、根据token调用SSO的服务，获取用户的信息
        TaotaoResult userDataByToken = userRegister.getUserDataByToken("SsoForSession"+tt_token);
        System.out.println("userDataByToken为购物车："+userDataByToken);
        System.out.println("JSON 为购物车 ： "+ JsonUtils.objectToJson(userDataByToken));

        //1.先判断用户是否登录
        if(userDataByToken != null && userDataByToken.getStatus()==200 && userDataByToken.getData() !=null)
         //登录下从redis中取出
        {
            TbUser tbUser = (TbUser) userDataByToken.getData();
            System.out.println("userid is "+tbUser.getId()+" and userDataByToken is  "+userDataByToken);
            List<TbItem> itemListByRedis = cartFotInter.getItemListByRedisOrCookie(tbUser.getId());
            model.addAttribute("cartList" , itemListByRedis);

        }
        else //未登录下从cookie中取出
        {
            List<TbItem> tbItemListByCookie = getTbItemListByCookie(request);
            model.addAttribute("cartList" , tbItemListByCookie);
        }
        return "cart";
    }
    /**
     * 更新商品数量，价格随之变动
     */
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public TaotaoResult updItemNum(@PathVariable Long itemId,@PathVariable Integer num, HttpServletRequest request, HttpServletResponse response)
    {
        String tt_token = CookieUtils.getCookieValue(request, "TT_Token");
        System.out.println("tt_tokem为购物车更新数量："+tt_token);
        // 2、根据token调用SSO的服务，获取用户的信息
        TaotaoResult userDataByToken = userRegister.getUserDataByToken("SsoForSession"+tt_token);
        System.out.println("userDataByToken为购物车更新数量："+userDataByToken);
        System.out.println("JSON 为购物车 更新数量： "+ JsonUtils.objectToJson(userDataByToken));

        if (userDataByToken != null && userDataByToken.getStatus()==200 && userDataByToken.getData() !=null)
        {

            TbUser tbUser = (TbUser) userDataByToken.getData();
            System.out.println(tbUser+"12312312用户");
            cartFotInter.updItemNumByRedis(tbUser.getId() ,itemId ,num);
        }
        else
            {
            //1.从cookie中取出商品数量
            List<TbItem> tbItemListByCookie = getTbItemListByCookie(request);
            //2.设置新的商品数量
            for(TbItem rs : tbItemListByCookie)
            {
                if(rs.getId() == itemId.longValue())
                {
                    rs.setNum(num);
                    break;
                }
            }
            //3.写入cookie
            CookieUtils.setCookie(request , response ,COOKIE_CART_KEY ,JsonUtils.objectToJson(tbItemListByCookie));
        }
        return TaotaoResult.ok();
    }

    /**
     * 删除选择的商品或全选
     */
    @RequestMapping("/cart/delete/{itemId}")
    public String delItemByItemId(@PathVariable Long itemId , HttpServletRequest request , HttpServletResponse response)
    {
        String tt_token = CookieUtils.getCookieValue(request, "TT_Token");
        System.out.println("tt_tokem为购物车删除："+tt_token);
        // 2、根据token调用SSO的服务，获取用户的信息
        TaotaoResult userDataByToken = userRegister.getUserDataByToken("SsoForSession"+tt_token);
        System.out.println("userDataByToken为购物车删除："+userDataByToken);
        System.out.println("JSON 为购物车 删除： "+ JsonUtils.objectToJson(userDataByToken));
        if (userDataByToken != null && userDataByToken.getStatus()==200 && userDataByToken.getData() !=null)
        {
            TbUser user = (TbUser) userDataByToken.getData();
            cartFotInter.delItemByRedis(user.getId() ,itemId);
        }
        else
        {
            //1.从cookie中取出商品数量
            List<TbItem> tbItemListByCookie = getTbItemListByCookie(request);
            //List<TbItem> newTbItemCookie = new ArrayList<TbItem>();
            //2.设置新的商品数量
            for(TbItem rs : tbItemListByCookie)
            {
                if(rs.getId() == itemId.longValue())
                {
                    System.out.println(rs);
                    tbItemListByCookie.remove(rs);
                    break;//在循环中删除，在遍历会有问题，所以我们在遍历完后就应该跳出循环。
                    // newTbItemCookie.add(rs);
                }
            }
            //3.写入cookie
            CookieUtils.setCookie(request , response ,COOKIE_CART_KEY ,JsonUtils.objectToJson(tbItemListByCookie));
        }

        return "redirect:/cart/cart.html";
    }
}
