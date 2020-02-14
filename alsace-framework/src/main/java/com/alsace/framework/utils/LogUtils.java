package com.alsace.framework.utils;

import com.google.common.base.Throwables;
import org.slf4j.Logger;

/**
 * 统一日志处理
 */

public class LogUtils {

  /**
   * 打印info级别日志
   */
  public static void info(Logger log, String formatter, Object... obj) {
    if (log.isInfoEnabled()) {
      log.info(formatter, obj);
    }
  }

  /**
   * 打印info级别日志
   */
  public static void info(Logger log, String message) {
    if (log.isInfoEnabled()) {
      log.info(message);
    }
  }

  /**
   * 打印error级别日志
   */
  public static void error(Logger log, String formatter, Object... obj) {
    if (log.isErrorEnabled()) {
      log.error(formatter, obj);
    }
  }

  /**
   * 打印error级别日志
   */
  public static void error(Logger log, Exception ex) {
    if (log.isErrorEnabled()) {
      log.error(Throwables.getStackTraceAsString(ex));
    }
  }

  /**
   * 打印error级别日志
   */
  public static void error(Logger log, String message) {
    if (log.isErrorEnabled()) {
      log.error(message);
    }
  }

  /**
   * 打印warn级别日志
   */
  public static void warn(Logger log, String formatter, Object... obj) {
    if (log.isWarnEnabled()) {
      log.warn(formatter, obj);
    }
  }

  /**
   * 打印warn级别日志
   */
  public static void warn(Logger log, String message) {
    if (log.isWarnEnabled()) {
      log.warn(message);
    }
  }

  /**
   * 打印debug级别日志
   */
  public static void debug(Logger log, String formatter, Object... obj) {
    if (log.isDebugEnabled()) {
      log.debug(formatter, obj);
    }
  }

  /**
   * 打印debug级别日志
   */
  public static void debug(Logger log, String message) {
    if (log.isDebugEnabled()) {
      log.debug(message);
    }
  }

}
