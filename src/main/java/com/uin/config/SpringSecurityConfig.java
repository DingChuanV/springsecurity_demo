package com.uin.config;

import com.uin.handler.MyAuthenticationSuccessHandler;
import com.uin.handler.MyForwardAuthenticationFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author wanglufei
 * @description: SpringSecurity配置类
 * @date 2022/4/10/7:51 PM
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 自定义登陆之后的逻辑
     * 1.自定义登陆页面
     *
     * @param http
     * @author wanglufei
     * @date 2022/4/11 9:44 AM
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //表单提交
        http.formLogin()
                .usernameParameter("name")
                //必须和表单的提交是一样的
                .loginProcessingUrl("/login")
                //自定义登陆页面
                .loginPage("/login.html")
                //登陆成功之后跳转的页面 必须是Post强求
                //还有一点值得注意的，我们现在开发项目大多数都是前后端分离，这种跳转是行不通的。
                //.successForwardUrl("/toMain").
                //实现自定义认证之后的跳转路径  successForwardUrl不能共存
                .successHandler(new MyAuthenticationSuccessHandler("http://www.baidu.com"))
                //登陆失败跳转的页面
                //.failureForwardUrl("/toError")
                .failureHandler(new MyForwardAuthenticationFailureHandler("http://www.cnblogs.com/bearbrick0/p/16129311.html"));

        //authorizeRequests 授权请求
        //authenticated 认证
        //意思就是所有的请求的都需要认证，也有点像我们之间做的不登陆的话，去访问其他页面，都会被转发到登陆页面
        http.authorizeRequests()
                //给登陆错误页面放行，不需要认证
                .antMatchers("/error.html").permitAll()
                //意思就是给login.html放行，不需要认证
                .antMatchers("/login.html").permitAll()
                .anyRequest().authenticated();
        //关闭防火墙
        http.csrf().disable();
    }

    /**
     * 在Spring容器中实现BCryptPasswordEncoder实例
     * 密码加密
     *
     * @return org.springframework.security.crypto.password.PasswordEncoder
     * @author wanglufei
     * @date 2022/4/11 8:32 AM
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
