package com.alsace.framework.aspect;

import com.alsace.framework.common.constants.Constants;
import com.alsace.framework.annotation.LogModify;
import com.alsace.framework.utils.GsonUtils;
import com.alsace.framework.utils.LogUtils;
import com.google.common.base.Throwables;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

  @Pointcut("@annotation(com.alsace.framework.annotation.LogModify)")
  public void logAop() {
  }


  @Before("logAop()")
  public void doBefore(JoinPoint joinPoint) {
    LogInfo logInfo = getLogInfo(joinPoint);
    LogUtils.printInfo(log, Constants.LOG_MODIFY_BEFORE, logInfo.operateId,
        logInfo.operationType, logInfo.className,
        logInfo.methodName, logInfo.argsJson);
  }

  private LogInfo getLogInfo(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Object[] args = joinPoint.getArgs();
    String methodName = signature.getName();
    String className = signature.getDeclaringTypeName();
    LogModify logModify = signature.getMethod().getAnnotation(LogModify.class);
    LogInfo logInfo = new LogInfo();
    logInfo.operateId = LogOperateIdHolder.getId();
    logInfo.className = className;
    logInfo.methodName = methodName;
    logInfo.saveParams = logModify.saveParams();
    logInfo.argsJson = GsonUtils.toJson(args);
    logInfo.operationType = logModify.operationType();
    return logInfo;
  }

  @AfterReturning(value = "logAop()", returning = "res")
  public void doAfterReturning(JoinPoint joinPoint, Object res) {
    LogInfo logInfo = getLogInfo(joinPoint);
    LogUtils.printInfo(log, Constants.LOG_MODIFY_AFTER, logInfo.operateId,
        logInfo.operationType, GsonUtils.toJson(res));
    saveLogInfo(logInfo);
  }

  /**
   * TODO 保存操作日志
   */
  private void saveLogInfo(LogInfo logInfo) {
    if (logInfo.saveParams) {
      //TODO 持久化保存日志信息
      LogUtils.printInfo(log, "持久化保存日志信息:{}", GsonUtils.toJson(logInfo));
    }
  }

  @AfterThrowing(value = "logAop()", throwing = "ex")
  public void doAfterThrowing(JoinPoint joinPoint, Exception ex) {
    LogInfo logInfo = getLogInfo(joinPoint);
    LogUtils.printInfo(log, Constants.LOG_MODIFY_EXCEPTION, logInfo.operateId,
        logInfo.operationType, Throwables.getStackTraceAsString(ex));
  }


  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  private class LogInfo {

    private String operateId;//切面操作ID
    private String className;//调用class类名称
    private String methodName;//调用方法名称
    private String argsJson;//参数JSON
    private boolean saveParams;//是否保存参数和结果

    private String operationType;//操作类型
  }
}
