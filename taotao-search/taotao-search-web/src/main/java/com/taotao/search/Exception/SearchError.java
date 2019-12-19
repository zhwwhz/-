package com.taotao.search.Exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理器的类
 */
public class SearchError implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        /**
         * 常规做法：
         * 1.将错误信息写入日志文件（这里打印进行打印处理）
         * 2.给相关运维人员发送消息（调用第三方短信接口）
         * 3.页面上提示友好信息
         */
        System.out.println(ex.getMessage());
        System.out.println("发送短信");
        ModelAndView model = new ModelAndView();
        model.setViewName("WEB-INF/jsp/error/exception.jsp");
        model.addObject("message","系统错误，请稍后在试。");

        return model;
    }
}
