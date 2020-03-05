package com.lagou.edu.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAutowired {
    /**
     * 数值
     * @return
     */
    int num() default 1;
}