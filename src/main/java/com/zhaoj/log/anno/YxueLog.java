package com.zhaoj.log.anno;

import org.springframework.data.elasticsearch.annotations.Document;

import java.lang.annotation.*;

/**
 * 自定义注解:
 *  1.元注解：描述注解的注解.java源生提供的
 * @Target 指定当前注解使用在哪个位置
 *              ElementType.METHOD   用在方法上
 *              ElementType.TYPE     用在类上
 *              ElementType.FIELD    用在成员变量上
 * @Retention   用于指定注解的有效期
 *              RetentionPolicy.RUNTIME 代表程序运行时有效
 *
 * @Documented  指定注解是否出现在javadoc文档中中
 */
@Documented
@Target(value = {ElementType.METHOD,ElementType.TYPE,ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface  YxueLog {

    String value() default "";
    String name() default "";
    String tableName() default "";
    String methodName() default "";

}
