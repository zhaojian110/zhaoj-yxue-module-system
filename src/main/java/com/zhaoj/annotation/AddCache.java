package com.zhaoj.annotation;

import java.lang.annotation.*;

@Documented
@Target(value = {ElementType.METHOD,ElementType.TYPE,ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface AddCache {
}
