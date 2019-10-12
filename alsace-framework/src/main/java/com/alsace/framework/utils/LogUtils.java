package com.alsace.framework.utils;

import org.slf4j.Logger;

/**
 * 统一日志处理
 */

public class LogUtils {

  /**
   * 打印info级别日志
   */
  public static void printInfo(Logger log, String formatter, Object... obj) {
    if (log.isInfoEnabled()) {
      log.info(formatter, obj);
    }
  }

  /**
   * 打印info级别日志
   */
  public static void printInfo(Logger log, String message) {
    if (log.isInfoEnabled()) {
      log.info(message);
    }
  }

  /**
   * 打印error级别日志
   */
  public static void printError(Logger log, String formatter, Object... obj) {
    if (log.isErrorEnabled()) {
      log.error(formatter, obj);
    }
  }

  /**
   * 打印error级别日志
   */
  public static void printError(Logger log, String message) {
    if (log.isErrorEnabled()) {
      log.error(message);
    }
  }

  /**
   * 打印warn级别日志
   */
  public static void printWarn(Logger log, String formatter, Object... obj) {
    if (log.isWarnEnabled()) {
      log.warn(formatter, obj);
    }
  }

  /**
   * 打印warn级别日志
   */
  public static void printWarn(Logger log, String message) {
    if (log.isWarnEnabled()) {
      log.warn(message);
    }
  }

  /**
   * 打印debug级别日志
   */
  public static void printDebug(Logger log, String formatter, Object... obj) {
    if (log.isDebugEnabled()) {
      log.debug(formatter, obj);
    }
  }

  /**
   * 打印debug级别日志
   */
  public static void printDebug(Logger log, String message) {
    if (log.isDebugEnabled()) {
      log.debug(message);
    }
  }

}
