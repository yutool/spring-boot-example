package com.example.oauth.service;

import com.example.oauth.entity.User;
import com.example.oauth.jwt.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String account) throws  UsernameNotFoundException {
        // 从数据库中获取用户，如果需要的话，再获取权限
        // User user = accountMapper.loadUserByAccount(account);
        // 因为使用模拟数据不调用这里，为了语法通过先这样写了
        User user = new User();
        return new JwtUserDetails(user);
    }

    // 给sms登录用的
    public UserDetails loadUserByTelPhone(String telephone) {
        // User user = accountMapper.findUserByTelPhone(telephone);
        User user = new User();
        // if(user == null) return null;
        return new JwtUserDetails(user);
    }
}

