package com.jhon.rain.common.access;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>功能描述</br>接入限制的注解</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/16 10:24
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface AccessLimit {

  int seconds();

  int maxCount();

  boolean needLogin() default true;
}
