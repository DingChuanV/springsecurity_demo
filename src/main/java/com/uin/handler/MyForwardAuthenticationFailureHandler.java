package com.uin.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wanglufei
 * @description: 自定义失败登陆跳转处理器
 * 通过判断断言，源码中还会抛出一个异常，这个异常是继承我们的运行时的异常
 * @date 2022/4/11/12:20 PM
 */
public class MyForwardAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private String forward_url;

    public MyForwardAuthenticationFailureHandler(String forward_url) {
        //源码这里还会判断
        //Assert.isTrue(UrlUtils.isValidRedirectUrl(forwardUrl), () -> "'" + forwardUrl + "' is not a valid forward URL");
        this.forward_url = forward_url;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //这里源码还会抛出异常
        //request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
        //源码是通过转发实现的
        //request.getRequestDispatcher(this.forwardUrl).forward(request, response);
        request.getRequestDispatcher(forward_url).forward(request, response);
    }
}
