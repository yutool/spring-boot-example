package com.example.jwt.jwt;

import com.example.jwt.model.Result;
import com.example.jwt.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 过滤掉不需要验证的url
        final String path = request.getServletPath();
        if (path.equals("/api/login") || path.equals("/api/register")) {
            chain.doFilter(request, response);
            return ;
        }
        // 验证token
        String authToken = request.getHeader(JwtUtils.TOKEN_HEADER);
        if (authToken != null && authToken.startsWith(JwtUtils.TOKEN_PREFIX)) {
            final String token = authToken.substring(JwtUtils.TOKEN_PREFIX.length());
            log.info(request.getRequestURI() + "携带的token值：" + token);
            if(JwtUtils.validateToken(token)) {
                // 认证成功放行
                chain.doFilter(request, response);
            } else {
                // 设置一个状态码，告诉前端，token失效，让其刷新token
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Result result = new Result(3000, "token过期");
                JsonUtils.responseWrite(response, result);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            Result result = new Result(2000, "无效token");
            JsonUtils.responseWrite(response, result);
        }
    }
}
