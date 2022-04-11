package com.uin.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wanglufei
 * @description: TODO
 * @date 2022/4/10/5:44 PM
 */
@Controller
public class LoginController {

    /**
     * 登陆
     *
     * @return java.lang.String
     * @author wanglufei
     * @date 2022/4/10 7:54 PM
     */
    @RequestMapping("login")
    public String login() {
        System.out.println("执行了login");
        return "redirect:main.html";
    }

    /**
     * 登陆成功跳转页面
     *
     * @return java.lang.String
     * @author wanglufei
     * @date 2022/4/11 11:03 AM
     */
    @RequestMapping("/toMain")
    public String toMain() {
        return "redirect:main";
    }

    /**
     * 登陆成功跳转页面 基于注解配置
     *
     * @return java.lang.String
     * @Secured("ROLE_uin") 判断是否是uin这个角色
     * @author wanglufei
     * @date 2022/4/11 2:20 PM
     */
    @Secured("ROLE_uin")
    @RequestMapping("/toMain")
    public String toMain2() {
        return "redirect:main";
    }

    /**
     * @return java.lang.String
     * @PreAuthorize("hasAnyRole('uin')") 可以不用ROLE_开头
     * @author wanglufei
     * @date 2022/4/11 2:29 PM
     */
    @PreAuthorize("hasAnyRole('uin')")
    @RequestMapping("/toMain")
    public String toMain3() {
        return "redirect:main";
    }

    /**
     * 登陆失败跳转页面
     *
     * @return java.lang.String
     * @author wanglufei
     * @date 2022/4/11 11:11 AM
     */
    @RequestMapping("/toError")
    public String toError() {
        return "redirect:error.html";
    }

    /**
     * 跳转页面
     *
     * @return java.lang.String
     * @author wanglufei
     * @date 2022/4/11 3:43 PM
     */
    @RequestMapping("demo")
    public String demo() {
        return "demo";
    }
}
