package com.example.oauth.jwt;

import com.example.oauth.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 登录成功处理的业务
 */
@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        // 获得授权后可得到用户信息(非jwt 方式)
        // User userDetails =  (User) authentication.getPrincipal();

        // 查看源代码会发现调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
        // 使用模拟数据返回 security User, 而不是JwtUserDetails对象
        User securityUser = (User) authentication.getPrincipal();
        String role = null;
        for(GrantedAuthority r : securityUser.getAuthorities()) {
            role = r.toString();
        }
        String token = JwtTokenUtils.generateToken(UUID.randomUUID().toString(), securityUser.getUsername(), role);
        Map<String, Object> result = new HashMap<>();
        result.put("user", securityUser);
        result.put("token", token);
        log.info("JwtAuthenticationSuccessHandler");
        response.getWriter().write(JsonUtils.toString(result));
    }
}