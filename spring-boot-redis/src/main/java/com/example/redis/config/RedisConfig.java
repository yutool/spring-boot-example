package com.example.redis.config;

import com.example.redis.cache.RedisManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * spring boot 2.x默认使用Lettuce连接redis
 * 如果要使用Jedis需要排除Lettuce包引入Jedis包
 */
@Configuration
@EnableCaching //开启注解式缓存
public class RedisConfig {
    /**
     * RedisTemplate与 StringRedisTemplate的区别
     *  详解：
     *      1，两者是独立的 各有自己的工作空间，也就是它俩都是使用的redis数据库，但是彼此互不干涉彼此的数据信息
     *      2，两者实现各不相同的序列化接口，RedisTemplate使用的是JdkSerializationRedisSerializer
     *         存入数据会将数据先序列化成字节数组然后在存入Redis数据库。
     * 　　     StringRedisTemplate使用的是StringRedisSerializer
     *      （以map作栗子）
     *      3，RedisTemplate数据返回的是<String,Object> 当你需要获取该数据时，使用redisTemplate则是不需要做类型转换的
     *         StringRedisTemplate数据返回的是<String, String> 类型，所以你在存取数据时，
     *         如果是字符串类型的数据，则可以使用stringRedisTemplate
     *
     * 默认情况下的模板只能支持RedisTemplate<String, String>，也就是只能存入字符串，因此支持序列化
     */
    @Bean
    public RedisTemplate redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    public RedisManager redisManager(RedisTemplate redisTemplate) {
        RedisManager redisManager = new RedisManager();
        redisManager.setRedisTemplate(redisTemplate);
        return redisManager;
    }

    /**
     * 需要的话 extends CachingConfigurerSupport
     * 实现自定义key生成策略
     */
//    @Override
//    @Bean
//    public KeyGenerator keyGenerator() {
//        return (target, method, params) -> {
//            StringBuilder sb = new StringBuilder();
//            sb.append(target.getClass().getName());
//            sb.append(".");
//            sb.append(method.getName());
//            for (Object obj : params) {
//                sb.append(".");
//                sb.append(obj.toString());
//            }
//            return sb.toString();
//        }
//    }

    /**
     * 配置SpringBoot的缓存为RedisCacheManage。
     * 这个配置还没测试过
     * 配置使用注解的时候缓存配置，默认是序列化反序列化的形式，加上此配置则为 json 形式
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        // 配置序列化
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        RedisCacheConfiguration redisCacheConfiguration = config
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        return RedisCacheManager.builder(factory).cacheDefaults(redisCacheConfiguration).build();
    }
}
