package com.zhaoj.config;

import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    // 创建分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    // 创建oracle数据库主键使用序列生成
    @Bean
    public OracleKeyGenerator oracleKeyGenerator() {
        return new OracleKeyGenerator();
    }
}
