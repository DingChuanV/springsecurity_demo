package com.uin.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wanglufei
 * @description: 自定义成功认证重定向处理器
 * 解决： 前后端分离项目的跳转
 * @date 2022/4/11/12:10 PM
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private String url;

    public MyAuthenticationSuccessHandler(String url) {
        this.url = url;
    }

    /**
     * 重写认证成功之后重定向url
     *
     * @param request
     * @param response
     * @param authentication
     * @author wanglufei
     * @date 2022/4/11 12:13 PM
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //获取认证用户的信息
        User user = (User) authentication.getPrincipal();
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());//null
        System.out.println(user.getAuthorities());
        response.sendRedirect(url);
    }
}
