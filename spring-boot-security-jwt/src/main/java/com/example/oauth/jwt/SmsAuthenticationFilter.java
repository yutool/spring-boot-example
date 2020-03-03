package com.example.oauth.jwt;

import com.example.oauth.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 手机短信登录过滤器
 */
public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    // 是否只支持POST
    private boolean postOnly = true;

    public SmsAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/v1/login/mobile", "POST"));
        log.info("MobileLoginAuthenticationFilter loading ...");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        if (postOnly && !request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        // get request body
        String mobile = null;
        String smsCode = null;
        try {
            Map<String, String> loginForm = JsonUtils.toBean(request.getInputStream(), Map.class);
            log.info(loginForm.toString());
            mobile = loginForm.get("telephone");
            smsCode = loginForm.get("smsCode");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // assemble token
        SmsAuthenticationToken authRequest = new SmsAuthenticationToken(mobile, smsCode);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 设置身份认证的详情信息
     */
    private void setDetails(HttpServletRequest request, SmsAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}

