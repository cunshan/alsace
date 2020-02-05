package com.alsace.framework.aspect;

import com.alsace.framework.common.log.LogInfo;
import com.alsace.framework.common.log.LogService;
import com.alsace.framework.common.constants.Constants;
import com.alsace.framework.annotation.LogModify;
import com.alsace.framework.utils.GsonUtils;
import com.alsace.framework.utils.LogUtils;
import com.google.common.base.Throwables;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect {

  @Resource
  private LogService logService;

  @Pointcut("@annotation(com.alsace.framework.annotation.LogModify)")
  public void logAop() {
  }


  @Before("logAop()")
  public void doBefore(JoinPoint joinPoint) {
    LogInfo logInfo = getLogInfo(joinPoint);
    LogUtils.printInfo(log, Constants.LOG_MODIFY_BEFORE, logInfo.getOperateId(),
        logInfo.getOperationType(), logInfo.getClassName(),
        logInfo.getMethodName(), logInfo.getArgsJson());
  }

  private LogInfo getLogInfo(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Object[] args = joinPoint.getArgs();
    String methodName = signature.getName();
    String className = signature.getDeclaringTypeName();
    LogModify logModify = signature.getMethod().getAnnotation(LogModify.class);
    LogInfo logInfo = new LogInfo();

    logInfo.setOperateId(LogOperateIdHolder.getId());
    logInfo.setClassName(className);
    logInfo.setMethodName(methodName);
    logInfo.setArgsJson(GsonUtils.toJson(args));
    logInfo.setOperationType(logModify.operationType());

    return logInfo;
  }

  @AfterReturning(value = "logAop()", returning = "res")
  public void doAfterReturning(JoinPoint joinPoint, Object res) {
    LogInfo logInfo = getLogInfo(joinPoint);
    LogUtils.printInfo(log, Constants.LOG_MODIFY_AFTER, logInfo.getOperateId(),
        logInfo.getOperationType(), GsonUtils.toJson(res));
    saveLogInfo(logInfo);
  }

  /**
   * 保存操作日志
   */
  private void saveLogInfo(LogInfo logInfo) {
      logService.saveLog(logInfo);
  }

  @AfterThrowing(value = "logAop()", throwing = "ex")
  public void doAfterThrowing(JoinPoint joinPoint, Exception ex) {
    LogInfo logInfo = getLogInfo(joinPoint);
    LogUtils.printInfo(log, Constants.LOG_MODIFY_EXCEPTION, logInfo.getOperateId(),
        logInfo.getOperationType(), Throwables.getStackTraceAsString(ex));
  }
}
