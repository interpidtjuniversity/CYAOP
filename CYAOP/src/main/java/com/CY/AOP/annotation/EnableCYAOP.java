package com.CY.AOP.annotation;

import com.CY.AOP.core.CYAutoProxyCreator;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CYAutoProxyCreator.class)
public @interface EnableCYAOP {

    /**
     * 暴露代理对象到当前的线程变量中
     * @return
     */
    boolean exposeProxy() default false;
}
