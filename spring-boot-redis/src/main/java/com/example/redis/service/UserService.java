package com.example.redis.service;


import com.example.redis.entity.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 测试redis注解式开发
 */

@Service
public class UserService {

    /**
     * @Cacheable
     * 用来声明方法是可缓存的。将结果存储到缓存中以便后续使用相同参数调用时不需执行实际的方法。
     * 直接从缓存中取值。最简单的格式需要制定缓存名称。
     * value: 缓存的名称，在 spring 配置文件中定义，必须指定至少一个
     * key: 缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合
     * condition: 缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存
     * @return 必须要有返回值，该返回值才是最后存到redis中的值。
     */
    @Cacheable(value = "user", key = "#id")
    public User get(String id) {
        User user = new User("1", "user"); // 模拟从数据库获取数据
        return user;
    }

    /**
     * @CachePut
     * 标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，
     * 而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
     * value: 缓存的名称，在 spring 配置文件中定义，必须指定至少一个
     * key: 缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合
     * condition: 缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存
     * @return 必须要有返回值，该返回值才是最后存到redis中的值。
     */
    @CachePut(value = "user", key = "#user.id")
    public User saveOrUpdate(User user) {
        // ... 操作数据库
        return user;
    }

    /**
     * @CacheEvict
     * 主要针对方法配置，能够根据一定的条件对缓存进行清空
     * value: 缓存的名称，在 spring 配置文件中定义，必须指定至少一个
     * key: 缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合
     * condition: 缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存
     * allEntries: 是否清空所有缓存内容，缺省为 false，如果指定为 true，则方法调用后将立即清空所有缓存
     * beforeInvocation: 是否在方法执行前就清空，缺省为 false，如果指定为 true，则在方法还没有执行的时候就清空缓存，缺省情况下，如果方法执行抛出异常，则不会清空缓存
     */
    @CacheEvict(value = "user", key = "#id")
    public void delete(String id) {
        // ... 操作数据库
    }
}
