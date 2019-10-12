package com.alsace.framework.common.annotation;

import com.alsace.framework.common.enums.LogModifyType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 被注解的方法自动分页查询
 * 默认从第一个是
 * com.alsace.framework.common.domain.BasePageParam
 * 类的参数中取pageSize 和 pageNum
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogModify {

  String desc() default "";

  LogModifyType modifyType();

  boolean saveParams() default true;

}
