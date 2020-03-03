package com.example.pagehelper.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class PageHelperConfig {
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        // 默认值为 false，该参数对使用 RowBounds 作为分页参数时有效。
        // 当该参数设置为 true 时，会将 RowBounds 中的 offset 参数当成 pageNum 使用，可以用页码和页面大小两个参数进行分页。
        p.setProperty("offsetAsPageNum", "true");
        // 默认值为false，该参数对使用 RowBounds 作为分页参数时有效。
        // 当该参数设置为true时，使用 RowBounds 分页会进行 count 查询。
        // 使用更强大的 PageInfo 类，需要设置该参数为 true
        p.setProperty("rowBoundsWithCount", "true");
        // 默认值为 false，当该参数设置为 true 时，如果 pageSize=0 或者 RowBounds.limit = 0 就会查询出全部的结果。
        p.setProperty("pageSizeZero", "true");
        // 默认值为false。当该参数设置为 true 时，pageNum<=0 时会查询第一页，
        // pageNum>pages（超过总数时），会查询最后一页。默认false 时，直接根据参数进行查询。
        p.setProperty("reasonable", "true");
        // supportMethodsArguments：支持通过 Mapper 接口参数来传递分页参数，默认值false，
        // 分页插件会从查询方法的参数值中，自动根据上面 params 配置的字段中取值，查找到合适的值时就会自动分页。
        // autoRuntimeDialect：默认值为 false。设置为 true 时，允许在运行时根据多数据源自动识别对应方言的分页

        pageHelper.setProperties(p);
        return pageHelper;
    }
}
