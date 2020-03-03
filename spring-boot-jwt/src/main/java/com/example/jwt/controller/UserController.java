package com.example.jwt.controller;

import com.example.jwt.entity.User;
import com.example.jwt.jwt.JwtUtils;
import com.example.jwt.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api")
@RestController
public class UserController {
    Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        // 简单起见，模拟一个数据
        if("zs".equals(user.getUsername()) && "123456".equals(user.getPassword())) {
            user.setUserId("xxx-xxx");
            Map<String, Object> tokens = JwtUtils.generateToken(user);
            return new Result(0, "登录成功", tokens);
        } else {
            return new Result(-1, "用户名或密码错误");
        }
    }

    @GetMapping("/refreshToken")
    public Result refreshToken(@RequestHeader(JwtUtils.TOKEN_HEADER) String token) {
        log.info(token);
        Map<String, Object> tokens = JwtUtils.refreshToken(token.substring(JwtUtils.TOKEN_PREFIX.length()));
        return new Result(0, "刷新token成功", tokens);
    }

    @GetMapping("/other")
    public Result other() {
        log.info("other请求成功");
        return new Result(0, "other请求成功");
    }
}
