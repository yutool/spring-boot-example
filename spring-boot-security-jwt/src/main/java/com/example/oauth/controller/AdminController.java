package com.example.oauth.controller;

import com.example.oauth.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/admins")
@RestController
public class AdminController {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{id}")
    public User test1(@PathVariable String id) {
        log.info(id);
        return new User(id, "a", "a", "ADMIN");
    }
}
