package com.CY.AOP.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CYAfter {

    /**
     * 描述切点的值
     * @return
     */
    String value();

}
