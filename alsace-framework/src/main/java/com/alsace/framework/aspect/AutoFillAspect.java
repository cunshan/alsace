package com.alsace.framework.aspect;

import com.alsace.framework.annotation.AutoFill;
import com.alsace.framework.common.basic.BaseEntity;
import com.alsace.framework.common.enums.ActiveFlag;
import com.alsace.framework.common.enums.AutoFillType;
import com.alsace.framework.common.idworker.IdWorker;
import com.alsace.framework.common.shiro.ShiroPrincipal;
import com.alsace.framework.config.properties.ShiroProperties;
import com.alsace.framework.utils.JwtUtils;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Object[] args = joinPoint.getArgs();
    AutoFill autoFill = signature.getMethod().getDeclaredAnnotation(AutoFill.class);
    String loginAccount;
    //获取登录账号
    if (shiroProperties.isJwt()) {
      loginAccount = JwtUtils
          .getLoginAccount((String) SecurityUtils.getSubject().getPrincipal());

    } else {
      ShiroPrincipal principal = (ShiroPrincipal) SecurityUtils.getSubject().getPrincipal();
      loginAccount = principal.getLoginAccount();
    }
    Arrays.stream(args).forEach(arg -> {
      if (arg instanceof BaseEntity) {
        //单个参数
        fillEntity(arg, autoFill, loginAccount);
      } else if (arg instanceof List) {
        //参数是list的
        List list = (List) arg;
        if (!list.isEmpty() && list.get(0) instanceof BaseEntity) {
          for (Object obj : list) {
            fillEntity(obj, autoFill, loginAccount);
          }
        }
      }
    });
    return joinPoint.proceed();
  }

  private void fillEntity(Object arg, AutoFill autoFill, String loginAccount) {
    BaseEntity entity = (BaseEntity) arg;
    if (AutoFillType.CREATE.equals(autoFill.value())) {
      //新增的
      entity.setId(IdWorker.getId());
      entity.setCreatedTime(new Date());
      entity.setCreatedBy(loginAccount);
      entity.setActiveFlag(ActiveFlag.YES.value());
    } else if (AutoFillType.DELETE.equals(autoFill.value())) {
      //逻辑删除的
      entity.setActiveFlag(ActiveFlag.NO.value());
    }
    //单纯修改的
    entity.setModifiedTime(new Date());
    entity.setModifiedBy(loginAccount);
  }

}
