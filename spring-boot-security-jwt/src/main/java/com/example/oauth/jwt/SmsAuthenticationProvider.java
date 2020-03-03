package com.example.oauth.jwt;

import com.example.oauth.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 手机短信登录认证提供者
 */
public class SmsAuthenticationProvider implements AuthenticationProvider {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private UserDetailsServiceImpl userDetailsService;

    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

//    private StringRedisTemplate stringRedisTemplate;
//    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
//        this.stringRedisTemplate = stringRedisTemplate;
//    }

    public SmsAuthenticationProvider() {
        log.info("MobileLoginAuthenticationProvider loading ...");
    }

    /**
     * 认证
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取过滤器封装的token信息
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;

        // 验证验证码
        String mobile = (String) authenticationToken.getPrincipal();
        String smsCode = (String) authenticationToken.getCredentials();
        // String verifyCode = stringRedisTemplate.opsForValue().get(mobile);
        String verifyCode = "123456";  // 模拟从reids中取值
        if(verifyCode == null) {
            throw new BadCredentialsException("未检测到申请验证码");
        }
        if(!smsCode.equals(verifyCode)) {
            throw new BadCredentialsException("验证码错误");
        }
        // 获取用户信息（数据库认证）
        UserDetails userDetails = userDetailsService.loadUserByTelPhone(mobile);
        log.info(userDetails.toString());

        // 不通过
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("Unable to obtain user information");
        }
        //通过
        SmsAuthenticationToken authenticationResult = new SmsAuthenticationToken(userDetails, userDetails.getAuthorities());

        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    /**
     * 根据token类型，来判断使用哪个Provider
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
