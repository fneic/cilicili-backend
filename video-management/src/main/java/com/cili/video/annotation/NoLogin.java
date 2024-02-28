package com.cili.video.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 登录放行注解，可加在controller类和方法上，表示登录放行某些api
 * @author Zhou JunJie
 * @date 2023/11/9
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoLogin {

}
