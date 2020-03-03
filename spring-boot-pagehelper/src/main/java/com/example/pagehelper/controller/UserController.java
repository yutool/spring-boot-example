package com.example.pagehelper.controller;

import com.example.pagehelper.dao.UserMapper;
import com.example.pagehelper.entity.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserController {
    @Resource
    UserMapper userMapper;

    @GetMapping("/users")
    public List<User> getUserList(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.select();
        // 可以将userList强转成Page类型
        System.out.println((Page)userList);
        // 可以通过UserList获取UserInfo
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        System.out.println(pageInfo);
        return userList;
    }

}
