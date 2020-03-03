package com.example.swagger.controller;

import com.example.swagger.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(tags = "用户相关接口", value = "提供用户相关的 Rest API")
public class UserController {
    @ApiOperation(value = "新增用户", notes = "新增用户")
    @PostMapping("/add")
    public boolean addUser(@RequestBody User user) {
        return false;
    }

    @GetMapping("/find/{id}")
    public User findById(@PathVariable("id") int id) {
        return new User();
    }

    @PutMapping("/update")
    public boolean update(@RequestBody User user) {
        return true;
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") int id) {
        return true;
    }
}