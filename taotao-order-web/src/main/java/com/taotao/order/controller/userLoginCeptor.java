package com.taotao.order.controller;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.taotao.pojo.CookieUtils;
import com.taotao.pojo.TaotaoResult;
import com.taotao.sso.UserRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 订单系统用户登录拦截器。提交订单前确认用户是否已经登录。
 * 拦截器实现的接口HandlerInterceptor
 */
public class userLoginCeptor implements HandlerInterceptor {
    @Autowired
    private UserRegister userRegister;
    //进入目标方法（这里指的的controller的展示购物车列表的方法）之前执行，预处理动作
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.从cookie中取出token
        String tt_token = CookieUtils.getCookieValue(request, "TT_Token");
        if(tt_token != null)//token不为空则取用户信息
        {
            //2.调用sso服务获取用户信息
            TaotaoResult userDataByToken = userRegister.getUserDataByToken("SsoForSession" + tt_token);
            //3.若用户已经登录则放行，用户未登录则跳转到登录界面
            if(userDataByToken.getData() != null && userDataByToken.getStatus()==200)
            {
                return true;
            }
            else
            {
                //为了登录后能直接返回订单确认页面而是不会直接返回首页
                response.sendRedirect("http://120.78.192.106:8090/page/login?redirect="+request.getRequestURL().toString());
            }
        }
        else //token为空则重定向到登录页面
        {
            response.sendRedirect("http://120.78.192.106:8090/page/login?redirect="+request.getRequestURL().toString());
            System.out.println(request.getRequestURL().toString()+"**********..//");
        }
        return false;
    }
    //在进入目标方法之后，返回modelandView之前执行
    //常用于一些共用参数的设置，比如多个页面都需要的用户姓名等信息
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
    //在modelandview返回之后执行
    //常用于异常处理，日志写入，关闭连接等工作。
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
