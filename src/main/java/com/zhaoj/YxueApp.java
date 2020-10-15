package com.zhaoj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 作者:zhaoj
 * 创建时间:2020/9/23    11:09
 * 类的作用:
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.zhaoj.dao","com.zhaoj.log.dao"})
//扫描servlet源生注解
@ServletComponentScan
public class YxueApp {
    public static void main(String[] args) {
        SpringApplication.run(YxueApp.class,args);
    }
}
