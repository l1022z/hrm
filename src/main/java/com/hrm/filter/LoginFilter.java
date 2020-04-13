package com.hrm.filter;

import com.hrm.commons.beans.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * company: www.abc.com
 * Author: Administrator
 * Create Data: 2020/2/23
 */
public class LoginFilter implements Filter {
    /*不进行过滤的页面、处理器方法或其他静态资源*/
    String[] IG_URL = {"/index.jsp","loginForm.jsp","/login","/",".css",".js",".jpg"};
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       /*强制类型转换*/
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        /*获取本次请求的uri*/
        String requestURI = request.getRequestURI();
        /*循环判断本次请求的URI是否为数组中定义的字符串*/
        for (String s:IG_URL){
            /*如果本次请求是不进行过滤的请求*/
            if (requestURI.endsWith(s)){
                /*直接放行*/
                filterChain.doFilter(request,response);
                return;
            }
        }
        /*获取用户登录的信息*/
        User login_user = (User) request.getSession().getAttribute("login_user");
        /*用户已经登录*/
        if (login_user!=null){
            /*放行*/
            filterChain.doFilter(request,response);
        }else {
            /*用户未登录*/
            request.setAttribute("login_error","您还未登录，请登录后访问");
            request.getRequestDispatcher("index.jsp").forward(request,response);
        }
    }
}
