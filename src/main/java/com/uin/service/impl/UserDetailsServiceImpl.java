package com.uin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author wanglufei
 * @description: 实现SpringSecurity中的UserDetailsService
 * @date 2022/4/11/8:43 AM
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 自定义username
     *
     * @param username
     * @return org.springframework.security.core.userdetails.UserDetails
     * @author wanglufei
     * @date 2022/4/11 8:44 AM
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.根据username去查询数据库，不存在的就会抛出异常UsernameNotFoundException
        if (!"admin".equals(username)) {
            return (UserDetails) new UsernameNotFoundException("用户名不存在");
        }
        //2.把查询的密码（注册是已经加过密）进行解析，或者直接把密码放入构造方法
        String password = passwordEncoder.encode("123");
        return new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList(
                "admin,normal,ROLE_uin"));
    }
}
