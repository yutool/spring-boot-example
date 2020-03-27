package com.example.quartz.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class QuartzDataSourceConfig {
    //Error:EmbeddedDatabaseType class not found，Druid数据源初始化需要引入spring-jdbc依赖，JPA或mybatis依赖已经包含该依赖
    @Bean
    @QuartzDataSource
    @ConfigurationProperties(prefix = "spring.quartz.properties.org.quartz.datasource")
    DataSource quartzDataSource(){
        return DruidDataSourceBuilder.create().build();
    }
}
