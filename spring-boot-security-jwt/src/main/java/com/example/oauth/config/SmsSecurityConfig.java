package com.example.oauth.config;

import com.example.oauth.jwt.SmsAuthenticationFilter;
import com.example.oauth.jwt.SmsAuthenticationProvider;
import com.example.oauth.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SmsSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler jwtAuthenticationFailureHandler;

//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;

    public SmsSecurityConfig() {
        log.info("MobileLoginAuthenticationSecurityConfig loading ...");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {


        SmsAuthenticationFilter smsAuthenticationFilter = new SmsAuthenticationFilter();

        smsAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        smsAuthenticationFilter.setAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler);
        smsAuthenticationFilter.setAuthenticationFailureHandler(jwtAuthenticationFailureHandler);

        SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider();
        smsAuthenticationProvider.setUserDetailsService(userDetailsService);
        // smsAuthenticationProvider.setStringRedisTemplate(stringRedisTemplate);

        http.authenticationProvider(smsAuthenticationProvider)
                .addFilterAfter(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

