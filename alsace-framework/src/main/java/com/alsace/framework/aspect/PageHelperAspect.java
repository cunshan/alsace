package com.alsace.framework.aspect;

import com.alsace.framework.annotation.PageQuery;
import com.alsace.framework.common.basic.BasePageParam;
import com.github.pagehelper.PageHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PageHelperAspect {

  @Pointcut("@annotation(com.alsace.framework.annotation.PageQuery)")
  public void queryPage() {
  }


  @Before("queryPage()")
  public void doBefore(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    PageQuery pageQuery = signature.getMethod().getAnnotation(PageQuery.class);
    int index = pageQuery.value();
    Object arg = null;
    if (index >= 0) {
      //如果注解里指定了含有分页信息的参数下标  直接去该参数
      arg = joinPoint.getArgs()[index];
    } else {
      //如果没指定含有分页信息的参数下标，遍历参数取第一个符合条件的
      for (Object joinPointArg : joinPoint.getArgs()) {
        if (joinPointArg instanceof BasePageParam) {
          arg = joinPointArg;
          break;
        }
      }
    }
    if (arg instanceof BasePageParam) {
      BasePageParam param = (BasePageParam) arg;
      Integer pageNum = param.getPageNum();
      Integer pageSize = param.getPageSize();
      if (pageNum != null && pageSize != null) {
        PageHelper.startPage(pageNum, pageSize, pageQuery.countSql());
      }

    }

  }

}
