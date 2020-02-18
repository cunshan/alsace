package com.alsace.framework.aspect;

import com.alsace.framework.common.basic.BaseEntity;
import com.alsace.framework.common.idworker.IdWorker;
import com.alsace.framework.common.shiro.ShiroPrincipal;
import com.alsace.framework.config.properties.ShiroProperties;
import com.alsace.framework.utils.JwtUtils;
import java.util.Arrays;
import java.util.Date;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

  @Resource
  private ShiroProperties shiroProperties;

  @Pointcut("@annotation(com.alsace.framework.annotation.AutoFill)")
  public void autoFill() {

  }

  @Around("autoFill()")
  public Object doProceed(ProceedingJoinPoint joinPoint) throws Throwable {
    Object[] args = joinPoint.getArgs();
    Arrays.stream(args).forEach(arg -> {
      if (arg instanceof BaseEntity) {
        BaseEntity entity = (BaseEntity) arg;
        entity.setId(IdWorker.getId());
        entity.setCreatedTime(new Date());
        entity.setModifiedTime(new Date());
        if (shiroProperties.isJwt()) {
          String loginAccount = JwtUtils
              .getLoginAccount((String) SecurityUtils.getSubject().getPrincipal());
          entity.setCreatedBy(loginAccount);
          entity.setModifiedBy(loginAccount);
        } else {
          ShiroPrincipal principal = (ShiroPrincipal) SecurityUtils.getSubject().getPrincipal();
          entity.setCreatedBy(principal.getLoginAccount());
          entity.setModifiedBy(principal.getLoginAccount());
        }
      }
    });
    return joinPoint.proceed();
  }

}
