package com.uin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
