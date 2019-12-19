package com.taotao.sso.controller;

import com.taotao.pojo.*;
import com.taotao.sso.UserRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@Controller
public class UserRegisterController {
    @Autowired
    private UserRegister userRegister;
    @Value("${TAOTAO.A}")
    private String TT_Token;
    /**
     * 用户注册校验数据
     */
    @RequestMapping(value="/user/check/{param}/{type}", method= RequestMethod.GET)
    @ResponseBody
    public TaotaoResult UserCheckData(@PathVariable String param ,@PathVariable Integer type)
    {
//        try {
//            param=new String(param.getBytes("iso8859-1"),"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        System.out.println("param is "+param +"type is "+type);
        return userRegister.UserCheckData(param, type);
    }
    /**
     * 用户注册
     */
    @RequestMapping(value="/user/register", method=RequestMethod.POST)
    @ResponseBody
    public TaotaoResult UserGister(TbUser user ,HttpServletRequest request ,HttpServletResponse response)
    {
        return userRegister.UserRegistser(user);
    }

    /**
     * 用户登录
     * @param request
     * @param response
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value="/user/login", method=RequestMethod.POST)
    @ResponseBody
    public TaotaoResult UserLogin(HttpServletRequest request, HttpServletResponse response, String username , String password)
    {
        //需要把token设置到cookie里面，这个涉及到cookie跨域的问题。
            TaotaoResult result = userRegister.UserLogin(username, password);
            System.out.println("Cookies登录时候设置的是 ："+result);
            System.out.println("Cookies登录时候json设置的是 ："+JsonUtils.objectToJson(result));
            if(result.getStatus() == 200)
            {
                System.out.println("==200Cookies登录时候json设置的是 ："+result.getData().toString());
                String token = result.getData().toString();
                System.out.println("==200Cookies登录时候token设置的是 ：" + token);
                CookieUtils.setCookie(request,response,TT_Token,token);
                String cookieValue = CookieUtils.getCookieValue(request , TT_Token);
                System.out.println("检查下Cookie是否设置成功了~~~~~~  "+cookieValue+" ~~~...");
            }
        return result;
    }

    /**
     * 用户通过Token从redis获取到用户信息数据
     */
    @RequestMapping(value="/user/token/{token}", method=RequestMethod.GET , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getUserDataByToken(@PathVariable String token , String callback)
    {
        //1.处理json跨域问题。使用jsonp方法。
        //判断是否有callback的返回。如有的话需要拼接成类似fun（{ id ：1}）的格式：
        try {
            if(callback != null)
            {
                System.out.println("Controller的token是："+token);
                TaotaoResult userDataByToken = userRegister.getUserDataByToken(token);
                String json =  JsonUtils.objectToJson(userDataByToken);
                System.out.println(json+"--111");
                //拼装成函数包裹的形式，并把这个对象转换成json对象。
                String jsonp = callback + "("+ json +")";
                System.out.println(jsonp+"--222");
                return jsonp;

            }
        } catch (Exception e) {
            System.out.println("回显用户名的时候返回了空值");
            e.printStackTrace();
        }
        //如果不是，不做任何处理。
        System.out.println(userRegister.getUserDataByToken(token).getData());
        System.out.println("---333333");
        return userRegister.getUserDataByToken(token);
    }
    /**
     * 用户退出
     */
    @RequestMapping(value="/user/logout/{token}")
    //@ResponseBody
    public String UserLogout(@PathVariable String token , HttpServletResponse response)throws Exception
    {
        System.out.println("Controller退出前 + "+token);
        TaotaoResult result = userRegister.UserLogout(token);
        System.out.println("Controller退出后 + "+token);
        //response.sendRedirect("localhost:8081");
        return "redirect:http://39.108.185.153:8081/";
    }


}
