package com.alsace.framework.aspect;

import com.alsace.framework.common.basic.AlsaceResponse;
import com.alsace.framework.utils.LogUtils;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {


  /**
   * 处理shiro登录异常
   */
  @ExceptionHandler(AuthenticationException.class)
  @ResponseBody
  public AlsaceResponse authenticationException(AuthenticationException ex){
    LogUtils.info(log, Throwables.getStackTraceAsString(ex));
    return new AlsaceResponse.Builder(false).code(HttpStatus.UNAUTHORIZED.value()).msg(ex.getMessage()).build();
  }


}
