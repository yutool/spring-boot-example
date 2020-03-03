package com.example.redis;

import com.example.redis.cache.RedisManager;
import com.example.redis.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RedisApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisManager redisManager;

    @Test
    void contextLoads() {
    }

    @Test
    public void testStringTemplate()
    {
        stringRedisTemplate.opsForValue().set("name", "Susan King");
        stringRedisTemplate.opsForValue().set("age", "29");
        assertEquals("Susan King", stringRedisTemplate.opsForValue().get("name"));
        assertEquals("29", stringRedisTemplate.opsForValue().get("age"));
    }

    @Test
    public void redisTemplate()
    {
        // 使用redisManager，就不这样用了
        String key = "user";
        redisTemplate.opsForValue().set(key, new User("1", "user1"));
        // 对应 String（字符串）
        User user = (User) redisTemplate.opsForValue().get(key);
        assertEquals("1", user.getName());
    }

    @Test
    public void redisManager()
    {
        String key = "user";
        redisManager.set("user", new User("2", "123"));
        User user = redisManager.get("user", User.class);
        assertEquals("2", user.getName());
    }

}
