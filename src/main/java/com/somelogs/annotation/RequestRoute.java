package com.somelogs.annotation;

import java.lang.annotation.*;

/**
 * request route
 *
 * provide request url by the annotation
 *
 * @author LBG - 2018/1/5 0005
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestRoute {

    String url() default "";
}
