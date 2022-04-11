package com.uin.config;

import com.uin.handler.MyAccessDeniedHandler;
import com.uin.handler.MyAuthenticationSuccessHandler;
import com.uin.handler.MyForwardAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
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

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
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
                //1. 自定义登陆参数
                .usernameParameter("name")
                .passwordParameter("pwd")

                //2. 必须和表单的提交是一样的
                .loginProcessingUrl("/login")
                //自定义登陆页面
                .loginPage("/login.html")

                //登陆成功之后跳转的页面 必须是Post强求
                //还有一点值得注意的，我们现在开发项目大多数都是前后端分离，这种跳转是行不通的。

                //.successForwardUrl("/toMain").
                //3. 实现自定义认证之后的跳转路径  successForwardUrl不能共存
                //自定义登陆成功跳转页面
                .successHandler(new MyAuthenticationSuccessHandler("http://www.baidu.com"))

                //登陆失败跳转的页面
                //.failureForwardUrl("/toError")
                //4. 自定义登陆失败跳转页面
                .failureHandler(new MyForwardAuthenticationFailureHandler("http://www.cnblogs.com/bearbrick0/p/16129311.html"));

        //authorizeRequests 授权请求
        //authenticated 认证
        //意思就是所有的请求的都需要认证，也有点像我们之间做的不登陆的话，去访问其他页面，都会被转发到登陆页面
        http.authorizeRequests()

                //1. 给登陆错误页面放行，不需要认证
                .antMatchers("/error.html").permitAll()
                //意思就是给login.html放行，不需要认证
                .antMatchers("/login.html").permitAll()

                //自定义请求的方式
                //.regexMatchers(HttpMethod.GET,"/hello").permitAll()

                //自定义servletPath
                //.mvcMatchers("/hello").servletPath("/uin").permitAll()
                //.antMatchers("/uin/hello").permitAll()


                //2. 自定义角色控制访问权限
                //意思就是有admin的角色才可以访问 admin严格区分大小写
                //.antMatchers("/main2.html").hasAuthority("admin")
                //.antMatchers("/main1.html").hasAnyAuthority("admin","admin1")

                //3. 自定义角色的判断控制
                //在service层 return new User(username, password, AuthorityUtils
                // .commaSeparatedStringToAuthorityList(
                //                "admin,normal,ROLE_uin"));
                //.antMatchers("/main1.html").hasRole("uin")
                //.antMatchers("/main.html").hasAnyRole("uin","bearbrick")

                //4. 自定义IP地址控制
                // System.out.println(request.getRemoteAddr());
                // localhost  0.0.0.0.0.0.0.1
                // 127.0.0.1  127.0.0.1
                //.antMatchers("/main.html").hasIpAddress("127.0.0.1")


                //5. 基于表达式的访问控制
                //access()
                //上面的都是
                //.antMatchers("/main.html").access("permitAll()")

                //6. 基于注解的访问控制
                //默认的不可用的，需要使用@EnableGlobalMethodSecurity(securedEnabled = true) 在程序的启动开启上开启
                //注解可以写在service层的接口和方法和controller层或者controller的方法上
                //通常的情况都是写在控制器（controller）的方法上，控制接口和Url是否被允许
                //常见的注解访问控制
                //1. @Secured  专门判断是否具有角色。能够写在方法上或者类上。参数要ROLE_开头
                //2. @PreAuthorize()和@PostAuthorize()都是方法或类级别的注解
                //   @PreAuthorize()表示访问方法或类在执行之前判断权限，大多情况下都是使用这个注解，注解的参数和access()
                //一样。都是权限表达是
                //   @PostAuthorize()表示在方法或类执行之后进行权限判断，此注解很少用。
                // 当然使用它，也是需要去开启的，方法还是一样
                // @EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)

                //任何的请求都要认证
                .anyRequest().authenticated();


        //关闭防火墙
        http.csrf().disable();


        //自定义403权限异常
        http.exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler);
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
