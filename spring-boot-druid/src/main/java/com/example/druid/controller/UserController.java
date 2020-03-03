package com.example.druid.controller;

import com.example.druid.dao.UserMapper;
import com.example.druid.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserController {
    @Resource
    UserMapper userMapper;

    @GetMapping("users")
    public List<User> getAllUser() {
        return userMapper.selectAll();
    }
}
