package com.lish.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description:  java--class--run
 * author: lish
 * date: 2019/7/14
 * update:
 * version: 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface BindPath {
    String value();
}
